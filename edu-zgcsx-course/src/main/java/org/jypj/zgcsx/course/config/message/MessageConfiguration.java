package org.jypj.zgcsx.course.config.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class MessageConfiguration {

    @Bean
    public MessageSourceService messageSourceService(MessageSource messageSource) {
        return new MessageSourceService(messageSource);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
