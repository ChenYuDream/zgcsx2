package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.StudentCourse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生选课表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    /**
     * 根据课程属性id查询学生选课表和学生表信息
     *
     * @param page
     * @param studentCourse
     * @return
     */
    List<StudentCourse> selectStudents(Pagination page, StudentCourse studentCourse);

    /**
     * 查询所有学生选课信息
     *
     * @param page
     * @param condition
     * @return
     */
    List<StudentCourse> selectAllStudentCourse(Pagination page, Map<String, Object> condition);

    List<StudentCourse> selectAllStudentCourse(Map<String, Object> condition);

    /**
     * 查询所有学生选课信息(历史记录)
     *
     * @param page
     * @param condition
     * @return
     */
    List<StudentCourse> selectAllHistory(Page<StudentCourse> page, Map<String, Object> condition);

    /**
     * 查询所有学生自动分配信息
     *
     * @param page
     * @param condition
     * @return
     */
    List<StudentCourse> selectAllByAllot(Pagination page, Map<String, Object> condition);

    List<StudentCourse> selectAllByAllot(Map<String, Object> condition);

    /**
     * 查询开放课程缺勤学生
     *
     * @param page
     * @param student
     * @return
     */
    List<StudentCourse> selectChooseAttendanceStudents(Page<StudentCourse> page, StudentCourse student);

    /**
     * 查询学生选修课历史记录
     *
     * @return
     */
    List<StudentCourse> selectHistChooseCourse();
}
