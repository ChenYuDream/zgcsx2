package org.jypj.zgcsx.course.controller;

import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.message.MessageSourceService;
import org.jypj.zgcsx.course.service.*;

import javax.annotation.Resource;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 13:47
 */
public class BaseController {
    @Resource
    StudentAttendanceService studentAttendanceService;
    @Resource
    TeacherService teacherService;
    @Resource
    ApiService apiService;
    @Resource
    StudentService studentService;
    @Resource
    XnxqService xnxqService;
    @Resource
    SpaceService spaceService;
    @Resource
    TreeService treeService;
    @Resource
    ClazzService clazzService;
    @Resource
    SchoolXqService schoolXqService;
    @Resource
    GradeService gradeService;
    @Resource
    ClazzTimetableService clazzTimetableService;
    @Resource
    BaseCourseService baseCourseService;
    @Resource
    CourseResourceService courseResourceService;
    @Resource
    AttachmentService attachmentService;
    @Resource
    OptionalCourseService optionalCourseService;
    @Resource
    StudentCourseService studentCourseService;
    @Resource
    TeacherCourseService teacherCourseService;
    @Resource
    WorkDayService workDayService;
    @Resource
    GradeTimetableService gradeTimetableService;
    @Resource
    MessageSourceService messageSourceService;

    Result render(String code) {
        return new Result(code, messageSourceService.getMessage(code));
    }

    Result render(boolean success, String code) {
        return new Result(true, code, messageSourceService.getMessage(code));
    }

    Result render(Object data) {
        return new Result(true, messageSourceService.getMessage("course.ok"), data);
    }
}
