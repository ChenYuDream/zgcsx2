package org.jypj.zgcsx.course.controller;


import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.dao.WorkDayMapper;
import org.jypj.zgcsx.course.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * 考勤表 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/studentAttendance")
public class StudentAttendanceController extends BaseController {

    @Resource
    private WorkDayMapper workDayMapper;

    /**
     * 显示 基础课程考勤 列表
     *
     * @return
     */
    @RequestMapping("page/basic/list")
    public String toBasicList(Model model, StudentAttendance studentAttendance) {
        WorkDay workDay = workDayMapper.selectById(studentAttendance.getWorkDayId());
        studentAttendance.setDayOfWeek(workDay.getDayOfWeek());
        studentAttendance.setPeriod(workDay.getPeriod());
        studentAttendance.setCampusId(workDay.getCampusId());
        model.addAttribute("data", studentAttendance);
        return "attendance/basic_list";
    }

    /**
     * 显示 选修课程考勤 列表
     *
     * @return
     */
    @RequestMapping("page/choose/list")
    public String toChooseList(Model model, StudentAttendance studentAttendance) {
        model.addAttribute("data", studentAttendance);
        OptionalCourse optionalCourse = optionalCourseService.selectById(studentAttendance.getOptionalCourseId());
        model.addAttribute("xxlb", optionalCourse.getBaseCourse().getXxlb());
        return "attendance/choose_list";
    }

    /**
     * 显示 基础课程 缺勤学生 列表
     *
     * @return
     */
    @RequestMapping("page/basic/student/list")
    public String toBasicStudentList() {
        return "attendance/dlg/basic_absentee_list";
    }

    /**
     * 显示 选修课程 缺勤学生 列表
     *
     * @return
     */
    @RequestMapping("page/choose/student/list")
    public String toChooseStudentList() {
        return "attendance/dlg/choose_student_list";
    }

    /**
     * 添加 基础层 考勤
     *
     * @param clazzId   班级id
     * @param workDayId 日程id
     * @return
     */
    @RequestMapping("page/basic/add")
    public String toBasicAdd(Model model, String clazzId, String workDayId, String week) {
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("workDayId", workDayId);
        model.addAttribute("week", week);
        return "attendance/dlg/basic_add";
    }

    /**
     * 添加 开放/拓展层 考勤
     *
     * @param optionalCourseId 课程详情id
     * @param workDayId        日程id
     * @return
     */
    @RequestMapping("page/choose/add")
    public String toChooseAdd(Model model, String optionalCourseId, String workDayId) {
        model.addAttribute("optionalCourseId", optionalCourseId);
        model.addAttribute("workDayId", workDayId);
        return "attendance/dlg/choose_add";
    }

    /**
     * 显示 学生 列表(弹框)
     *
     * @return
     */
    @RequestMapping("page/student/list")
    public String toStudentDetail() {
        return "attendance/dlg/student_list";
    }

    /**
     * 添加基础课程考勤
     *
     * @param studentAttendance
     * @return
     */
    @RequestMapping("/save/attendance")
    @ResponseBody
    public Result saveAttendance(StudentAttendance studentAttendance) {
        int i = 0;
        if (StringUtil.isNotEmpty(studentAttendance.getStudentIds()) && studentAttendance.getStudentIds().length > 0) {
            //根据workDayId（不带周次）添加或查询带周次的workDayId
            WorkDay workDay = studentAttendanceService.saveOrSelectWorkDay(studentAttendance.getWorkDayId(), studentAttendance.getWeekOfTerm());
            studentAttendance.setWorkDayId(workDay.getId());
            i = studentAttendanceService.saveAttendance(studentAttendance);
        }
        return new Result(i > 0, i > 0 ? "添加考勤成功" : "添加考勤失败，该学生已添加");
    }

    /**
     * 添加开放课程考勤
     *
     * @param studentAttendance
     * @return
     */
    @RequestMapping("/save/choose/attendance")
    @ResponseBody
    public Result saveChooseAttendance(StudentAttendance studentAttendance) {
        int i = 0;
        if (StringUtil.isNotEmpty(studentAttendance.getChooseAttendance()) && studentAttendance.getChooseAttendance().size() > 0) {
            i = studentAttendanceService.saveChooseAttendance(studentAttendance);
        }
        return new Result(i > 0, i > 0 ? "添加考勤成功" : "添加考勤失败");
    }

    /**
     * 获取基础课程缺勤记录
     *
     * @param page
     * @param studentAttendance
     * @return
     */
    @RequestMapping("/basic/student/attendance")
    @ResponseBody
    public Result selectBasicStudentAttendance(Page<StudentAttendance> page, StudentAttendance studentAttendance, @CurrentXnxq Xnxq xnxq) {
        return render(studentAttendanceService.selectBasicStudentAttendance(page, studentAttendance, xnxq));
    }

    /**
     * 获取选修课程缺勤记录
     *
     * @param page
     * @param studentAttendance
     * @param xnxq
     * @return
     */
    @RequestMapping("/choose/student/attendance")
    @ResponseBody
    public Result selectChooseStudentAttendance(Page<StudentAttendance> page, StudentAttendance studentAttendance, @CurrentXnxq Xnxq xnxq) {
        return render(studentAttendanceService.selectChooseStudentAttendance(page, studentAttendance, xnxq));
    }

    /**
     * 查询基础课程缺勤学生
     *
     * @param page
     * @param student
     * @return
     */
    @RequestMapping("/attendance/students")
    @ResponseBody
    public Result selectAttendanceStudents(Page<Student> page, Student student) {
        return render(studentAttendanceService.selectAttendanceStudents(page, student));
    }

    /**
     * 查询开放课程缺勤学生
     *
     * @param page
     * @param studentCourse
     * @return
     */
    @RequestMapping("/attendance/choose/students")
    @ResponseBody
    public Result selectChooseAttendanceStudents(Page<StudentCourse> page, StudentCourse studentCourse) {
        return render(studentAttendanceService.selectChooseAttendanceStudents(page, studentCourse));
    }
}

