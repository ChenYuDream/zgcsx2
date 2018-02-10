package org.jypj.zgcsx.course.enums;

/**
 * @author qi_ma
 * @version 1.0 2017/11/24 10:12
 * 对应字典： cims_kcly_sc
 */
public enum CourseDefinition {
    OTHER("00", "其他"),
    BASIC("01", "基础层"),
    EXPAND("02", "拓展层"),
    OPEN("03", "开放层");
    private String code;
    private String name;

    CourseDefinition(String code, String name) {
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
