package org.jypj.zgcsx.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.dao.ClazzTimetableMapper;
import org.jypj.zgcsx.course.dao.CourseTimeMapper;
import org.jypj.zgcsx.course.dto.TeacherCourseDto;
import org.jypj.zgcsx.course.entity.CourseTime;
import org.jypj.zgcsx.course.entity.GradeTimetable;
import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.WorkDayService;
import org.jypj.zgcsx.course.util.WorkDayUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 选修课控制器
 *
 * @author yu_chen
 * @create 2018-01-02 16:10
 **/
@RestController
@RequestMapping("course/selective")
@Slf4j
public class SelectiveCourseController extends BaseController {


    /**
     * 检查操作
     */
    public static final String COURSE_CHECK_OPTION = "check";

    @Resource
    private CourseTimeMapper courseTimeMapper;
    @Resource
    private ClazzTimetableMapper clazzTimetableMapper;

    /**
     * 查询选修课站位
     *
     * @param xnxq
     * @return
     */
    @RequestMapping("query/{schoolXqId}/{gradeId}")
    public Result selectAllSelectiveCourse(@CurrentXnxq Xnxq xnxq, @PathVariable("schoolXqId") String schoolXqId, @PathVariable("gradeId") String gradeId) {
        Map<String, Object> dataMap = new HashMap<>();
        List<CourseTime> courseTimes = courseTimeMapper.selectList(new EntityWrapper<CourseTime>()
                .eq("xqid", schoolXqId)
                .eq("kczc", 1));

        //当前年级的日程ID 用于存放选修课占位
        Set<String> workDayIds = new HashSet<>();
        //获得教师课表对象
        TeacherCourseDto teacherCourseDto = new TeacherCourseDto().initTeacherCourseDto(courseTimes);
        //获得当前学年学期必修课的日程列表
        List<WorkDay> workDays = workDayService.selectCurrentXnxqWorkDayList(xnxq);
        //将每个课程对应一个日程id
        WorkDayUtil.initTeacherDto(teacherCourseDto, workDays, true);
        List<GradeTimetable> gradeTimetables = gradeTimetableService.selectAllByMap(schoolXqId, gradeId, xnxq);
        gradeTimetables.forEach(gradeTimetable -> {
            WorkDay workDay = gradeTimetable.getWorkDay();
            TeacherCourseDto.CourseSection.Course course = teacherCourseDto.getCourseSections().get(workDay.getPeriod() - 1).getCourses().get(workDay.getDayOfWeek());
            course.setCourseName("选修课占位");
            //将已有的选修课占位存起来
            course.setEditView(true);
            course.setAddView(false);
            course.setWorkDayId(gradeTimetable.getWorkDayId());
            workDayIds.add(workDay.getId());
        });
        dataMap.put("teacherCourse", teacherCourseDto);
        dataMap.put("workDayIds", workDayIds);
        return new Result(dataMap);
    }


    /**
     * 保存选修课占位数据
     *
     * @param workDayIds 日程ID
     * @param gradeId    年级ID
     * @return
     */
    @RequestMapping("{option}/{gradeId}")
    public Result saveSelectiveCourse(String workDayIds, @PathVariable("gradeId") String gradeId, @PathVariable("option") String option,@CurrentXnxq Xnxq xnxq) {
        log.error("参数workDayIDs={}", workDayIds);
        String substring = null;
        if (StringUtils.isNotEmpty(workDayIds)) {
            substring = workDayIds.substring(1, workDayIds.length() - 1).replace("\"", "'");
            log.error(substring);
        }
        //判断是否和教师课程冲突给个提示
        String teacherNames = "";
        if (StringUtils.isNotEmpty(substring)) {
            teacherNames=clazzTimetableMapper.selectConflictTeacherName(substring, gradeId);
        }
        //如果不为空并且是检查操作
        if (StringUtils.isNotEmpty(teacherNames) && COURSE_CHECK_OPTION.equals(option)) {
            //表明有冲突给出提示
            return new Result("-1", messageSourceService.getMessage("course.selective.conflict.alert", new String[]{teacherNames}));
        } else {
            gradeTimetableService.saveAllGradeTimetables(substring, gradeId,xnxq);
        }
        return new Result();
    }


}
