package org.jypj.zgcsx.course.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.DayOfWeek;

@ConfigurationProperties(prefix = "course")
public class CourseProperties {
    private String casServerPrefix;
    private String casService;
    private String logoutMatcher = "/logout";
    private DayOfWeek firstDayOfWeek = DayOfWeek.SUNDAY;
    private String commonUrl;
    private String systemNum;
    private Integer evaluateEndDay;

    public String getCasServerPrefix() {
        return casServerPrefix;
    }

    public void setCasServerPrefix(String casServerPrefix) {
        this.casServerPrefix = casServerPrefix;
    }

    public String getCasService() {
        return casService;
    }

    public void setCasService(String casService) {
        this.casService = casService;
    }

    public String getLogoutMatcher() {
        return logoutMatcher;
    }

    public void setLogoutMatcher(String logoutMatcher) {
        this.logoutMatcher = logoutMatcher;
    }

    public DayOfWeek getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(DayOfWeek firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public String getCommonUrl() {
        return commonUrl;
    }

    public void setCommonUrl(String commonUrl) {
        this.commonUrl = commonUrl;
    }

    public String getSystemNum() {
        return systemNum;
    }

    public void setSystemNum(String systemNum) {
        this.systemNum = systemNum;
    }

    public Integer getEvaluateEndDay() {
        return evaluateEndDay;
    }

    public void setEvaluateEndDay(Integer evaluateEndDay) {
        this.evaluateEndDay = evaluateEndDay;
    }
}
