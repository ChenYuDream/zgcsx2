package org.jypj.zgcsx.course.config.security;

import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.common.dto.DtoMenu;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAccessDecisionManager implements AccessDecisionManager {
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("当前访问没有权限");
        }
        for (ConfigAttribute configAttribute : configAttributes) {
            if (authentication == null) {
                throw new AccessDeniedException("当前访问没有权限");
            }
            UserInfo userInfo = (UserInfo) ((CasAuthenticationToken) authentication).getUserDetails();
            String needCode = configAttribute.getAttribute();
            List<DtoMenu> menuList = userInfo.getMenuList();
            if (menuList == null) {
                menuList = new ArrayList<>();
            }
            List<String> authorities = menuList.stream().flatMap(menu -> menu.getSubMenu().stream().map(DtoMenu::getUrl).filter(StringUtils::isNotEmpty)).collect(Collectors.toList());
            for (String authority : authorities) {
                if (StringUtils.equals(authority, needCode)) {
                    return;
                }
            }
            for (GrantedAuthority authority : userInfo.getAuthorities()) {
                if (StringUtils.equals(authority.getAuthority(), needCode)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("当前访问没有权限");
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}