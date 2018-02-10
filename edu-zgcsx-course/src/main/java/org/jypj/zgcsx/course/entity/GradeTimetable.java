package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.List;

/**
 * <p>
 * 年级选修课时间表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_GRADE_TIMETABLE")
public class GradeTimetable extends BaseEntity<GradeTimetable> {

    private static final long serialVersionUID = 1L;

    /**
     * 日程ID
     */
    @TableField("WORK_DAY_ID")
    private String workDayId;
    /**
     * 年级ID
     */
    @TableField("GRADE_ID")
    private String gradeId;

    /**
     * 年级名称
     */
    private String gradeName;
    /**
     * 日程
     */
    @TableField(exist = false)
    private WorkDay workDay;
    /**
     * 班级列表
     */
    @TableField(exist = false)
    private List<Clazz> clazzs;


    public GradeTimetable() {

    }

    public GradeTimetable(String workDayId, String gradeId) {
        this.workDayId = workDayId;
        this.gradeId = gradeId;
    }

    public String getWorkDayId() {
        return workDayId;
    }

    public void setWorkDayId(String workDayId) {
        this.workDayId = workDayId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public List<Clazz> getClazzs() {
        return clazzs;
    }

    public void setClazzs(List<Clazz> clazzs) {
        this.clazzs = clazzs;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public String toString() {
        return "GradeTimetable{" +
                ", workDayId=" + workDayId +
                ", gradeId=" + gradeId +
                "}";
    }
}
