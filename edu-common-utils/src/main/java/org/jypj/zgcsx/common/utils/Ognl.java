package org.jypj.zgcsx.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Ognl {
    /**
     * 使用ognl扩展
     * 该功能为，根据传入的值，
     * 如果值为0，则 ... 。
     * 如果值为1，则 ... 。
     *
     * @return
     */
    public static boolean isSolve(Object o, String soleState) {
        if (o == null) {
            return false;
        }
        String str = null;
        if (o instanceof String[]) {
            String[] objects = (String[]) o;
            str = objects[0];
        } else if (o instanceof Character) {
            Character c = (Character) o;
            str = Character.toString(c);
        } else if (o instanceof String) {
            str = (String) o;
        }
        return soleState.equals(str);

    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否为空
     *
     * @param o Object
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return StringUtils.isEmpty((String) o);
        }
        if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) o);
        }
        if (o.getClass().isArray()) {
            return ArrayUtils.isEmpty((Object[]) o);
        }
        if (o instanceof Map) {
            return ((Map) o).size() == 0;
        }
        if (o instanceof Date || o instanceof LocalDate || o instanceof Boolean || o instanceof Number) {
            return false;
        }
        throw new IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:" + o.getClass());
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param o Object
     * @return boolean
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 传入一组数据，有一个不为空返回true
     *
     * @param strs
     * @return
     */
    public static boolean hasNotEmpty(String... strs) {
        for (String s : strs) {
            if (isNotEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isBlank(Object o) {
        return StringUtils.isBlank((String) o);
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean eq(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    public static boolean neq(Object o1, Object o2) {
        return !eq(o1, o2);
    }

    public static String asString(Object[] objs) {
        StringBuilder s = new StringBuilder("");
        for (Object o : objs) {
            if (StringUtils.isNotEmpty(s)) {
                s.append(",");
            }
            s.append("'").append(o).append("'");
        }
        return String.valueOf(s);
    }
}