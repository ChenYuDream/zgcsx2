package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.dao.SchoolXqMapper;
import org.jypj.zgcsx.course.entity.SchoolXq;
import org.jypj.zgcsx.course.service.SchoolXqService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yu_chen
 * @create 2017-11-22 13:51
 **/
@Service
public class SchoolXqServiceImpl extends BaseServiceImpl<SchoolXqMapper, SchoolXq> implements SchoolXqService {

    @Override
    public List<SchoolXq> selectAllCampuses() {
        return baseMapper.selectAllCampuses();
    }
}
