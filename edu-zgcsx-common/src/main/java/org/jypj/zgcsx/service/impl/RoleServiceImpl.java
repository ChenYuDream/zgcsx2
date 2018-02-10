package org.jypj.zgcsx.service.impl;

import org.jypj.zgcsx.dao.RoleDao;
import org.jypj.zgcsx.entity.Role;
import org.jypj.zgcsx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/27.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> selectByUsernameAndSys(Map<String, Object> queryMap) {
        return roleDao.selectByUsernameAndSys(queryMap);
    }

    @Override
    public List<Role> selectByLoginName(String loginName) {
        return roleDao.selectByLoginName(loginName);
    }

    @Override
    public List<Role> selectByUserId(String userId) {
        return roleDao.selectByUserId(userId);
    }
}
