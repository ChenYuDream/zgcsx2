package org.jypj.zgcsx.course.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 课后一小时 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/after")
public class AfterCourseController extends CourseController {
    private final static String XXLB = "5";

    /**
     * 课后一小时列表
     */
    @GetMapping("own_after")
    public String ownAfter(Model model) {
        model.addAttribute("showType", "own");
        model.addAttribute("xxlb", XXLB);
        return "optional/own_optional";
    }

    /**
     * 我教的课程(课后一小时)
     */
    @GetMapping("my_after")
    public String ownCourse(Model model) {
        model.addAttribute("xxlb", XXLB);
        return "optional/my_optional";
    }

    /**
     * 课后一小时列表
     */
    @GetMapping("all_after")
    public String allAfter(Model model) {
        model.addAttribute("showType", "all");
        model.addAttribute("xxlb", XXLB);
        return "optional/own_optional";
    }

    /**
     * 新增课后一小时
     */
    @GetMapping("{showType}_after/add")
    public String add(Model model, @PathVariable String showType) {
        model.addAttribute("showType", showType);
        model.addAttribute("xxlb", XXLB);
        return "optional/optional_add";
    }

    /**
     * 编辑课后一小时
     */
    @GetMapping("{showType}_after/edit")
    public String edit(Model model, String id, @PathVariable String showType) {
        model.addAttribute("showType", showType);
        model.addAttribute("id", id);
        model.addAttribute("xxlb", XXLB);
        return "optional/optional_add";
    }

    /**
     * 课后一小时详情
     */
    @GetMapping("detail")
    public String detail(Model model, String id) {
        model.addAttribute("id", id);
        model.addAttribute("xxlb", XXLB);
        model.addAttribute("readonly", true);
        return "optional/optional_add";
    }
}

