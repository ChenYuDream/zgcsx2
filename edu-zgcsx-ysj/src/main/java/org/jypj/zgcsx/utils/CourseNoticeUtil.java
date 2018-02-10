package org.jypj.zgcsx.utils;


import org.jypj.zgcsx.cache.CommonCache;
import org.jypj.zgcsx.entity.CodeDataChild;
import org.jypj.zgcsx.entity.CourseNotice;
import org.jypj.zgcsx.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian_wu on 2017/11/17.
 */
public class CourseNoticeUtil {

    //把事件中的字典表示的字段，转成中文
    public static void translation(CourseNotice courseNotice) {

        //事件等级 noticeLevel   无字典  0001 一般 0002重要 0003紧急
        if (StringUtil.isNotEmpty(courseNotice.getNoticeLevel())) {
            switch (courseNotice.getNoticeLevel()) {
                case "0001":
                    courseNotice.setNoticeLevel("一般");
                    break;
                case "0002":
                    courseNotice.setNoticeLevel("重要");
                    break;
                case "0003":
                    courseNotice.setNoticeLevel("紧急");
                    break;
                default:
                    break;
            }
        }

        //事件级别
        List<CodeDataChild> codeDataChildList = (List<CodeDataChild>) CommonCache.getCodeMap().get("even_level");
        if (codeDataChildList != null) {
            for (CodeDataChild codeDataChild : codeDataChildList) {
                if (StringUtil.isNotEmpty(courseNotice.getEvenLevel()) && courseNotice.getEvenLevel().equals(codeDataChild.getValue())) {
                    courseNotice.setEvenLevel(codeDataChild.getText());
                }
            }
        }

        //事件性质evenProperty  无字典 1个人事件 2部门事件
        if (StringUtil.isNotEmpty(courseNotice.getEvenProperty())) {
            switch (courseNotice.getEvenProperty()) {
                case "1":
                    courseNotice.setEvenProperty("个人事件");
                    break;
                case "2":
                    courseNotice.setEvenProperty("部门事件");
                    break;
                default:
                    break;
            }
        }

        //事件类型 evenStyle    字典 even_style
        //事件级别
        codeDataChildList = (List<CodeDataChild>) CommonCache.getCodeMap().get("even_style");
        if (codeDataChildList != null) {
            for (CodeDataChild codeDataChild : codeDataChildList) {
                if (StringUtil.isNotEmpty(courseNotice.getEvenStyle()) && courseNotice.getEvenStyle().equals(codeDataChild.getValue())) {
                    courseNotice.setEvenStyle(codeDataChild.getText());
                }
            }
        }

        //事件体系 evenTypes 多选传string，中间逗号分隔，类似’1,2,3’  字典even_type
        codeDataChildList = (List<CodeDataChild>) CommonCache.getCodeMap().get("even_type");
        if (codeDataChildList != null && StringUtil.isNotEmpty(courseNotice.getEvenType())) {
            List<String> evenTypesList = new ArrayList<>();
            String evenTypes = courseNotice.getEvenType();
            if (evenTypes.contains(",")) {
                String types[] = evenTypes.split(",");
                for (String str : types) {
                    evenTypesList.add(str);
                }
            } else {
                evenTypesList.add(evenTypes);
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (CodeDataChild codeDataChild : codeDataChildList) {
                for (String s : evenTypesList) {
                    if (s.equals(codeDataChild.getValue())) {
                        stringBuffer.append(codeDataChild.getText() + ",");
                    }
                }
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            courseNotice.setEvenType(stringBuffer.toString());
        }

    }

}
