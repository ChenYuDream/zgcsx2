package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.CourseClazz;
import org.jypj.zgcsx.course.service.BaseService;

/**
 * <p>
 * 选修课班级限制表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface CourseClazzService extends BaseService<CourseClazz> {

    boolean deleteByOptionalCourseId(String id);
}
