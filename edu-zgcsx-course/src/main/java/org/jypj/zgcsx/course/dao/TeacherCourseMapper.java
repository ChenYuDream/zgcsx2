package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.entity.TeacherCourse;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 老师任课表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {

    /**
     * 选修课程【课程管理】【我的课表】
     * @param teacherCourse
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<TeacherCourse> selectTeacherCourse(@Param("teacherCourse") TeacherCourse teacherCourse,@Param("xnxq") Xnxq xnxq);

    /**
     * 选修课程【课程管理】【我的工作】
     * @param teacherCourse
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<TeacherCourse> selectChooseWork(@Param("teacherCourse") TeacherCourse teacherCourse, @Param("xnxq") Xnxq xnxq);
}
