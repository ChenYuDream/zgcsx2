package org.jypj.zgcsx.course.controller;

import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.entity.ClazzTimetable;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 历史记录 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-15
 */
@Controller
@RequestMapping("history")
public class HistoryController extends BaseController {
    /**
     * 班级课程表
     */
    @RequestMapping("clazz_timetable")
    public String clazzTimetable() {
        return "history/clazz_timetable";
    }

    /**
     * 老师课程表
     */
    @RequestMapping("teacher_timetable")
    public String teacherTimetable() {
        return "history/teacher_timetable";
    }

    /**
     * 课程详情弹窗
     */
    @RequestMapping("course_detail")
    public String courseDetail() {
        return "history/dlg/course_detail";
    }

    /**
     * 学生选课信息
     */
    @RequestMapping("student_course")
    public String studentCourse() {
        return "history/student_course";
    }

    /**
     * 学生选课信息
     */
    @RequestMapping("clazz_timetable/list")
    @ResponseBody
    public Result clazzTimetableList(@RequestParam Map<String, Object> condition) {
        return new Result(workDayService.selectAllClazzTimetable(condition));
    }

}

