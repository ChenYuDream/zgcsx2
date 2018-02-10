package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jypj.zgcsx.entity.CourseNotice;
import org.jypj.zgcsx.utils.CourseNoticeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by jian_wu on 2017/11/17.
 * 事件，分页使用
 * 时间轴也使用此实体
 */
@Data
public class DtoNoticeList {
    private String id;
    private String noticeTitle;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date start;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date end;

    private String evenProperty;
    private String noticeLevel;
    private String noticeLevelNum;
    private String evenStyle;
    private String evenLevel;
    private String noticeContent;
    private String place;
    private String evenType;
    private String creater;
    private String evenStyleType;
    public static DtoNoticeList transfer(CourseNotice courseNotice){
        DtoNoticeList dtoNoticeList = new DtoNoticeList();
        dtoNoticeList.setEvenStyleType(courseNotice.getEvenStyle());
        CourseNoticeUtil.translation(courseNotice);
        BeanUtils.copyProperties(courseNotice,dtoNoticeList);
        dtoNoticeList.setCreateTime(courseNotice.getCtime());
        dtoNoticeList.setStart(courseNotice.getNoticeStart());
        dtoNoticeList.setEnd(courseNotice.getNoticeEnd());
        /*switch (courseNotice.getNoticeLevel()){
            case "0001":dtoNoticeList.setNoticeLevel("一般");
            break;
            case "0002":dtoNoticeList.setNoticeLevel("重要");
            break;
            case "0003":dtoNoticeList.setNoticeLevel("紧急");
            break;
            default:break;
        }
        List<CodeDataChild> codeDataChildList = (List<CodeDataChild>)CommonCache.getCodeMap().get("even_level");
        if(codeDataChildList!=null){
            for(CodeDataChild codeDataChild:codeDataChildList){
                if(StringUtil.isNotEmpty(courseNotice.getEvenLevel())&&courseNotice.getEvenLevel().equals(codeDataChild.getValue())){
                    dtoNoticeList.setEvenLevel(codeDataChild.getText());
                }
            }
        }*/
        return dtoNoticeList;
    }

}
