package org.jypj.zgcsx.course.config.security;

import org.jypj.zgcsx.course.service.UserInfoService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;

public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final UserInfoService userInfoService;

    CustomSecurityMetadataSource(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        Map<String, Collection<ConfigAttribute>> metadataSource = getMetadataSource();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : metadataSource.entrySet()) {
            String uri = entry.getKey();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(uri);
            if (requestMatcher.matches(fi.getHttpRequest())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    private Map<String, Collection<ConfigAttribute>> getMetadataSource() {
        List<String> menus = userInfoService.selectAllMenus();
        Map<String, Collection<ConfigAttribute>> result = new HashMap<>();
        for (String menu : menus) {
            setAuth(result, menu, menu);
        }
        setAuth(result, "/*", "AUTH");
        return result;
    }

    private void setAuth(Map<String, Collection<ConfigAttribute>> result, String url, String role) {
        Collection<ConfigAttribute> configAttributes = new ArrayList<>();
        ConfigAttribute configAttribute = new org.springframework.security.access.SecurityConfig(role);
        configAttributes.add(configAttribute);
        result.put(url, configAttributes);
    }
}