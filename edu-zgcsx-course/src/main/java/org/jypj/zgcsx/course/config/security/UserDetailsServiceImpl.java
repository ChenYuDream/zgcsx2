package org.jypj.zgcsx.course.config.security;

import org.jasig.cas.client.validation.Assertion;
import org.jypj.zgcsx.course.service.UserInfoService;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailsServiceImpl extends AbstractCasAssertionUserDetailsService {
    private final UserInfoService userInfoService;

    UserDetailsServiceImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public UserDetails loadUserDetails(Assertion assertion) throws UsernameNotFoundException {
        Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
        return userInfoService.save(attributes);
    }

}