package org.jypj.zgcsx.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.Resource;

import java.util.List;

/**
* @author yu_chen
* @create 2017-11-24 10:36
**/
public interface ResourceDao extends BaseMapper<Resource>{

    List<Resource> selectByRoleId(String roleId);

}
