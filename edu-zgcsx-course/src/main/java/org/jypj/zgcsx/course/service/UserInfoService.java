package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 13:48
 */
public interface UserInfoService {
    UserInfo save(Map<String, Object> attributes);

    List<String> selectAllMenus();
}
