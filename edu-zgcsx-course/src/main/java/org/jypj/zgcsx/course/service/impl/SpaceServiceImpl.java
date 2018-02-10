package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.SpaceMapper;
import org.jypj.zgcsx.course.entity.Space;
import org.jypj.zgcsx.course.service.SpaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author qi_ma
 * @create 2017-12-12 13:51
 **/
@Service
public class SpaceServiceImpl extends BaseServiceImpl<SpaceMapper, Space> implements SpaceService {
    @Override
    public List<Space> selectAll(Map<String, Object> condition) {
        return baseMapper.selectAll(condition);
    }

    @Override
    public Page<Space> selectAll(Page<Space> page) {
        page.setRecords(baseMapper.selectAll(page, page.getCondition()));
        return page;
    }
}
