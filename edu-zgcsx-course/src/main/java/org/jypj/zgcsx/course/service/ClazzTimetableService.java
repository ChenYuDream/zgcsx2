package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.ClazzTimetable;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 班级课程表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface ClazzTimetableService extends BaseService<ClazzTimetable> {


    /**
     * 解析班级数据
     *
     * @param saveErrorMsg   允许保存的错误提示
     * @param unSaveErrorMsg 不允许保存的错误提示
     * @param courseData     页面传来的数据
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> resolveCourseData(StringBuffer saveErrorMsg, StringBuffer unSaveErrorMsg,String courseData) throws IOException;

    /**
     * 基础课程【课程管理】【我的课表】
     *
     * 查询单个教师
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> selectCourseTimetable(ClazzTimetable clazzTimetable, Xnxq xnxq);

    /**
     * 基础课程【课程管理】【我的课表】
     *
     * 查询多个教师
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> selectCourseTimetableTeachers(ClazzTimetable clazzTimetable, Xnxq xnxq);

    /**
     * 保存所有的clazzTimeTable数据
     *
     * @param clazzTimetables
     * @param teacherId
     * @return
     */
    Boolean saveAllClazzTimeTable(List<ClazzTimetable> clazzTimetables, String teacherId,Xnxq xnxq);

    /**
     * 基础课程【课程管理】【我的工作】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> selectBasicWork(ClazzTimetable clazzTimetable, Xnxq xnxq);

    /**
     * 【课程管理】【公共课表】/【课表信息查询】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     */
    List<ClazzTimetable> selectPublicTimetable(ClazzTimetable clazzTimetable, Xnxq xnxq);

    /**
     * 【课程管理】【教师任课列表】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     */
    Page<ClazzTimetable> selectTeacherTeach(Page<ClazzTimetable> page, ClazzTimetable clazzTimetable, Xnxq xnxq, String[] clazzes);

    /**
     * 根据教师id获取教师 默认 校区，年级， 班级
     *
     * @param clazzTimetable
     * @return
     */
    List<ClazzTimetable> selectTeacherOwn(ClazzTimetable clazzTimetable, Xnxq xnxq);

    /**
     * 根据班级id何日程id查询课程信息(翻译课程名称，教师名称，年级，班级)
     *
     * 查询单个教师
     *
     * @param clazzTimetable
     * @return
     */
    ClazzTimetable selectTimetableOwn(ClazzTimetable clazzTimetable);

    /**
     * 根据班级id何日程id查询课程信息(翻译课程名称，教师名称，年级，班级)
     *
     * 查询多个教师
     *
     * @param clazzTimetable
     * @return
     */
    ClazzTimetable selectTimetableTeacherOwn(ClazzTimetable clazzTimetable);
}
