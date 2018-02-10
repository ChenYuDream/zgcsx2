package org.jypj.zgcsx.service.impl;

import org.jypj.zgcsx.dao.CodeMapDao;
import org.jypj.zgcsx.entity.CodeMap;
import org.jypj.zgcsx.service.CodeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyu
 */
@Service
public class CodeMapServiceImpl implements CodeMapService {

    private CodeMapDao codeMapDao;

    @Autowired
    public CodeMapServiceImpl(CodeMapDao codeMapDao) {
        this.codeMapDao = codeMapDao;
    }

    @Override
    public List<CodeMap> queryCodeMap(String code) {
        return codeMapDao.queryCodemap(code);
    }


    @Override
    public String queryItemText(String code, String itemValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("itemValue", itemValue);
        return codeMapDao.queryItemText(map);
    }

}
