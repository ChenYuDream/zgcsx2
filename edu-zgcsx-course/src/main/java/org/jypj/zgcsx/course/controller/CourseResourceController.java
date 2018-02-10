package org.jypj.zgcsx.course.controller;


import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.config.user.SessionUser;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.dao.BaseCourseMapper;
import org.jypj.zgcsx.course.dao.OptionalCourseMapper;
import org.jypj.zgcsx.course.entity.*;
import org.jypj.zgcsx.course.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程资源表 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/courseResource")
public class CourseResourceController extends BaseController {

    @Resource
    private CourseProperties properties;

    @Resource
    private BaseCourseMapper baseCourseMapper;

    @Resource
    private EvaluateService evaluateService;

    @Resource
    private OptionalCourseMapper optionalCourseMapper;

    @Autowired
    FileController fileController;

    /**
     * 课程资源
     *
     * @return
     */
    @RequestMapping("page/resource/tab")
    public String toResource(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("tab", params.get("tab"));
        model.addAttribute("map", params);
        return "upload/course_resource";
    }

    /**
     * 资源上传
     *
     * @param courseResource
     * @param files
     * @return
     */
    @RequestMapping("/upload/resource")
    @ResponseBody
    public Result upload(CourseResource courseResource, List<MultipartFile> files, @SessionUser UserInfo userInfo, @CurrentXnxq Xnxq xnxq) throws Exception {
        Result result = fileController.uploads(files, userInfo);
        int i = 0;
        if (result.getSuccess()) { //写库
            courseResource.setXn(xnxq.getXn());
            courseResource.setXq(xnxq.getXq());
            courseResource.setUserId(userInfo.getId());
            if (StringUtil.isNotEmpty(courseResource.getOptionalCourseId())) { //课程属性id不为空，为开放、拓展课程
                courseResource.setCourseType("2");
            } else {
                courseResource.setCourseType("1");
            }
            i = courseResourceService.save(courseResource, (List<Attachment>) result.getResult());
        } else {
            return result;
        }
        return render(i);
    }

    /**
     * 删除资源
     *
     * @param id
     * @param attachmentId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(String id, String attachmentId) throws Exception {
        int i = courseResourceService.delete(id, attachmentId);
        return new Result(i > 0, i > 0 ? "删除成功" : "删除失败");
    }

    /**
     * 查询上传资源
     * uploadType ：1 ：年级（clazzId），2：班级（gradeId），为空：年级 or 班级
     * 查询我上传的资源 （当前学期）: userId = 教师id
     * 查询基础课程[我的课表][课程资源] ：courseId = 课程定义表ID；
     * 查询选修课程[我的课表][课程资源] ：optionalCourseId = 课程属性表ID
     *
     * @param courseResource
     * @return
     */
    @RequestMapping("/course/resource")
    @ResponseBody
    public Result selectMyResource(Page<CourseResource> page, CourseResource courseResource, @CurrentXnxq Xnxq xnxq) {
        if (StringUtil.isNotEmpty(courseResource.getUploadType())) {
            switch (courseResource.getUploadType()) {
                case "1": //年级
                    courseResource.setClazzId(null);
                    break;
                case "2": //班级
                    courseResource.setGradeId(null);
                    break;
                default:
                    break;
            }
        }
        return render(courseResourceService.selectResource(page, courseResource, xnxq));
    }

    /**
     * 查询教师教授的基础课程
     *
     * @param baseCourse
     * @param xnxq
     * @return
     * @throws Exception
     */
    @RequestMapping("/basic/teacher/course")
    @ResponseBody
    public Result selectTeacherBasicCourse(BaseCourse baseCourse, @CurrentXnxq Xnxq xnxq) throws Exception {
        return render(baseCourseMapper.selectTeacherBasicCourse(baseCourse, xnxq));
    }

    /**
     * 查询教师教授的选修课程
     *
     * @param baseCourse
     * @param xnxq
     * @return
     * @throws Exception
     */
    @RequestMapping("/choose/teacher/course")
    @ResponseBody
    public Result selectTeacherChooseCourse(BaseCourse baseCourse, @CurrentXnxq Xnxq xnxq) throws Exception {
        return render(baseCourseMapper.selectTeacherChooseCourse(baseCourse, xnxq));
    }

    /**
     * 编辑评价
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit/evaluate")
    @ResponseBody
    public Result editEvaluate(CourseResource courseResource, @SessionUser UserInfo userInfo) throws Exception {
        return render(evaluateService.saveOrUpdate(courseResource, userInfo));
    }

    /**
     * 验证评价
     * 教师评价学生上传资源的时间：选修课课程开始当天 到 选修课课程结束的两周后
     * start <= now <= end
     *
     * @param
     * @return true:验证通过
     */
    @RequestMapping("/validate/evaluate")
    @ResponseBody
    public boolean validateEvaluate(String id) throws Exception {
        OptionalCourse optionalCourse = optionalCourseMapper.selectById(id);
        LocalDate startTime = optionalCourse.getStartTime();
        LocalDate endTime = optionalCourse.getEndTime().plusDays(properties.getEvaluateEndDay()); //选修课课程结束 + day天
        LocalDate now = LocalDate.now();
        return now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0;
    }
}

