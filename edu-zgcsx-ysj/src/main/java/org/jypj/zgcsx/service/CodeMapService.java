package org.jypj.zgcsx.service;

import org.jypj.zgcsx.entity.CodeMap;

import java.util.List;

/**
 * 代码表映射
 *
 * @author HUHAO
 */
public interface CodeMapService {

    /**
     * 根据code值查询字典信息
     *
     * @param code
     * @return
     */
    List<CodeMap> queryCodeMap(String code);

    /**
     * 根据itemValue 查询itemtext
     * 方法的作用:TODO
     * @param codeName
     * @param itemValue
     * @return
     */
    String queryItemText(String codeName, String itemValue);

}
