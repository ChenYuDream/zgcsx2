package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.jypj.zgcsx.course.entity.CourseClazz;
import org.jypj.zgcsx.course.dao.CourseClazzMapper;
import org.jypj.zgcsx.course.service.CourseClazzService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 选修课班级限制表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class CourseClazzServiceImpl extends BaseServiceImpl<CourseClazzMapper, CourseClazz> implements CourseClazzService {

    @Override
    public boolean deleteByOptionalCourseId(String id) {
        return delete(new EntityWrapper<CourseClazz>().eq("OPTIONAL_COURSE_ID", id));
    }
}
