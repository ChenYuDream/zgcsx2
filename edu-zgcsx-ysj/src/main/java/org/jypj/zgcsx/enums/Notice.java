package org.jypj.zgcsx.enums;

import lombok.Getter;

/**
 * Created by jian_wu on 2017/11/9.
 */
@Getter
public enum Notice {

    //一般： {color: #333;background-color: #fff;border-color: #ccc;}
    // 重要： {color: #fff;background-color: #f0ad4e;border-color: #eea236;}
    // 紧急：{ color: #fff;background-color: #d9534f;border-color: #d43f3a;}
    NORMAL_COLOR("#333","一般信息颜色"),
    NORMAL_BGCOLOR("#fff","一般信息背景颜色"),
    NORMAL_BORDERCOLOR(" #ccc","一般信息背景颜色"),
    IMPORTANT_COLOR("#fff","一般信息颜色"),
    IMPORTANT_BGCOLOR("#f0ad4e","一般信息背景颜色"),
    IMPORTANT_BORDERCOLOR(" #eea236","一般信息背景颜色"),
    URGENT_COLOR("#fff","一般信息颜色"),
    URGENT_BGCOLOR("#d9534f","一般信息背景颜色"),
    URGENT_BORDERCOLOR(" #d43f3a","一般信息背景颜色"),
    ACTIVITY_TYPE("2","活动事件")
    ;
    private String code;

    private String msg;

    Notice(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
