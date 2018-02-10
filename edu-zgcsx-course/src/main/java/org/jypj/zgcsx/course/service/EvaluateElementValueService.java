package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.EvaluateElementValue;

/**
 * <p>
 * 选修课评价要素值 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-03
 */
public interface EvaluateElementValueService extends BaseService<EvaluateElementValue> {
    /**
     * 根据选修课ID删除选修课评价要素值
     * @param id
     * @return
     */
    boolean deleteByOptionalCourseId(String id);
}
