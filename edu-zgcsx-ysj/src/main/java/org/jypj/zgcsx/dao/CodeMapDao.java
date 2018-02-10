package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.entity.CodeMap;

import java.util.List;
import java.util.Map;

/**
 * 代码映射表键值对查询
 *
 * @author HUHAO
 */
public interface CodeMapDao extends BaseMapper<CodeMap> {



    /**
     * 根据code值查询字典信息
     *
     * @param code
     * @return
     */
    List<CodeMap> queryCodemap(@Param("code") String code);


    String queryItemText(Map<String, Object> map);

    List<CodeMap> selectAllEvaluateLevels();
}
