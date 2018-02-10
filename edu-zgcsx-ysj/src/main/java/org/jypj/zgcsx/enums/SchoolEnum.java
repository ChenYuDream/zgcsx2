package org.jypj.zgcsx.enums;

import lombok.Getter;

/**
 * Created by jian_wu on 2017/11/15.
 */
@Getter
public enum SchoolEnum {
    SCHOOL_NODE("1","学校级别"),
    CAMPUS_NODE("2","校区级别"),
    GRADE_NODE("3","年级级别"),
    CLASS_NODE("4","班级级别"),
    ;
    private String code;

    private String msg;

    SchoolEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
