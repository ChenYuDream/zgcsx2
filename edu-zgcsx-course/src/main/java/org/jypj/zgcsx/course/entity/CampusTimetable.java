package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * <p>
 * 校区时间表
 * </p>
 *
 * @author qi_ma
 * @since 2017-12-15
 */
@TableName("KCXX_KC_SJHS")
public class CampusTimetable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 校区ID
     */
    @TableField("XQID")
    private String campusId;
    /**
     * 课程周次
     */
    @TableField("KCZC")
    private Integer dayOfWeek;
    /**
     * 课程节次
     */
    @TableField("KCJC")
    private Integer period;
    /**
     * 开始时间
     */
    @TableField("KSSJ")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;
    /**
     * 学期结束时间
     */
    @TableField("JSSJ")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CampusTimetable{" +
                "id='" + id + '\'' +
                ", campusId='" + campusId + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", period=" + period +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
