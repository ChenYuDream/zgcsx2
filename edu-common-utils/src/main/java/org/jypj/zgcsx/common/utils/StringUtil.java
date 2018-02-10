package org.jypj.zgcsx.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static boolean isEmpty(String str) {
        if ("".equals(str) || str == null) {

            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        if (!"".equals(str) && str != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 非空判断
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        boolean result = false;
        if(obj != null && obj instanceof String){
            if(obj.equals("null"))
                return false;
        }
        if (obj != null && !(obj.toString()).trim().equals(""))
            result = true;
        return result;
    }

    /**
     * 为空判断
     * @param pObj
     * @return
     */
    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }

        if (pObj instanceof String) {
            if(pObj.equals("null")) {
                return true;
            }
            if (((String) pObj).length() == 0) {
                return true;
            }
        }
        else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        }
        else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    static Pattern pattern = Pattern.compile("[0-9]*");
    public static boolean isNumeric(String str){
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /* JSON字符串特殊字符处理，比如：“\A1;1300”
     * @param s
     * @return String
     */
    public final static String stringToJson(String s) {
        if (StringUtil.isEmpty(s)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getLowUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static String getUpUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String getString(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return null;
        }
    }


    public static String transferSex(String sex){
        if("1".equals(sex)){
            return "男";
        }else if("2".equals(sex)){
            return "女";
        }else{
            return "未知";
        }
    }

    /**
     * 当前周
     * @return
     */
    public static String getCurrentTimeAndWeek(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(new Date());
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        String weekday =  weekDays[w];
        return date+"("+weekday+")";
    }

    /**
     * 获得项目url
     * @param request
     * @return
     */
    public static String getLocalUrl(HttpServletRequest request){
        String url = "http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
        return url;
    }

    /**
     * 封装，向map中加入参数
     * @param queryMap
     * @param args String 类型
     */
    public static Map<String,Object> setQueryMapParas(Map<String,Object> queryMap,String... args){
        if(queryMap == null){
            queryMap = new HashMap<>(16);
        }
        for(String arg:args){
            if(StringUtil.isNotEmpty(arg)){
                queryMap.put(arg,arg);
            }
        }
        return queryMap;
    }

    /**
     * 获得uri
     */
    public static String getUriWithOutCtx(HttpServletRequest request){
        String uri = request.getRequestURI();
        int ctxLength = request.getContextPath().length();
        return uri.substring(ctxLength,uri.length());
    }

    /**
     * 如果字符串前后有'/',则去掉'/'
     * 判断菜单权限使用
     * @param str
     * @return
     */
    public static String removeHenggang(String str){
        if(str.contains("/")&&str.indexOf("/")==0){
            str=str.substring(1);
        }
        if(str.contains("/")&&str.lastIndexOf("/")==str.length()-1){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }

    /**
     * 判断中文字符
     * 1.下载文件解决中文乱码和特殊字符的时候会使用
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

}
