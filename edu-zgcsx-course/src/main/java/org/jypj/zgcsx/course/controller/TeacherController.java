package org.jypj.zgcsx.course.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.export.PublicCourseView;
import org.jypj.zgcsx.course.config.user.SessionUser;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.dao.ClazzMapper;
import org.jypj.zgcsx.course.dao.ClazzTimetableMapper;
import org.jypj.zgcsx.course.dao.SchoolXqMapper;
import org.jypj.zgcsx.course.dao.WorkDayMapper;
import org.jypj.zgcsx.course.entity.*;
import org.jypj.zgcsx.course.vo.PublicCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 老师查询 控制器
 *
 * @author xiaoyu
 * @version 1.0 2017/11/24 13:47
 */
@Controller
@RequestMapping("teacher")
public class TeacherController extends BaseController {
    private final Log log = LogFactory.getLog(TeacherController.class);
    @Resource
    private ClazzTimetableMapper clazzTimetableMapper;

    @Resource
    private WorkDayMapper workDayMapper;

    @Resource
    private ClazzMapper clazzMapper;

    @Resource
    private SchoolXqMapper schoolXqMapper;

    @Autowired
    ApiController apiController;

    /**
     * 基础课程【课程管理】【我的课表】
     *
     * 查询单个教师
     *
     * 我的课表 ： teacherId不为空
     * 公用课表 ： clazzId不为空
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     */
    @RequestMapping("/basic/timetable")
    @ResponseBody
    public Result courseTimetable(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        if (clazzTimetable.getWorkDay() != null && clazzTimetable.getWorkDay().getXnxq() != null) {
            xnxq = clazzTimetable.getWorkDay().getXnxq();
        }
        return render(clazzTimetableService.selectCourseTimetable(clazzTimetable, xnxq));
    }

    /**
     * 基础课程【课程管理】【我的课表】
     *
     * 查询多个教师
     *
     * 我的课表 ： teacherId不为空
     * 公用课表 ： clazzId不为空
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     */
    @RequestMapping("/basic/timetable/teachers")
    @ResponseBody
    public Result courseTimetableTeachers(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        if (clazzTimetable.getWorkDay() != null && clazzTimetable.getWorkDay().getXnxq() != null) {
            xnxq = clazzTimetable.getWorkDay().getXnxq();
        }
        return render(clazzTimetableService.selectCourseTimetableTeachers(clazzTimetable, xnxq));
    }

    /**
     * 选修和课后一小时课程【课程管理】【我的课表】
     *
     * @param teacherCourse
     * @param xnxq
     * @return
     */
    @RequestMapping("/choose/timetable")
    @ResponseBody
    public Result teacherCourse(TeacherCourse teacherCourse, @CurrentXnxq Xnxq xnxq) {
        if (teacherCourse.getWorkDay() != null && teacherCourse.getWorkDay().getXnxq() != null) {
            xnxq = teacherCourse.getWorkDay().getXnxq();
        }
        return render(teacherCourseService.selectTeacherCourse(teacherCourse, xnxq));
    }

    /**
     * 基础课程【课程管理】【我的工作】 / 【基础课程】【我的课程】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    @RequestMapping("/basic/work")
    @ResponseBody
    public Result selectBasicWork(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        return render(clazzTimetableService.selectBasicWork(clazzTimetable, xnxq));
    }

    /**
     * 选修课程【课程管理】【我的工作】
     *
     * @param teacherCourse
     * @param xnxq
     * @return
     * @throws IOException
     */
    @RequestMapping("/choose/work")
    @ResponseBody
    public Result selectChooseWork(TeacherCourse teacherCourse, @CurrentXnxq Xnxq xnxq) {
        return render(teacherCourseService.selectChooseWork(teacherCourse, xnxq));
    }

    /**
     * 【课程管理】【公共课表】/【课表信息查询】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    @RequestMapping("/public/timetable")
    @ResponseBody
    public Result selectPublicTimetable(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        return render(clazzTimetableService.selectPublicTimetable(clazzTimetable, xnxq));
    }

    /**
     * 【课程管理】【公共课表】 导出
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    @RequestMapping("/public/timetable/export")
    @ResponseBody
    public ModelAndView exportPublicTimetable(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        ModelAndView modelAndView = new ModelAndView();
        List<PublicCourseVo> publicCourseVos = new ArrayList<>();
        //查询校区、年级、班级名称
        Clazz clazz = clazzMapper.selectClazzByClazzId(clazzTimetable.getClazzId());
        Grade grade = new Grade();
        grade.setName(clazz.getGradeName());
        grade.setCampusName(clazz.getCampusName());
        clazzTimetable.setClazzName(clazz.getName());
        PublicCourseVo publicCourseVo = exportDate(clazzTimetable, xnxq, null, grade);
        publicCourseVos.add(publicCourseVo);
        modelAndView.setView(new PublicCourseView(publicCourseVos, "课表"));
        return modelAndView;
    }

    /**
     * 【课程管理】【公共课表】 导出所选校区的全部班级
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    @RequestMapping("/public/timetable/exportAll")
    @ResponseBody
    public ModelAndView exporAlltPublicTimetable(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        ModelAndView modelAndView = new ModelAndView();
        List<PublicCourseVo> publicCourseVos = new ArrayList<>();
        //根据校区查询所有年级信息
        SchoolXq schoolXqs = schoolXqMapper.selectCampusesById(clazzTimetable.getCampusId());
        PublicCourseVo publicCourseVo = null;
        List<Clazz> clazzes = null;
        //根据年级查询所有班级
        for (Grade g : schoolXqs.getGradeList()) {
            g.setCampusName(schoolXqs.getName());
            Clazz clazz = new Clazz();
            clazz.setGradeId(g.getId());
            clazzes = apiService.selectClazzes(clazz);

            publicCourseVo = exportDate(clazzTimetable, xnxq, clazzes, g);
            publicCourseVos.add(publicCourseVo);
        }

        modelAndView.setView(new PublicCourseView(publicCourseVos, "课表"));
        return modelAndView;
    }

    /**
     * 导出写入单个年级数据
     * @param clazzTimetable
     * @param xnxq
     * @param clazzes 班级集合
     * @param grade 年级信息
     * @return
     */
    private PublicCourseVo exportDate(ClazzTimetable clazzTimetable, Xnxq xnxq, List<Clazz> clazzes, Grade grade){
        PublicCourseVo publicCourseVo = new PublicCourseVo(); //sheet
        List<PublicCourseVo.GradeVo> gradeVos = new ArrayList<>();           //年级集合
        PublicCourseVo.GradeVo gradeVo = new PublicCourseVo().new GradeVo(); //单年级

        List<PublicCourseVo.ClazzVo> clazzVos = new ArrayList<>();          //班级集合

        //处理数据
        if(clazzes == null){
            clazzVos.add(setExportDate(clazzTimetable, xnxq, grade));
        }else{
            for (Clazz clazz : clazzes) {
                clazzTimetable.setGradeId(grade.getId());
                clazzTimetable.setClazzId(clazz.getId());
                clazzTimetable.setClazzName(clazz.getName());
                clazzVos.add(setExportDate(clazzTimetable, xnxq, grade));
            }
        }

        gradeVo.setClazzVoList(clazzVos);
        gradeVos.add(gradeVo);

        publicCourseVo.setSheet(grade.getName());
        publicCourseVo.setGradeVoList(gradeVos);
        return publicCourseVo;
    }


    /**
     * 导出写入单个班级数据
     * @param clazzTimetable  查询条件
     * @param xnxq            学年学期
     */
    private PublicCourseVo.ClazzVo setExportDate(ClazzTimetable clazzTimetable, Xnxq xnxq, Grade grade){
        //获取数据
        List<ClazzTimetable> clazzTimetables = clazzTimetableService.selectPublicTimetable(clazzTimetable, xnxq);

        PublicCourseVo.ClazzVo clazzVo = new PublicCourseVo().new ClazzVo();
        if (clazzTimetables != null && clazzTimetables.size() > 0) {
            String[][] strings = new String[6][5];  //默认定义6节课,5天
            for (ClazzTimetable timetable : clazzTimetables) {
                strings[timetable.getWorkDay().getPeriod() - 1][timetable.getWorkDay().getDayOfWeek() - 1] = timetable.getCourseName();
            }

            List<PublicCourseVo.CourseVo> courseVos = new ArrayList<>(); //单班级所有列集合
            PublicCourseVo.CourseVo courseVo = null;        //单列
            for (int n = 0; n < strings.length; n++) {
                //if(PublicCourseView.validate(strings[n])){
                courseVo = new PublicCourseVo().new CourseVo();
                courseVo.setIndex(n + 1);
                courseVo.setCourseName(strings[n]);
                courseVos.add(courseVo);
                //}
            }
            clazzVo.setCourseVoList(courseVos);
        }
        clazzVo.setXnxq(xnxq.getXn() + "学年"+ xnxq.getXq() + "学期");
        clazzVo.setGrade(grade.getName());
        clazzVo.setClazz(grade.getName() + grade.getCampusName() + clazzTimetable.getClazzName() + "班");
        return clazzVo;
    }

    /**
     * 根据班级课程：id，修改课程教师
     *
     * @param clazzTimetable
     * @return
     * @throws IOException
     */
    @RequestMapping("/update/timetable/teacher")
    @ResponseBody
    public Result updateTimetableTeacher(ClazzTimetable clazzTimetable) {
        //查询要修改的教师在当前日程下是否存在其它课程
        EntityWrapper<ClazzTimetable> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("TEACHER_ID", clazzTimetable.getTeacherId());
        entityWrapper.eq("WORK_DAY_ID", clazzTimetable.getWorkDayId());
        int num = clazzTimetableMapper.selectCount(entityWrapper); //基础课程是否冲突
        if (num > 0) {
            return new Result(false, "所选教师【" + clazzTimetable.getTeacherName() + "】的上课时间有冲突，操作失败");
        }

        WorkDay workDay = workDayMapper.selectById(clazzTimetable.getWorkDayId());
        if(workDay != null){
            workDay.setTeacherId(clazzTimetable.getTeacherId());
            num = workDayMapper.selectCount(workDay);
            if (num > 0) {
                return new Result(false, "所选教师【" + clazzTimetable.getTeacherName() + "】的上课时间有冲突，操作失败");
            }
        }

        ClazzTimetable find = new ClazzTimetable();
        find.setClazzId(clazzTimetable.getClazzId());
        find.setWorkDayId(clazzTimetable.getWorkDayId());
        if(StringUtil.isNotEmpty(clazzTimetable.getOldTeacherId())){
            find.setTeacherId(clazzTimetable.getOldTeacherId());
        }
        find = clazzTimetableMapper.selectTimetable(find);
        int i = 0;
        if (StringUtil.isNotEmpty(find)) {
            find.setTeacherId(clazzTimetable.getTeacherId());
            i = clazzTimetableMapper.updateById(find);
        }
        return new Result(i > 0, i > 0 ? "修改成功" : "修改失败");
    }

    /**
     * 【课程管理】【教师任课列表】
     *
     * @param clazzTimetable
     * @param xnxq
     * @param userInfo
     * @return
     */
    @RequestMapping("/basic/teacher/teach")
    @ResponseBody
    public Result selectTeacherTeach(Page<ClazzTimetable> page, ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq, @SessionUser UserInfo userInfo) {
        return render(clazzTimetableService.selectTeacherTeach(page, clazzTimetable, xnxq, userInfo.getClazzes()));
    }

    /**
     * 根据课程属性id查询学生选课表和学生表信息(isShow: true 过滤缺勤学生)
     *
     * @param page
     * @param studentCourse
     * @return
     */
    @RequestMapping("/choose/students")
    @ResponseBody
    public Result selectStudents(Page<StudentCourse> page, StudentCourse studentCourse) {
        return render(studentCourseService.selectStudents(page, studentCourse));
    }

    /**
     * 根据教师id获取教师 默认 校区，年级， 班级
     *
     * @param clazzTimetable
     * @return
     */
    @RequestMapping("/teacher/own")
    @ResponseBody
    public Result selectTeacherOwn(ClazzTimetable clazzTimetable, @CurrentXnxq Xnxq xnxq) {
        return render(clazzTimetableService.selectTeacherOwn(clazzTimetable, xnxq));
    }

    /**
     * 根据班级id何日程id(课程id)查询课程信息(翻译课程名称，教师名称，年级，班级)
     *
     * 查询单个教师
     *
     * @param clazzTimetable
     * @return
     */
    @RequestMapping("/timetable/own")
    @ResponseBody
    public Result selectTimetableOwn(ClazzTimetable clazzTimetable) {
        return render(clazzTimetableService.selectTimetableOwn(clazzTimetable));
    }


    /**
     * 根据班级id何日程id(课程id)查询课程信息(翻译课程名称，教师名称，年级，班级)
     *
     * 查询多个教师
     *
     * @param clazzTimetable
     * @return
     */
    @RequestMapping("/timetable/teachers/own")
    @ResponseBody
    public Result selectTimetableTeachersOwn(ClazzTimetable clazzTimetable) {
        return render(clazzTimetableService.selectTimetableTeacherOwn(clazzTimetable));
    }
}