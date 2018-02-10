package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.jypj.zgcsx.course.dao.TeacherCourseMapper;
import org.jypj.zgcsx.course.entity.TeacherCourse;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.TeacherCourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 老师任课表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class TeacherCourseServiceImpl extends BaseServiceImpl<TeacherCourseMapper, TeacherCourse> implements TeacherCourseService {

    @Override
    public List<TeacherCourse> selectTeacherCourse(TeacherCourse teacherCourse, Xnxq xnxq) {
        return baseMapper.selectTeacherCourse(teacherCourse, xnxq);
    }

    @Override
    public List<TeacherCourse> selectChooseWork(TeacherCourse teacherCourse, Xnxq xnxq) {
        return baseMapper.selectChooseWork(teacherCourse, xnxq);
    }

    @Override
    public boolean deleteByOptionalCourseId(String id) {
        return delete(new EntityWrapper<TeacherCourse>().eq("OPTIONAL_COURSE_ID", id));
    }
}
