package org.jypj.zgcsx.common.utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author yu_chen
 * @create 2017-11-27 9:45
 **/
public class DateUtil {

    public static void main(String[] args) {
        System.out.println(getCurrentWeek());
    }

    /**
     * 得到今天的周次索引
     * 0表示周末 1表示周一 2表示周二 以此类推...
     *
     * @return
     */
    public static int getCurrentWeek() {
        return LocalDate.now().getDayOfWeek().getValue();
    }


}
