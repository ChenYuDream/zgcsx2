package org.jypj.zgcsx.service;

import org.jypj.zgcsx.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/27.
 */
public interface RoleService {

    List<Role> selectByUsernameAndSys(Map<String,Object> queryMap);

    //edit by qi_ma 2018-02-09 根据登录名查询全部角色
    List<Role> selectByLoginName(String loginName);
    //edit by qi_ma 2018-02-09 根据userId查询全部角色
    List<Role> selectByUserId(String userId);
}
