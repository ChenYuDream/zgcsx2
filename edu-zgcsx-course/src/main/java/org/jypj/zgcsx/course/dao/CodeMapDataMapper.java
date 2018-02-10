package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Clazz;
import org.jypj.zgcsx.course.entity.CodeMapData;

import java.util.List;

/**
 * 字典mapper
 *
 * @author
 * @create 2017-11-22 10:59
 **/
public interface CodeMapDataMapper extends BaseMapper<CodeMapData> {
    List<CodeMapData> selectCodeMapDataByCode(String code);
    List<CodeMapData> selectCodeMapDataByCodes(@Param("codes") String[] codes);
}
