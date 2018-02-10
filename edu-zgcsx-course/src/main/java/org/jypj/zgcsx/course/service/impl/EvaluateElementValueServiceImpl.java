package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.dao.EvaluateElementValueMapper;
import org.jypj.zgcsx.course.entity.EvaluateElementValue;
import org.jypj.zgcsx.course.service.EvaluateElementValueService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 选修课评价要素值 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-03
 */
@Service
public class EvaluateElementValueServiceImpl extends BaseServiceImpl<EvaluateElementValueMapper, EvaluateElementValue> implements EvaluateElementValueService {

    @Override
    public boolean deleteByOptionalCourseId(String id) {
        return baseMapper.deleteByOptionalCourseId(id) > 0;
    }
}
