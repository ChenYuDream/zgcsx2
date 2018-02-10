package org.jypj.zgcsx.course.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jypj.zgcsx.common.dto.DtoMenu;
import org.jypj.zgcsx.common.utils.DataUtil;
import org.jypj.zgcsx.common.utils.MenuUtil;
import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.config.user.SessionUser;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.jypj.zgcsx.course.error.CourseException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 13:47
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private CourseProperties properties;
    private final static Log log = LogFactory.getLog(IndexController.class);

    @RequestMapping({"/", ""})
    public String index(String roleId, String roleSize, String roleName, HttpSession session) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserInfo onlineUser;
        try {
            onlineUser = (UserInfo) securityContext.getAuthentication().getPrincipal();
        } catch (Exception ignored) {
            throw new CourseException("non-login");
        }
        if (StringUtils.isAnyEmpty(roleId, roleSize, roleName)) {
            roleId = (String) session.getAttribute("roleId");
            roleSize = (String) session.getAttribute("roleSize");
            roleName = (String) session.getAttribute("roleName");
        }
        if (StringUtils.isAnyEmpty(roleId, roleSize, roleName)) {
            return "redirect:" + properties.getCommonUrl() + "/login?systemNum=" + properties.getSystemNum();
        }
        if (StringUtils.isNoneEmpty(roleId, roleSize, roleName)) {
            onlineUser.setRoleId(roleId);
            onlineUser.setRoleSize(roleSize);
            onlineUser.setRoleName(roleName);
            List<DtoMenu> menuList = new MenuUtil(properties.getCommonUrl()).getMenu(roleId);
            String[] clazzes = new DataUtil(properties.getCommonUrl()).getUserData(onlineUser.getLoginName(), roleId);
            onlineUser.setClazzes(clazzes);
            if (CollectionUtils.isNotEmpty(menuList)) {
                onlineUser.setMenuList(menuList);
            } else {
                log.debug("未获取到菜单，user：" + onlineUser.getUsername() + ",userId:" + onlineUser.getId());
            }
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }
        return "index";
    }

    @RequestMapping("/user")
    @ResponseBody
    public UserInfo user(@SessionUser UserInfo userInfo) {
        return userInfo;
    }

    @RequestMapping("/session")
    @ResponseBody
    public Map<String, Object> session(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            Object value = session.getAttribute(key);
            result.put(key, value);
        }
        result.put("session_id", session.getId());
        return result;
    }


}
