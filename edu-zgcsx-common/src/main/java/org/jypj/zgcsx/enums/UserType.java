package org.jypj.zgcsx.enums;

import lombok.Getter;

/**
 * Created by jian_wu on 2017/11/17.
 */
@Getter
public enum UserType {
    ADMIN("0000","admin"),
    TEACHER("0001","教师"),
    STUDENT("0002","学生"),
    WUYE("0004","物业人员"),
    ;
    private String code;

    private String msg;

    UserType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
