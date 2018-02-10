package org.jypj.zgcsx.course.enums;

/**
 * @author qi_ma
 * @version 1.0 2017/11/24 10:12
 * 对应字典： cims_kcly_ll
 */
public enum CourseCategory {
    COUNTRY("00", "其他"),
    LANGUAGE("01", "语言类"),
    HISTORY("02", "历史与社会科学类"),
    MATH("03", "数学、科学、工程、技术类"),
    VISION("04", "视觉艺术类"),
    PERFORM("05", "表演艺术类"),
    PLACE("06", "积极身体活动的健康生活方式");
    private String code;
    private String name;

    CourseCategory(String code, String name) {
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
