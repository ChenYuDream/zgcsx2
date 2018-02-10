package org.jypj.zgcsx.course.config.template;

import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.enums.WeekDay;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({FreeMarkerProperties.class, CourseProperties.class})
public class CourseFreeMarkerConfiguration {
    private final FreeMarkerProperties properties;
    private final CourseProperties courseProperties;

    public CourseFreeMarkerConfiguration(FreeMarkerProperties properties, CourseProperties courseProperties) {
        this.properties = properties;
        this.courseProperties = courseProperties;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver() {
            @Override
            protected Class<?> requiredViewClass() {
                return CourseFreeMarkerView.class;
            }
        };
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("commonUrl", courseProperties.getCommonUrl());
        List<WeekDay> workDayWeeks = new ArrayList<>();
        List<WeekDay> allWeeks = new ArrayList<>();
        DayOfWeek first = courseProperties.getFirstDayOfWeek();
        for (int i = 0; i < 7; i++, first = first.plus(1)) {
            WeekDay weekDay = WeekDay.getByDayOfWeek(first);
            if (first != DayOfWeek.SATURDAY && first != DayOfWeek.SUNDAY) {
                workDayWeeks.add(weekDay);
            }
            allWeeks.add(weekDay);
        }
        attributes.put("firstDayOfWeek", first == DayOfWeek.SUNDAY ? 0 : first.getValue());
        attributes.put("workDayWeeks", workDayWeeks);
        attributes.put("allWeeks", allWeeks);
        resolver.setAttributesMap(attributes);
        this.properties.applyToViewResolver(resolver);
        return resolver;
    }
}
