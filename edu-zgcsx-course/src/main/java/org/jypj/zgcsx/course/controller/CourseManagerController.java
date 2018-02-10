package org.jypj.zgcsx.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.dao.ClazzTimetableMapper;
import org.jypj.zgcsx.course.dao.CourseTimeMapper;
import org.jypj.zgcsx.course.dao.GradeTimetableMapper;
import org.jypj.zgcsx.course.dto.TeacherCourseDto;
import org.jypj.zgcsx.course.entity.*;
import org.jypj.zgcsx.course.error.CourseException;
import org.jypj.zgcsx.course.util.WorkDayUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 课表管理模块
 *
 * @author yu_chen
 * @create 2017-11-22 11:04
 **/
@RestController
@RequestMapping("courseManager")
@Slf4j
public class CourseManagerController extends BaseController {
    /**
     * 检查操作
     */
    public static final String COURSE_CHECK_OPTION = "check";

    @Resource
    private ClazzTimetableMapper clazzTimetableMapper;

    @Resource
    private CourseTimeMapper courseTimeMapper;


    /**
     * 根据级部ID查询所有班级信息
     *
     * @param gradeId
     * @return
     */
    @RequestMapping("query/clazz/{gradeId}")
    public Result selectAllClazz(@PathVariable("gradeId") String gradeId) {
        List<Clazz> clazzList = clazzService.selectList(new EntityWrapper<Clazz>()
                .eq("jbid", gradeId)
                .eq("valid", '1'));
        return new Result(clazzList);
    }


    /**
     * 查询所有搜索数据
     *
     * @return
     */
    @RequestMapping("query/search/all")
    public Result selectSearchDto() {
        //模拟数据库查询学科信息
        List<BaseCourse> baseCourses = baseCourseService.selectList(new EntityWrapper<BaseCourse>()
                .eq("kcdj", "01")
                .eq("valid", "1"));
        //查询出所有校区以及级部信息教师信息
        Map<String, Object> dataMap = new HashMap<>(5);
        List<SchoolXq> schoolXqs = schoolXqService.selectList(new EntityWrapper<SchoolXq>());
        schoolXqs.forEach(schoolXq -> {
            //遍历设置关联信息
            List<Grade> grades = gradeService.selectList(new EntityWrapper<Grade>()
                    .eq("xqid", schoolXq.getId())
                    .last("order by to_number(ssnj)"));
            //遍历所有班级
            grades.forEach(grade -> {
                List<Clazz> clazzList = clazzService.selectList(new EntityWrapper<Clazz>()
                        .eq("jbid", grade.getId())
                        .eq("valid", '1')
                        .last("order by to_number(bjmc)"));
                grade.setClazzList(clazzList);
            });
            schoolXq.setGradeList(grades);
        });
        List<Teacher> teachers = teacherService.selectList(new EntityWrapper<Teacher>()
                .eq("valid", '1'));
        dataMap.put("xqList", schoolXqs);
        dataMap.put("xkList", baseCourses);
        dataMap.put("jsList", teachers);
        return new Result(dataMap);
    }

    /**
     * 根据教师ID查询其课程数据
     *
     * @param teacherId  教师ID
     * @param schoolXqId 校区ID
     * @param xnxq       学年学期
     * @return
     */
    @RequestMapping("query/{schoolXqId}/course/{teacherId}")
    public Result selectAllCourse(@PathVariable("teacherId") String teacherId, @PathVariable("schoolXqId") String schoolXqId, @CurrentXnxq Xnxq xnxq) {
        Map<String, Object> dataMap = new HashMap<>();
        List<CourseTime> courseTimes = courseTimeMapper.selectList(new EntityWrapper<CourseTime>()
                .eq("xqid", schoolXqId)
                .eq("kczc", 1));
        //查询教师的名称
        Teacher teacher = teacherService.selectById(teacherId);
        log.info("==>查询的教师teacher={}", teacher);
        if (teacher == null) {
            log.error("==>查询的教师不存在 teacherId={}", teacherId);
            throw new CourseException("教师不存在");
        }
        //获得教师课表对象
        TeacherCourseDto teacherCourseDto = new TeacherCourseDto(teacherId, teacher.getName()).initTeacherCourseDto(courseTimes);
        //获得当前学年学期必修课的日程列表
        List<WorkDay> workDays = workDayService.selectCurrentXnxqWorkDayList(xnxq);
        //将每个课程对应一个日程id
        WorkDayUtil.initTeacherDto(teacherCourseDto, workDays, true);
        //查询出教师的课程
        Map<String, String> queryMap = new HashMap<>(3);
        queryMap.put("teacherId", teacherId);
        queryMap.put("xn", xnxq.getXn() + "");
        queryMap.put("xq", xnxq.getXq() + "");
        List<ClazzTimetable> clazzTimetables = clazzTimetableMapper.selectListDetail(queryMap);
        //遍历设置teacherCourseDto
        clazzTimetables.forEach(clazzTimetable -> {
            Integer weekIndex = clazzTimetable.getWeekIndex();
            String teacherName = clazzTimetableMapper.selectTeacherName(clazzTimetable.getClazzId(), clazzTimetable.getWorkDayId());
            TeacherCourseDto.CourseSection.Course course = teacherCourseDto.getCourseSections().get(clazzTimetable.getSectionIndex() - 1).getCourses().get(weekIndex);
            course.setWorkDayId(clazzTimetable.getWorkDayId());
            course.setClassId(clazzTimetable.getClazzId());
            course.setClassName(clazzTimetable.getClazzName());
            course.setCourseId(clazzTimetable.getCourseId());
            course.setCourseName(clazzTimetable.getCourseName());
            course.setTeacherName(teacherName);
            course.setCampusId(clazzTimetable.getCampusId());
            course.setCampusName(clazzTimetable.getCampusName());
            course.setEditView(true);
            course.setAddView(false);
            log.info("==>course={}", course);
        });
        dataMap.put("teacherCourse", teacherCourseDto);
        dataMap.put("clazzTimeTables", clazzTimetables);
        return new Result(dataMap);
    }

    /**
     * 保存课程数据
     *
     * @param courseData
     * @param option     进行的操作，用来判断是检查还是保存
     * @return
     * @throws IOException
     */
    @RequestMapping("{option}/course/{teacherId}")
    public Result saveAllCourse(String courseData, @PathVariable("option") String option, @CurrentXnxq Xnxq xnxq, @PathVariable("teacherId") String teacherId) throws IOException {
        if (StringUtils.isEmpty(courseData)) {
            log.error("==>saveAllCourse 参数courseData为空");
            throw new CourseException("参数courseData不能为空");
        }
        log.info("==>courseData={}", courseData);
        StringBuffer saveErrorMsg = new StringBuffer();
        StringBuffer unSaveErrorMsg = new StringBuffer();
        //解析取到的课程数据
        List<ClazzTimetable> clazzTimetableList = clazzTimetableService.resolveCourseData(saveErrorMsg, unSaveErrorMsg, courseData);
        if (StringUtils.isNotEmpty(unSaveErrorMsg)) {
            return new Result("-2", unSaveErrorMsg.toString());
        }
        //如果错误信息不为空并且是检查操作 就返回到页面
        if (StringUtils.isNotEmpty(saveErrorMsg) && COURSE_CHECK_OPTION.equals(option)) {
            saveErrorMsg.append(" " + messageSourceService.getMessage("course.conflict.alert.info"));
            return new Result("-1", saveErrorMsg.toString());
        }
        //保存取到的课程
        clazzTimetableService.saveAllClazzTimeTable(clazzTimetableList, teacherId, xnxq);

        return new Result();
    }


}
