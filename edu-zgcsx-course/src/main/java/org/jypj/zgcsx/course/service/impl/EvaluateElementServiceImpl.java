package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.dao.EvaluateElementMapper;
import org.jypj.zgcsx.course.entity.EvaluateElement;
import org.jypj.zgcsx.course.service.EvaluateElementService;
import org.jypj.zgcsx.course.service.EvaluateElementValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 选修课评价要素 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-03
 */
@Service
public class EvaluateElementServiceImpl extends BaseServiceImpl<EvaluateElementMapper, EvaluateElement> implements EvaluateElementService {
    @Resource
    private EvaluateElementValueService evaluateElementValueService;

    /**
     * 根据选修课ID删除学业评价要素
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteByOptionalCourseId(String id) {
        return evaluateElementValueService.deleteByOptionalCourseId(id) && baseMapper.deleteByOptionalCourseId(id) > 0;
    }
}
