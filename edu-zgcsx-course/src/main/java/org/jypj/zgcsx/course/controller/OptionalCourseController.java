package org.jypj.zgcsx.course.controller;


import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jypj.zgcsx.common.utils.Ognl;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.export.OptionalCourseListView;
import org.jypj.zgcsx.course.config.export.StudentCourseAutoAllotView;
import org.jypj.zgcsx.course.config.export.StudentCourseView;
import org.jypj.zgcsx.course.config.user.SessionUser;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.entity.*;
import org.jypj.zgcsx.course.error.CourseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 选修课 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/optional")
public class OptionalCourseController extends CourseController {
    private final static String XXLB = "2";
    public final static Log log = LogFactory.getLog(OptionalCourseController.class);

    /**
     * 选修课列表
     *
     * @return
     */
    @GetMapping("list")
    public String view() {
        return "optional/list";
    }

    /**
     * 选修课学生列表
     *
     * @return
     */
    @GetMapping("course_student")
    public String students(Model model, String id) {
        model.addAttribute("id", id);
        OptionalCourse optionalCourse = optionalCourseService.selectById(id);
        if (optionalCourse == null) {
            throw new CourseException("course.optional.non-null");
        }
        model.addAttribute("xxlb", optionalCourse.getBaseCourse().getXxlb());
        return "optional/course_student";
    }

    /**
     * 我教的课程(选修课)
     *
     * @return
     */
    @GetMapping("my_optional")
    public String ownCourse(Model model) {
        model.addAttribute("xxlb", XXLB);
        return "optional/my_optional";
    }

    /**
     * 学生选课列表
     *
     * @return
     */
    @GetMapping("student_list")
    public String studentList() {
        return "optional/student_list";
    }

    /**
     * 自动分配列表
     *
     * @return
     */
    @GetMapping("allot_list")
    public String allotList() {
        return "optional/allot_list";
    }

    /**
     * 校区弹框
     *
     * @return
     */
    @GetMapping("campus")
    public String campus() {
        return "optional/dlg/campus";
    }

    /**
     * 我的选课列表
     *
     * @return
     */
    @GetMapping("own_optional")
    public String ownOptional(Model model) {
        model.addAttribute("showType", "own");
        model.addAttribute("xxlb", XXLB);
        return "optional/own_optional";
    }

    /**
     * 选课列表
     *
     * @return
     */
    @GetMapping("all_optional")
    public String allOptional(Model model) {
        model.addAttribute("showType", "all");
        model.addAttribute("xxlb", XXLB);
        return "optional/own_optional";
    }

    /**
     * 新增选修课
     *
     * @return
     */
    @GetMapping("{showType}_optional/add")
    public String add(Model model, @PathVariable String showType) {
        model.addAttribute("showType", showType);
        model.addAttribute("xxlb", XXLB);
        return "optional/optional_add";
    }

    /**
     * 编辑选修课
     *
     * @return
     */
    @GetMapping("{showType}_optional/edit")
    public String edit(Model model, String id, @PathVariable String showType) {
        model.addAttribute("showType", showType);
        model.addAttribute("id", id);
        model.addAttribute("xxlb", XXLB);
        return "optional/optional_add";
    }

    /**
     * 编辑选修课
     *
     * @return
     */
    @GetMapping("detail")
    public String detail(Model model, String id) {
        model.addAttribute("id", id);
        model.addAttribute("xxlb", XXLB);
        model.addAttribute("readonly", true);
        return "optional/optional_add";
    }

    /**
     * 选择选修课、课后一小时
     *
     * @param xxlb 2:选修课，5：课后一小时
     * @return
     */
    @GetMapping("choose_course")
    public String chooseOptionalCourse(Model model, String xxlb) {
        model.addAttribute("xxlb", xxlb);
        return "optional/dlg/choose_course";
    }

    /**
     * 选择上课地点
     */
    @GetMapping("choose_space")
    public String chooseSpace() {
        return "optional/dlg/choose_space";
    }

    /**
     * 选择任课教师
     */
    @GetMapping("choose_teacher")
    public String chooseTeacher() {
        return "optional/dlg/choose_teacher";
    }

    /**
     * 选择班级
     */
    @GetMapping("choose_clazz")
    public String chooseClazz(Model model, String xxlb, String campusId) {
        model.addAttribute("xxlb", xxlb);
        model.addAttribute("campusId", campusId);
        return "optional/dlg/choose_clazz";
    }

    /**
     * 选择学业评价要素
     */
    @GetMapping("choose_evaluate_element")
    public String chooseEvaluateElement() {
        return "optional/dlg/choose_evaluate_element";
    }

    /**
     * 课程操作
     */
    @GetMapping("course_detail")
    public String courseDetail() {
        return "optional/dlg/course_detail";
    }

    /**
     * 选修课、课后一小时时间列表弹窗
     *
     * @param xxlb 2:选修课，5：课后一小时
     * @return
     */
    @GetMapping("time_list")
    public String timeList(Model model, String xxlb) {
        model.addAttribute("xxlb", xxlb);
        return "optional/dlg/time_list";
    }

    /**
     * 查询所有选修课
     *
     * @param page condition['xn']              学年（默认当前）
     *             condition['xq']              学期（默认当前）
     *             condition['campusId']        校区
     *             condition['aliasName']       课程别名
     *             condition['kclb']            六类定义
     *             condition['xxlb']            2：选修课，5：课后一小时
     *             condition['courseStatus']    课程状态 1：已录入，2：已发布
     *             condition['teacherId']       任课教师ID
     *             condition['workDayId']       上课时间点
     *             condition['weekOfTerm']      所属周次
     * @param xnxq
     * @return
     */
    @PostMapping("optional_list")
    @ResponseBody
    public Result courseList(Page<OptionalCourse> page, @CurrentXnxq Xnxq xnxq) {
        page = setXnxq(page, xnxq);
        if (Ognl.isNotEmpty(page.getCondition().get("workDayId")) && Ognl.isNotEmpty(page.getCondition().get("weekOfTerm"))) {
            page.getCondition().put("workDayId", optionalCourseService.saveOrSelectWorkDay(page.getCondition().get("workDayId").toString(), Integer.valueOf(page.getCondition().get("weekOfTerm").toString())));
        }
        page = optionalCourseService.selectAll(page);
        return new Result(page);
    }

    /**
     * 导出所有选修课
     *
     * @param condition xn        学年（默认当前）
     *                  xq        学期（默认当前）
     *                  campusId  校区
     *                  aliasName 课程别名
     *                  xxlb      2：选修课，5：课后一小时
     *                  xnxq
     */
    @RequestMapping("export/{name}")
    public ModelAndView exportCourseList(@CurrentXnxq Xnxq xnxq,
                                         @RequestParam Map<String, Object> condition,
                                         @PathVariable("name") String name) {
        condition = setXnxq(condition, xnxq);
        List<OptionalCourse> optionalCourses = optionalCourseService.selectAll(condition);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new OptionalCourseListView(name, optionalCourses));
        return modelAndView;
    }

    /**
     * 新增选修课
     *
     * @param optionalCourse courseId           课程定义ID（必须）
     *                       aliasName          课程别名（必须）
     *                       courseType         选课类型（1：选修课，5：课后一小时）
     *                       campusId           校区ID（必须）
     *                       description        课程简介（必须）
     *                       chooseStartTime    选课开始时间（必须）
     *                       chooseEndTime      选课结束时间（必须）
     *                       workDays           详细时间（必须）
     *                       clazzes            适用班级（必须）
     *                       teachers           任课教师（必须）
     *                       limitCount         人数限制（必须）
     *                       spaceId            上课地点ID（必须）
     *                       courseRemind       课程事务提醒（必须）（1：是，0：否）
     *                       noticeLevel        日历通知等级（必须）（0001：一般，0002：重要，0003：紧急）
     * @param xnxq
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public Result save(@RequestBody @Valid OptionalCourse optionalCourse, @CurrentXnxq Xnxq xnxq, @SessionUser UserInfo userInfo) {
        boolean flag = optionalCourseService.save(optionalCourse, xnxq, userInfo);
        return new Result(flag, flag ? "保存成功！" : "保存失败！");
    }

    /**
     * 选修课详情
     *
     * @param id
     * @return
     */
    @PostMapping("detail")
    @ResponseBody
    public Result course(String id) {
        OptionalCourse optionalCourse = optionalCourseService.selectById(id);
        return new Result(optionalCourse);
    }

    /**
     * 根据选修课ID查询已选学生
     *
     * @return
     */
    @PostMapping("students")
    @ResponseBody
    public Result studentList(Page<Student> page) {
        page = studentService.selectByOptionalCourseId(page);
        return new Result(page);
    }

    /**
     * 查询所有学生选课信息
     *
     * @param page
     * @return
     */
    @PostMapping("student/list")
    @ResponseBody
    public Result selectAllStudentCourse(Page<StudentCourse> page, @CurrentXnxq Xnxq xnxq) {
        page = setXnxq(page, xnxq);
        page.getCondition().put("showType", "1");
        page = studentCourseService.selectAllStudentCourse(page);
        return new Result(page);
    }

    /**
     * 导出所有学生选课信息
     */
    @GetMapping("export/student/list")
    public ModelAndView selectAllStudentCourse(@RequestParam Map<String, Object> condition, @CurrentXnxq Xnxq xnxq) {
        condition = setXnxq(condition, xnxq);
        condition.put("showType", "1");
        List<StudentCourse> list = studentCourseService.selectAllStudentCourse(condition);
        return new ModelAndView(new StudentCourseView(list));
    }

    /**
     * 查询所有学生选课信息(历史记录)
     *
     * @param page
     * @return
     */
    @PostMapping("history/student/list")
    @ResponseBody
    public Result selectAllHistoryStudentCourse(Page<StudentCourse> page, @CurrentXnxq Xnxq xnxq) {
        page = setXnxq(page, xnxq);
        page = studentCourseService.selectAllHistory(page);
        return new Result(page);
    }

    /**
     * 查询所有学生自动分配信息
     *
     * @param page
     * @return
     */
    @PostMapping("allot/list")
    @ResponseBody
    public Result selectAllAutoAllots(Page<StudentCourse> page, @CurrentXnxq Xnxq xnxq) {
        page = setXnxq(page, xnxq);
        page.getCondition().put("showType", "2");
        page = studentCourseService.selectAllStudentCourse(page);
        return new Result(page);
    }

    /**
     * 导出所有分配记录
     *
     * @param xnxq
     * @return
     */
    @RequestMapping("allot/export")
    public ModelAndView exportAllotList(@CurrentXnxq Xnxq xnxq,
                                        @RequestParam Map<String, Object> condition) {
        condition = setXnxq(condition, xnxq);
        List<StudentCourse> studentCourses = studentCourseService.selectAllByAllot(condition);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new StudentCourseAutoAllotView(studentCourses));
        return modelAndView;
    }

    /**
     * 发布选修课
     *
     * @param id
     * @return
     */
    @PostMapping("publish")
    @ResponseBody
    public Result publish(String id) {
        OptionalCourse optionalCourse = new OptionalCourse();
        optionalCourse.setId(id);
        optionalCourse.setCourseStatus(XXLB);
        boolean success = optionalCourseService.updateById(optionalCourse);
        return new Result(success, success ? "发布成功" : "发布失败");
    }

    /**
     * 删除选修课
     *
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public Result delete(String id) {
        boolean success = optionalCourseService.deleteById(id);
        return new Result(success, success ? "删除成功" : "删除失败");
    }

    /**
     * 课程定义列表
     *
     * @param page
     * @return
     */
    @PostMapping("baseCourse/list")
    @ResponseBody
    public Result baseCourseList(Page<BaseCourse> page) {
        page = baseCourseService.selectAll(page);
        return new Result(page);
    }

    /**
     * 教师列表
     *
     * @param page
     * @param userInfo
     * @return
     */
    @PostMapping("teacher/list")
    @ResponseBody
    public Result teacherList(Page<TreeTeacher> page, @SessionUser UserInfo userInfo) {
        page.getCondition().put("currentId", userInfo.getId());
        page = treeService.selectAllTreeTeachers(page);
//        page = teacherService.selectByTree(page);
        return new Result(page);
    }

    /**
     * 班级列表
     *
     * @return
     */
    @PostMapping("clazz/list")
    @ResponseBody
    public Result clazzList(String xxlb, String campusId, @SessionUser UserInfo userInfo) {
        //edit by qi_ma 修改为无视数据权限范围
//        String[] clazzes = userInfo.getClazzes();
        List<SchoolXq> schoolXqs = schoolXqService.selectAllCampuses();
        schoolXqs = schoolXqs.stream().filter(schoolXq -> Objects.equals(schoolXq.getId(), campusId)).map(schoolXq -> {
            if (schoolXq.getGradeList() == null || schoolXq.getGradeList().size() == 0) {
                return null;
            }
            List<Grade> grades = schoolXq.getGradeList().stream().map(grade -> {
                if (Objects.equals(xxlb, "2") && grade.getGradeNum() < 3) {
                    return null;
                }
                List<Clazz> clazzList = grade.getClazzList();
                if (clazzList == null || clazzList.size() == 0) {
                    return null;
                }
//                if (clazzes != null) {
//                    clazzList = clazzList.stream().filter(clazz -> ArrayUtils.contains(clazzes, clazz.getId())).collect(Collectors.toList());
//                }
//                if (clazzList == null || clazzList.size() == 0) {
//                    return null;
//                }
                grade.setClazzList(clazzList);
                return grade;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            if (grades == null || grades.size() == 0) {
                return null;
            }
            schoolXq.setGradeList(grades);
            return schoolXq;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return new Result(schoolXqs);

    }

    /**
     * 选修课自动分配
     *
     * @param campusId 校区id
     * @param xnxq
     * @return
     */
    @RequestMapping("auto/course")
    @ResponseBody
    public Result autoSetElectiveCourse(String campusId, @SessionUser UserInfo userInfo, @CurrentXnxq Xnxq xnxq) {
        return optionalCourseService.autoSetElectiveCourse(campusId, userInfo.getClazzes(), xnxq);
    }

    /**
     * 手动释放自动分配锁
     */
    @RequestMapping("del/lock")
    @ResponseBody
    public void delLock() {
        optionalCourseService.delLock();
    }

    private <T> Page<T> setXnxq(Page<T> page, Xnxq xnxq) {
        page.setCondition(setXnxq(page.getCondition(), xnxq));
        return page;
    }

    private Map<String, Object> setXnxq(Map<String, Object> condition, Xnxq xnxq) {
        if (condition == null) {
            condition = new HashMap<>();
        }
        if (Ognl.isEmpty(condition.get("xn"))) {
            condition.put("xn", xnxq.getXn());
        }
        if (Ognl.isEmpty(condition.get("xq"))) {
            condition.put("xq", xnxq.getXq());
        }
        return condition;
    }
}

