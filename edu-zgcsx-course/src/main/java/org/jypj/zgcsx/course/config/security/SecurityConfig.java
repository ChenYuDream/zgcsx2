package org.jypj.zgcsx.course.config.security;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.constant.CommonConstant;
import org.jypj.zgcsx.course.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CourseProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CourseProperties courseProperties;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserInfoService userInfoService;
    private final SessionRegistry sessionRegistry;
    private final ConcurrentSessionControlAuthenticationStrategy sessionControlAuthenticationStrategy;

    @Autowired
    public SecurityConfig(CourseProperties courseProperties, UserDetailsServiceImpl userDetailsService, UserInfoService userInfoService, SessionRegistry sessionRegistry, ConcurrentSessionControlAuthenticationStrategy sessionControlAuthenticationStrategy) {
        this.courseProperties = courseProperties;
        this.userDetailsService = userDetailsService;
        this.userInfoService = userInfoService;
        this.sessionRegistry = sessionRegistry;
        this.sessionControlAuthenticationStrategy = sessionControlAuthenticationStrategy;
    }

    /**
     * 定义认证用户信息获取来源，密码校验规则等
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(casAuthenticationProvider());
    }

    /**
     * 定义安全策略
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//配置安全策略
                .antMatchers("/error/**", "/test/**", "/api/**", "/session", "/user", "/logout").permitAll()//定义/请求不需要验证
                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .logout().permitAll()//定义logout不需要验证
                .and()
                .formLogin().and().authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(accessDecisionManager());
                        fsi.setSecurityMetadataSource(securityMetadataSource(userInfoService));
                        return fsi;
                    }
                });
        ;//使用form表单登录

        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint())
                .and()
                .addFilterAt(casFilter(), CasAuthenticationFilter.class)
                .addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
                .addFilterBefore(roleFilter(), CasAuthenticationFilter.class)
        ;

        http.csrf().disable(); //禁用CSRF
        http.headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();
        http.sessionManagement().maximumSessions(1).expiredUrl("/logout").sessionRegistry(sessionRegistry);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/static/**", "/js/**", "/css/**", "/img/**", "/images/**", "/font/**", "/session", "/user");
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource(UserInfoService userInfoService) {
        return new CustomSecurityMetadataSource(userInfoService);
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    /**
     * 认证的入口
     */
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(courseProperties.getCasServerPrefix() + "/login");
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /**
     * 指定service相关信息
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(courseProperties.getCasService() + "/login");
        serviceProperties.setAuthenticateAllArtifacts(true);
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    /**
     * CAS认证过滤器
     */
    @Bean
    public CasAuthenticationFilter casFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setFilterProcessesUrl("/login");
        SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//        savedRequestAwareAuthenticationSuccessHandler.setDefaultTargetUrl(courseProperties.getCasService());
        savedRequestAwareAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        casAuthenticationFilter.setAuthenticationSuccessHandler(savedRequestAwareAuthenticationSuccessHandler);
//            casAuthenticationFilter.setAuthenticationFailureHandler(new HolidayExceptionHandler());
        casAuthenticationFilter.setSessionAuthenticationStrategy(sessionControlAuthenticationStrategy);
        return casAuthenticationFilter;
    }

    /**
     * cas 认证 Provider
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(userDetailsService);
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
        return casAuthenticationProvider;
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        Cas20ServiceTicketValidator cas20ServiceTicketValidator = new Cas20ServiceTicketValidator(courseProperties.getCasServerPrefix());
        cas20ServiceTicketValidator.setEncoding(CommonConstant.DEFAULT_ENCODING);
        return cas20ServiceTicketValidator;
    }

    @Bean
    public Cas30ServiceTicketValidator cas30ServiceTicketValidator() {
        Cas30ServiceTicketValidator cas30ServiceTicketValidator = new Cas30ServiceTicketValidator(courseProperties.getCasServerPrefix());
        cas30ServiceTicketValidator.setEncoding(CommonConstant.DEFAULT_ENCODING);
        return cas30ServiceTicketValidator;
    }

    /**
     * 单点登出过滤器
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(courseProperties.getCasServerPrefix());
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    /**
     * 单点登出过滤器
     */
    @Bean
    public RoleFilter roleFilter() {
        return new RoleFilter();
    }

    /**
     * 请求单点退出过滤器
     */
    @Bean
    public LogoutFilter casLogoutFilter() throws UnsupportedEncodingException {
        LogoutFilter logoutFilter = new LogoutFilter(courseProperties.getCasServerPrefix() + "/logout?service=" + URLEncoder.encode(courseProperties.getCasService() + "/login", CommonConstant.DEFAULT_ENCODING), new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(courseProperties.getLogoutMatcher());
        return logoutFilter;
    }
}
