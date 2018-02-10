package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.StudentCourseMapper;
import org.jypj.zgcsx.course.entity.StudentCourse;
import org.jypj.zgcsx.course.service.StudentCourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生选课表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class StudentCourseServiceImpl extends BaseServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

    @Override
    public Page<StudentCourse> selectStudents(Page<StudentCourse> page, StudentCourse studentCourse) {
        page.setRecords(baseMapper.selectStudents(page, studentCourse));
        return page;
    }

    @Override
    public Page<StudentCourse> selectAllStudentCourse(Page<StudentCourse> page) {
        page.setRecords(baseMapper.selectAllStudentCourse(page, page.getCondition()));
        return page;
    }

    @Override
    public List<StudentCourse> selectAllStudentCourse(Map<String, Object> condition) {
        return baseMapper.selectAllStudentCourse(condition);
    }


    @Override
    public Page<StudentCourse> selectAllHistory(Page<StudentCourse> page) {
        page.setRecords(baseMapper.selectAllHistory(page, page.getCondition()));
        return page;
    }

    @Override
    public Page<StudentCourse> selectAllByAllot(Page<StudentCourse> page) {
        page.setRecords(baseMapper.selectAllByAllot(page, page.getCondition()));
        return page;
    }

    @Override
    public List<StudentCourse> selectAllByAllot(Map<String, Object> condition) {
        return baseMapper.selectAllByAllot(condition);
    }
}
