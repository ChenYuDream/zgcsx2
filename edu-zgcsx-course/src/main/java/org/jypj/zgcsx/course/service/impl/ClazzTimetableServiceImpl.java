package org.jypj.zgcsx.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.config.message.MessageSourceService;
import org.jypj.zgcsx.course.dao.ClazzTimetableMapper;
import org.jypj.zgcsx.course.dao.WorkDayMapper;
import org.jypj.zgcsx.course.entity.ClazzTimetable;
import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.ClazzTimetableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 班级课程表 服务实现类
 * </p>
 *
 * @author yu_chen
 * @since 2017-11-21
 */
@Service
@Slf4j
public class ClazzTimetableServiceImpl extends BaseServiceImpl<ClazzTimetableMapper, ClazzTimetable> implements ClazzTimetableService {

    @Resource
    private MessageSourceService messageSourceService;

    @Resource
    private ClazzTimetableService clazzTimetableService;

    @Resource
    private WorkDayMapper workDayMapper;

    @Resource
    private ClazzTimetableMapper clazzTimetableMapper;

    /**
     * @param courseData 页面传来的数据
     * @return
     */
    @Override
    public List<ClazzTimetable> resolveCourseData(StringBuffer saveErrorMsg, StringBuffer unSaveErrorMsg, String courseData) {
        List<ClazzTimetable> clazzTimetables = JSON.parseArray(courseData, ClazzTimetable.class);
        clazzTimetables.forEach(clazzTimetable -> {
            WorkDay workDay = workDayService.selectById(clazzTimetable.getWorkDayId());
            clazzTimetable.setWorkDay(workDay);
            //根据传来的日程信息判断是否有冲突 clazzId,workDayId
            String unSaveTeacherName = baseMapper.selectRequiredCourseConflictUnSaveTeacherName(clazzTimetable);
            String saveTeacherName = baseMapper.selectRequiredCourseConflictSaveTeacherName(clazzTimetable);
            String unSaveGradeName = baseMapper.selectSelectiveCoursePlaceholderConflictUnSaveGradeName(clazzTimetable);
            String unSaveCourseName = baseMapper.selectSelectiveCourseConflictUnSaveCourseName(clazzTimetable);
            String unSaveRequireCourseName = baseMapper.selectRequiredCourseConflictUnSaveCourseName(clazzTimetable);
            if (StringUtils.isNotEmpty(unSaveTeacherName)) {
                //周{0}第{1}节课程与{2}教师有冲突并且学科不一致不允许保存</br>
                unSaveErrorMsg.append(messageSourceService.getMessage("course.subject.conflict.alert", new Object[]{workDay.getDayOfWeek(), workDay.getPeriod(), unSaveTeacherName}));
            }
            if (StringUtils.isNotEmpty(unSaveGradeName)) {
                //周{0}第{1}节课程与{2}选修课占位有冲突不允许保存</br>
                unSaveErrorMsg.append(messageSourceService.getMessage("course.grade.placeholder.conflict.alert", new Object[]{workDay.getDayOfWeek(), workDay.getPeriod(), unSaveGradeName}));
            }
            if (StringUtils.isNotEmpty(saveTeacherName)) {
                //周{0}第{1}节课程与{2}教师有冲突</br>
                saveErrorMsg.append(messageSourceService.getMessage("course.conflict.alert", new Object[]{workDay.getDayOfWeek(), workDay.getPeriod(), saveTeacherName}));
            }
            if (StringUtils.isNotEmpty(unSaveCourseName)) {
                //周{0}第{1}节课程该教师的“{2}”选修课有冲突不允许保存</br>
                unSaveErrorMsg.append(messageSourceService.getMessage("course.teacher.selective.conflict.alert", new Object[]{workDay.getDayOfWeek(), workDay.getPeriod(), unSaveCourseName}));
            }
            if (StringUtils.isNotEmpty(unSaveRequireCourseName)) {
                //周{0}第{1}节课程与{2}有冲突不允许保存</br>
                unSaveErrorMsg.append(messageSourceService.getMessage("course.conflict.name.alert", new Object[]{workDay.getDayOfWeek(), workDay.getPeriod(), unSaveRequireCourseName}));
            }

        });
        return clazzTimetables;
    }

    /**
     * 保存取到的班级课程信息
     *
     * @param clazzTimetables
     * @param teacherId       教师ID
     * @return
     */
    @Override
    public synchronized Boolean saveAllClazzTimeTable(List<ClazzTimetable> clazzTimetables, String teacherId, Xnxq xnxq) {
        clazzTimetableMapper.deleteBeforeSaveTeacherCourse(xnxq, teacherId);
        if (clazzTimetables != null && clazzTimetables.size() > 0) {
            clazzTimetableService.insertBatch(clazzTimetables);
        }
        return true;
    }

    /******************************* 分割线 避免冲突***************************************************/

    @Override
    public List<ClazzTimetable> selectCourseTimetable(ClazzTimetable clazzTimetable, Xnxq xnxq) {
        return baseMapper.selectCourseTimetable(clazzTimetable, xnxq);
    }

    @Override
    public List<ClazzTimetable> selectCourseTimetableTeachers(ClazzTimetable clazzTimetable, Xnxq xnxq) {
        return baseMapper.selectCourseTimetableTeachers(clazzTimetable, xnxq);
    }

    @Override
    public List<ClazzTimetable> selectBasicWork(ClazzTimetable clazzTimetable, Xnxq xnxq) {
        return baseMapper.selectBasicWork(clazzTimetable, xnxq);
    }

    @Override
    public List<ClazzTimetable> selectPublicTimetable(ClazzTimetable clazzTimetable, Xnxq xnxq) {
        return baseMapper.selectPublicTimetable(clazzTimetable, xnxq);
    }

    @Override
    public Page<ClazzTimetable> selectTeacherTeach(Page<ClazzTimetable> page, ClazzTimetable clazzTimetable, Xnxq xnxq, String[] clazzes) {
        page.setRecords(baseMapper.selectTeacherTeach(page, clazzTimetable, xnxq, clazzes));
        return page;
    }

    @Override
    public List<ClazzTimetable> selectTeacherOwn(ClazzTimetable clazzTimetable, Xnxq xnxq) {
        return baseMapper.selectTeacherOwn(clazzTimetable, xnxq);
    }

    @Override
    public ClazzTimetable selectTimetableOwn(ClazzTimetable clazzTimetable) {
        //查询出不带周次的workDayId
        if (StringUtil.isNotEmpty(clazzTimetable.getWorkDayId())) {
            WorkDay workDay = workDayMapper.selectNotWeekById(clazzTimetable.getWorkDayId());
            clazzTimetable.setWorkDayId(workDay.getId());
        }
        return baseMapper.selectTimetableOwn(clazzTimetable);
    }

    @Override
    public ClazzTimetable selectTimetableTeacherOwn(ClazzTimetable clazzTimetable) {
        //查询出不带周次的workDayId
        if (StringUtil.isNotEmpty(clazzTimetable.getWorkDayId())) {
            WorkDay workDay = workDayMapper.selectNotWeekById(clazzTimetable.getWorkDayId());
            clazzTimetable.setWorkDayId(workDay.getId());
        }
        return baseMapper.selectTimetableTeacherOwn(clazzTimetable);
    }
}
