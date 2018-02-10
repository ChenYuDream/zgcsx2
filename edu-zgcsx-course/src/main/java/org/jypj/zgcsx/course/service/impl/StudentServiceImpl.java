package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.StudentMapper;
import org.jypj.zgcsx.course.entity.Student;
import org.jypj.zgcsx.course.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public Page<Student> selectByOptionalCourseId(Page<Student> page) {
        page.setRecords(baseMapper.selectByOptionalCourseId(page, page.getCondition()));
        return page;
    }

    @Override
    public Page<Student> selectStudents(Page<Student> page, Student student) {
        page.setRecords(baseMapper.selectStudents(page, student));
        return page;
    }
}
