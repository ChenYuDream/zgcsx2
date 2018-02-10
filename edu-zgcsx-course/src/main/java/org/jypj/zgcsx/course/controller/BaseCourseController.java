package org.jypj.zgcsx.course.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 课程定义表 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/course")
public class BaseCourseController extends CourseController {

    /**
     * 课程添加页面
     * 拥有数据范围权限的人的添加，可以添加多个教师的课表
     *
     * @return
     */
    @RequestMapping("page/add/other")
    public String toCourseAddOther() {
        return "course_manager/add_course_other";
    }


    /**
     * 课程添加页面
     * 教师自己添加
     *
     * @return
     */
    @RequestMapping("page/add/selective")
    public String toCourseSelective() {
        return "course_manager/add_selective_course";
    }

    /**
     * 课程添加页面
     * 教师自己添加
     *
     * @return
     */
    @RequestMapping("page/add/own")
    public String toCourseAdd() {
        return "course_manager/add_course_own";
    }

    /**
     * 课程添加页面
     * 拥有数据范围权限的人的添加，可以添加多个教师的课表
     *
     * @return
     */
    @RequestMapping("page/show/other")
    public String toCourseShowOther() {
        return "course_manager/show_course_other";
    }


    /**
     * 课程添加页面
     * 教师自己添加
     *
     * @return
     */
    @RequestMapping("page/show/own")
    public String toCourseShowOwn() {
        return "course_manager/show_course_own";
    }

    /**
     * 课程选择班级对话框
     *
     * @return
     */
    @RequestMapping("dlg/classes")
    public String toCourseClassDlg() {
        return "course_manager/course_class_dialog";
    }

    /**
     * 我的课表
     *
     * @return
     */
    @RequestMapping("page/timetable")
    public String toCourseTimetable() {
        return "own_timetable/course_timatable_own";
    }

    /**
     * 显示 我的课表 详情
     *
     * @return
     */
    @RequestMapping("page/course/timetable/own/dlg")
    public String toShowCourseTimetableOwnDetail(Model model) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("time", df.format(LocalDateTime.now()));
        return "own_timetable/dlg/course_timatable_own_dlg";
    }

    /**
     * 显示 基础课程 学生 列表页面
     *
     * @return
     */
    @RequestMapping("page/basic/student/list")
    public String toBasicStudentList(Model model, String clazzId, String workDayId, String courseId, String teacherId) {
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("workDayId", workDayId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherId", teacherId);
        return "own_timetable/list/basic_student_list";
    }

    /**
     * 我的工作
     *
     * @return
     */
    @RequestMapping("page/work")
    public String toWork() {
        return "own_work/work_list";
    }

    /**
     * 显示 我的工作 详情
     *
     * @return
     */
    @RequestMapping("page/work/detail")
    public String toShowDetail() {
        return "own_work/dlg/work_detail";
    }

    /**
     * 公共课表
     *
     * @return
     */
    @RequestMapping("page/public/timetable")
    public String toPublicTimetable() {
        return "timetable/public_timetable";
    }

    /**
     * 显示 公共课表 详情
     *
     * @return
     */
    @RequestMapping("page/public/timetable/detail")
    public String toPublicTimetableDetail() {
        return "timetable/dlg/public_timetable_detail";
    }

    /**
     * 课表信息查询
     *
     * @return
     */
    @RequestMapping("page/timetable/own")
    public String toTimetableOwn() {
        return "timetable/timetable_own";
    }

    /**
     * 显示 课表信息查询 详情
     *
     * @return
     */
    @RequestMapping("page/timetable/detail")
    public String toTimetableDetailInfo() {
        return "timetable/dlg/timetable_detail";
    }

    /**
     * 显示 教师 列表
     *
     * @param campusId 校区id
     * @param clazzId 班级id
     * @param workDayId 日程id
     * @param teacherId 教师id
     * @return
     */
    @RequestMapping("page/teacher/list")
    public String toTeacherDetail(Model model,String campusId, String clazzId, String workDayId, String teacherId) {
        model.addAttribute("campusId", campusId);
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("workDayId", workDayId);
        model.addAttribute("teacherId", teacherId);
        return "timetable/dlg/teacher_list";
    }

    /**
     * 我上传的资源
     *
     * @return
     */
    @RequestMapping("page/upload/resource")
    public String toUploadResource() {
        return "upload/upload_resource";
    }

    /**
     * 上传资源
     *
     * @return
     */
    @RequestMapping("page/upload")
    public String toUpload() {
        return "upload/upload";
    }

    /**
     * 教师任课列表
     *
     * @return
     */
    @RequestMapping("page/teacher/timetable")
    public String toTeacherTimetable() {
        return "teacher_timetable/teacher_timetable_list";
    }

    /**
     * 显示 教师任课列表 详情
     *
     * @return
     */
    @RequestMapping("page/teacher/timetable/detail")
    public String toTeacherTimetableDetail() {
        return "teacher_timetable/dlg/teacher_timetable_detail";
    }

    /**
     * 我的课程
     *
     * @return
     */
    @RequestMapping("page/course/own")
    public String toCourseOwn() {
        return "own_course/course_own";
    }

    /**
     * 显示 我的课程 详情
     *
     * @return
     */
    @RequestMapping("page/course/detail")
    public String toTeacherTimetableOwnDetail() {
        return "own_course/dlg/course_detail";
    }

    /**
     * 显示 学生 列表(弹框)
     *
     * @return
     */
    @RequestMapping("page/student")
    public String toStudentDetail() {
        return "course_manager/show_student_detail";
    }
}

