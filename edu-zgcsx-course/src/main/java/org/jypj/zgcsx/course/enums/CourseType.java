package org.jypj.zgcsx.course.enums;

/**
 * @author qi_ma
 * @version 1.0 2017/11/24 10:12
 * 对应字典： cims_kc_xxlb
 */
public enum CourseType {
    COUNTRY("1", "必修"),
    LANGUAGE("2", "选修"),
    HISTORY("3", "社团"),
    MATH("4", "社团活动"),
    VISION("5", "课后一小时"),
    PERFORM("6", "管理班"),
    PLACE("7", "主题活动");
    private String code;
    private String name;

    CourseType(String code, String name) {
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
