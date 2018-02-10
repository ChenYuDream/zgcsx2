package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jypj.zgcsx.entity.CourseNotice;
import org.jypj.zgcsx.utils.CollectionUtil;

import java.util.*;

/**
 * Created by jian_wu on 2017/12/20.
 * @author jian_wu
 * 将数据转化成年月日事件轴的数据
 */
@Data
public class DtoNoticeTimeLine {

    public static Object transToTimeLine(List<CourseNotice> list){
        List<NoticeYear> years = new ArrayList<>();
        List<Integer> yearInt = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)){
            for(CourseNotice notice:list) {
                Date time = notice.getNoticeStart();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(time);
                int year = calendar.get(Calendar.YEAR);
                if(!yearInt.contains(year)){
                    yearInt.add(year);
                }
            }
            //年
            for(Integer year:yearInt){
                NoticeYear noticeYear = new NoticeYear();
                noticeYear.setData(new ArrayList<>());
                noticeYear.setYear(year);
                years.add(noticeYear);
            }
            for(NoticeYear noticeYear:years){
                List<NoticeDay> daysAll = new ArrayList<>();
                List<Integer> monthInt = new ArrayList<>();
                List<NoticeMonth> months = new ArrayList<>();
                for(CourseNotice notice:list){
                    Date time = notice.getNoticeStart();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(time);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONDAY) + 1;
                    if(year == noticeYear.getYear()){
                        if(!monthInt.contains(month)){
                            monthInt.add(month);
                        }
                    }
                }
                //月
                for(Integer month:monthInt){
                    NoticeMonth noticeMonth = new NoticeMonth();
                    noticeMonth.setMonth(month);
                    noticeMonth.setData(new ArrayList<>());
                    months.add(noticeMonth);
                }
                for(NoticeMonth noticeMonth:months){
                    List<NoticeDay> daysMonth = new ArrayList<>();
                    List<Integer> dayInt = new ArrayList<>();
                    for(CourseNotice notice:list){
                        Date time = notice.getNoticeStart();
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(time);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONDAY) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        if(year == noticeYear.getYear()&& month == noticeMonth.getMonth()){
                            if(!dayInt.contains(day)){
                                dayInt.add(day);
                            }
                        }
                    }
                    //日
                    for(Integer day:dayInt){
                        NoticeDay noticeDay = new NoticeDay();
                        noticeDay.setDay(day);
                        noticeDay.setMonth(noticeMonth.getMonth());
                        noticeDay.setData(new ArrayList<>());
                        daysMonth.add(noticeDay);
                    }
                    for(NoticeDay noticeDay:daysMonth){
                        List<DtoNoticeList> dtoNoticeLists = new ArrayList<>();
                        for(CourseNotice notice:list){
                            Date time = notice.getNoticeStart();
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(time);
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONDAY) + 1;
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            if(year == noticeYear.getYear()&& month == noticeMonth.getMonth()&&day==noticeDay.getDay()){
                                dtoNoticeLists.add(DtoNoticeList.transfer(notice));
                            }
                        }
                        noticeDay.setData(dtoNoticeLists);
                    }
                    daysAll.addAll(daysMonth);
                }
                daysAll.stream().forEach(n->n.setMonthDay(n.getMonth()+"月"+n.getDay()+"日"));
                noticeYear.setData(daysAll);
            }
        }
        return years;
    }


}

@Data
class NoticeYear{
    /**
     * 前端不要月，只要日期，但仍保留此字段
     */
    @JsonIgnore
    private List<NoticeMonth> dataold;
    private List<NoticeDay> data;
    private int year;
}

@Data
class NoticeMonth{
    private List<NoticeDay> data;
    private int month;
}

@Data
class NoticeDay{
    private List<DtoNoticeList> data;
    private int day;
    private int month;
    private String monthDay;
}