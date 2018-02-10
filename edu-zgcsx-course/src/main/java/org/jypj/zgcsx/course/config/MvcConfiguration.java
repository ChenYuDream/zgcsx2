package org.jypj.zgcsx.course.config;

import org.jypj.zgcsx.course.config.message.MessageSourceService;
import org.jypj.zgcsx.course.config.user.UserArgumentResolver;
import org.jypj.zgcsx.course.config.xnxq.XnxqArgumentResolver;
import org.jypj.zgcsx.course.error.CourseErrorAttributes;
import org.jypj.zgcsx.course.service.XnxqService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Servlet;
import java.util.List;

@Configuration
@Import(WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter.class)
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    private final XnxqService xnxqService;
    private final MessageSourceService messageSourceService;

    public MvcConfiguration(XnxqService xnxqService, MessageSourceService messageSourceService) {
        this.xnxqService = xnxqService;
        this.messageSourceService = messageSourceService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserArgumentResolver());
        argumentResolvers.add(new XnxqArgumentResolver(xnxqService));
    }

    @Bean
    public CourseErrorAttributes errorAttributes() {
        return new CourseErrorAttributes(messageSourceService);
    }
}