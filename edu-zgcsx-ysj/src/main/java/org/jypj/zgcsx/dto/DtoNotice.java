package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jypj.zgcsx.entity.CourseNotice;
import org.jypj.zgcsx.enums.Notice;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jian_wu on 2017/11/6.
 * 日历数据
 */
@Data
public class DtoNotice implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;


    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date start;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date end;

    private String url;

    private String color;

    private String backgroundColor;

    private String borderColor;

    private String textColor;

    public static DtoNotice transfer(CourseNotice courseNotice){
        DtoNotice dtoNotice = new DtoNotice();
        dtoNotice.setId(courseNotice.getId());
        dtoNotice.setTitle(courseNotice.getNoticeTitle());
        dtoNotice.setStart(courseNotice.getNoticeStart());
        dtoNotice.setEnd(courseNotice.getNoticeEnd());
        //根据事件紧急情况分颜色
        if("0001".equals(courseNotice.getNoticeLevel())){  //普通
            dtoNotice.setColor(Notice.NORMAL_COLOR.getCode());
            dtoNotice.setBackgroundColor(Notice.NORMAL_BGCOLOR.getCode());
            dtoNotice.setBorderColor(Notice.NORMAL_BORDERCOLOR.getCode());
            dtoNotice.setTextColor("black");
        }else if("0002".equals(courseNotice.getNoticeLevel())){
            dtoNotice.setColor(Notice.IMPORTANT_COLOR.getCode());
            dtoNotice.setBackgroundColor(Notice.IMPORTANT_BGCOLOR.getCode());
            dtoNotice.setBorderColor(Notice.IMPORTANT_BORDERCOLOR.getCode());
            dtoNotice.setTextColor("white");
        }else if("0003".equals(courseNotice.getNoticeLevel())){
            dtoNotice.setColor(Notice.URGENT_COLOR.getCode());
            dtoNotice.setBackgroundColor(Notice.URGENT_BGCOLOR.getCode());
            dtoNotice.setBorderColor(Notice.URGENT_BORDERCOLOR.getCode());
            dtoNotice.setTextColor("white");
        }
        return dtoNotice;
    }


}
