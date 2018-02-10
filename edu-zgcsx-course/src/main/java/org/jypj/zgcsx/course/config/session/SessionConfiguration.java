package org.jypj.zgcsx.course.config.session;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 14:44
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200, redisNamespace = "course")
public class SessionConfiguration {

    @Bean
    public ConcurrentSessionControlAuthenticationStrategy sessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
        return new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
    }

    @Bean
    public SessionRegistry sessionRegistry(FindByIndexNameSessionRepository sessionRepository) {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

}