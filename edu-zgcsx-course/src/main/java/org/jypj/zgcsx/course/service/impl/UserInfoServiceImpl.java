package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.dao.UserInfoMapper;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.jypj.zgcsx.course.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 13:48
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo save(Map<String, Object> attributes) {
        String userid = (String) attributes.get("userid");
        String username = (String) attributes.get("username");
        String usertype = (String) attributes.get("usertype");
        String idcard = (String) attributes.get("idcard");
        String phone = (String) attributes.get("phone");
        String loginname = (String) attributes.get("loginname");
        String nickname = (String) attributes.get("nickname");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(userid);
        userInfo.setUserName(username);
        userInfo.setUserType(usertype);
        userInfo.setIdCard(idcard);
        userInfo.setPhone(phone);
        userInfo.setLoginName(loginname);
        userInfo.setNickName(nickname);
//        userInfo = save(userInfo);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ACTUATOR"));
        authorities.add(new SimpleGrantedAuthority("AUTH"));
        userInfo.setAuthorities(authorities);
        return userInfo;
    }

    @Override
    public List<String> selectAllMenus() {
        return userInfoMapper.selectAllMenus();
    }
}
