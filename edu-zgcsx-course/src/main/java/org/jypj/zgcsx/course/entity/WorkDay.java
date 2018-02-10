package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jypj.zgcsx.course.error.CourseException;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * <p>
 * 日程表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_WORK_DAY")
public class WorkDay extends BaseEntity<WorkDay> implements Comparable<WorkDay> {

    private static final long serialVersionUID = 1L;

    /**
     * 校区ID
     */
    @TableField("CAMPUS_ID")
    private String campusId;
    /**
     * 学年
     */
    @TableField("XN")
    private Integer xn;
    /**
     * 学期
     */
    @TableField("XQ")
    private Integer xq;
    /**
     * 第几周
     */
    @TableField("WEEK_OF_TERM")
    private Integer weekOfTerm;
    /**
     * 周一至周五
     */
    @TableField("DAY_OF_WEEK")
    private Integer dayOfWeek;
    /**
     * 所属节次
     */
    @TableField("PERIOD")
    private Integer period;
    /**
     * 开始时间
     */
    @TableField("START_TIME")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    /**
     * 结束时间
     */
    @TableField("END_TIME")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    /**
     * 日期
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @TableField(exist = false)
    private Xnxq xnxq;

    /**
     * 教师id
     */
    @TableField(exist = false)
    private String teacherId;

    public Integer getXn() {
        return xn;
    }

    public void setXn(Integer xn) {
        this.xn = xn;
    }

    public Integer getXq() {
        return xq;
    }

    public void setXq(Integer xq) {
        this.xq = xq;
    }

    public Integer getWeekOfTerm() {
        return weekOfTerm;
    }

    public void setWeekOfTerm(Integer weekOfTerm) {
        this.weekOfTerm = weekOfTerm;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "WorkDay{" +
                "xn=" + xn +
                ", xq=" + xq +
                ", weekOfTerm=" + weekOfTerm +
                ", dayOfWeek=" + dayOfWeek +
                ", period=" + period +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public int compareTo(WorkDay o) {
        if (o == null) {
            throw new CourseException("course.data-error");
        }
        if (this.weekOfTerm == null && o.weekOfTerm != null) {
            throw new CourseException("course.data-error");
        }
        if (this.weekOfTerm != null && o.weekOfTerm == null) {
            throw new CourseException("course.data-error");
        }
        int result = this.xn.compareTo(o.xn);
        if (result == 0) {
            result = this.xq.compareTo(o.xq);
        }
        if (result == 0 && this.weekOfTerm != null) {
            result = this.weekOfTerm.compareTo(o.weekOfTerm);
        }
        if (result == 0) {
            result = this.dayOfWeek.compareTo(o.dayOfWeek);
        }
        if (result == 0) {
            if (this.period != null && o.period != null) {
                result = this.period.compareTo(o.period);
            } else {
                result = this.startTime.compareTo(o.startTime);
            }
        }
        return result;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Xnxq getXnxq() {
        return xnxq;
    }

    public void setXnxq(Xnxq xnxq) {
        this.xnxq = xnxq;
    }
}
