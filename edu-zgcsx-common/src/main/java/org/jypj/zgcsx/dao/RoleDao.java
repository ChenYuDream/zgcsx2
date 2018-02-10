package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author jian_wu
 * @create 2017-11-24 10:21
 **/
public interface RoleDao extends BaseMapper<Role> {

    List<Role> selectByUsernameAndSys(Map<String, Object> queryMap);

    //edit by qi_ma 2018-02-09 根据登录名查询全部角色
    List<Role> selectByLoginName(String loginName);

    //edit by qi_ma 2018-02-09 根据userId查询全部角色
    List<Role> selectByUserId(String userId);
}
