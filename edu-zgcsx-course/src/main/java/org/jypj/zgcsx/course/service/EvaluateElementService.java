package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.EvaluateElement;

/**
 * <p>
 * 选修课评价要素 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-03
 */
public interface EvaluateElementService extends BaseService<EvaluateElement> {

    boolean deleteByOptionalCourseId(String id);
}
