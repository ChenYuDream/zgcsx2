package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.CodeMapData;

import java.util.List;

/**
 * 字典服务类
 *
 * @author qi_ma
 * @create 2017-12-08 11:08
 **/
public interface CodeMapDataService extends BaseService<CodeMapData> {

    List<CodeMapData> selectCodeMapDataByCode(String code);

    List<CodeMapData> selectCodeMapDataByCodes(String[] codes);
}
