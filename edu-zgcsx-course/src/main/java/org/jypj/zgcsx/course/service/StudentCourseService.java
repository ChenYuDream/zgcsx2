package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.StudentCourse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生选课表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface StudentCourseService extends BaseService<StudentCourse> {

    /**
     * 根据课程属性id查询学生选课表和学生表信息
     *
     * @param page
     * @param studentCourse
     * @return
     */
    Page<StudentCourse> selectStudents(Page<StudentCourse> page, StudentCourse studentCourse);

    /**
     * 查询所有学生选课信息
     *
     * @param page
     * @return
     */
    Page<StudentCourse> selectAllStudentCourse(Page<StudentCourse> page);

    List<StudentCourse> selectAllStudentCourse(Map<String, Object> condition);

    /**
     * 查询所有学生选课信息(历史记录)
     *
     * @param page
     * @return
     */
    Page<StudentCourse> selectAllHistory(Page<StudentCourse> page);

    /**
     * 查询所有学生自动分配信息
     *
     * @param page
     * @return
     */
    Page<StudentCourse> selectAllByAllot(Page<StudentCourse> page);

    List<StudentCourse> selectAllByAllot(Map<String, Object> condition);
}
