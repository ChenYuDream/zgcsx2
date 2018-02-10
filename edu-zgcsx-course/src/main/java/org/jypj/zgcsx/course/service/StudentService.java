package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.Student;

/**
 * <p>
 * 学生 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface StudentService extends BaseService<Student> {

    /**
     * 根据选修课ID分页查询已选学生
     */
    Page<Student> selectByOptionalCourseId(Page<Student> page);


    /**
     * 根据条件查询学生信息（翻译班级信息）
     * @param page
     * @param student
     * @return
     */
    Page<Student> selectStudents(Page<Student> page, Student student);
}
