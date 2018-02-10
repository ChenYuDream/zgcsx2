package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.TeacherCourse;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 老师任课表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface TeacherCourseService extends BaseService<TeacherCourse> {

    /**
     * 选修课程【课程管理】【我的课表】
     * @param teacherCourse
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<TeacherCourse> selectTeacherCourse(TeacherCourse teacherCourse, Xnxq xnxq);

    /**
     * 选修课程【课程管理】【我的工作】
     * @param teacherCourse
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<TeacherCourse> selectChooseWork(TeacherCourse teacherCourse, Xnxq xnxq);

    boolean deleteByOptionalCourseId(String id);
}
