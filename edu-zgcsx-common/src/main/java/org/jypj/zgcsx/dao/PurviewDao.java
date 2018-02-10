package org.jypj.zgcsx.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.Purview;

import java.util.List;
import java.util.Map;

/**
* @author jian_wu
* @create 2017-11-28 14:23
**/
public interface PurviewDao extends BaseMapper<Purview> {


    List<Purview> getListByLoginNameRoleId(Map<String,Object> queryMap);

}
