package org.jypj.zgcsx.course.enums;

/**
 * @author qi_ma
 * @version 1.0 2017/11/24 10:12
 * 对应字典： cims_kc_cc
 */
public enum CourseLevel {
    country("1", "国家"),
    PLACE("2", "地方"),
    SCHOOL("3", "校本"),
    SELF("4", "自主");
    private String code;
    private String name;

    CourseLevel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
