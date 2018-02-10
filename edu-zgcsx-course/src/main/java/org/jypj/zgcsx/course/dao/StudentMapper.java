package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Student;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生基本信息表 Mapper 接口
 * </p>
 *
 * @author yu_xiao
 * @since 2017-11-28
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 根据选修课ID查询已选学生
     *
     * @param condition [id] 选课ID
     */
    List<Student> selectByOptionalCourseId(Page<Student> page, Map<String, Object> condition);

    List<Student> selectByOptionalCourseId(Map<String, Object> condition);

    /**
     * 根据条件查询学生信息（翻译班级信息）
     * @param page
     * @param student
     * @return
     */
    List<Student> selectStudents(Page<Student> page, Student student);

    /**
     * 查询基础课程缺勤学生
     * @param page
     * @param student
     * @return
     */
    List<Student> selectAttendanceStudents(Page<Student> page, Student student);

    /**
     * 查询所有未选择选修课的学生信息
     * @param campusId
     * @param clazzes  角色班级数据范围
     * @param xnxq
     * @return
     */
    List<Student> selectLaskStudent(@Param("campusId") String campusId, @Param("clazzes") String[] clazzes, @Param("xnxq") Xnxq xnxq);
}
