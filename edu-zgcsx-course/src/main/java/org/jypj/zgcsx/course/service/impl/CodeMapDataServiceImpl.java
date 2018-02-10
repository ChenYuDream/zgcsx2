package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.dao.CodeMapDataMapper;
import org.jypj.zgcsx.course.entity.CodeMapData;
import org.jypj.zgcsx.course.service.CodeMapDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qi_ma
 * @create 2017-12-08 11:09
 **/
@Service
public class CodeMapDataServiceImpl extends BaseServiceImpl<CodeMapDataMapper, CodeMapData> implements CodeMapDataService {

    @Override
    public List<CodeMapData> selectCodeMapDataByCode(String code) {
        return baseMapper.selectCodeMapDataByCode(code);
    }

    @Override
    public List<CodeMapData> selectCodeMapDataByCodes(String[] codes) {
        return baseMapper.selectCodeMapDataByCodes(codes);
    }
}
