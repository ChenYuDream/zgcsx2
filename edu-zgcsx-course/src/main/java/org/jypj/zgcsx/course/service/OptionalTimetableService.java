package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.OptionalTimetable;
import org.jypj.zgcsx.course.service.BaseService;

/**
 * <p>
 * 课程时间表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface OptionalTimetableService extends BaseService<OptionalTimetable> {

    boolean deleteByOptionalCourseId(String id);
}
