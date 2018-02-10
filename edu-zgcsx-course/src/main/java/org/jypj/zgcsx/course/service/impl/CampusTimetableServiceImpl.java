package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.dao.CampusTimetableMapper;
import org.jypj.zgcsx.course.entity.CampusTimetable;
import org.jypj.zgcsx.course.service.CampusTimetableService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校区时间表服务实现类
 *
 * @author qi_ma
 * @create 2017-12-15 11:09
 **/
@Service
public class CampusTimetableServiceImpl extends BaseServiceImpl<CampusTimetableMapper, CampusTimetable> implements CampusTimetableService {

    @Override
    public List<CampusTimetable> selectByCampusId(String campusId) {
        return baseMapper.selectByCampusId(campusId);
    }
}
