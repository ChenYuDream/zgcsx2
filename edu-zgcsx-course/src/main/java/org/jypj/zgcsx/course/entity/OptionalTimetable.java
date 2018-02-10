package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.List;

/**
 * <p>
 * 课程时间表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_OPTIONAL_TIMETABLE")
public class OptionalTimetable extends BaseEntity<OptionalTimetable> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程属性表ID
     */
    @TableField("OPTIONAL_COURSE_ID")
    private String optionalCourseId;
    /**
     * 日程ID
     */
    @TableField("WORK_DAY_ID")
    private String workDayId;
    /**
     * 日程
     */
    @TableField(exist = false)
    private WorkDay workDay;
    /**
     * 学生选课
     */
    @TableField(exist = false)
    private List<StudentCourse> studentCourses;

    public String getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(String optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }

    public String getWorkDayId() {
        return workDayId;
    }

    public void setWorkDayId(String workDayId) {
        this.workDayId = workDayId;
    }

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    @Override
    public String toString() {
        return "OptionalTimetable{" +
                ", optionalCourseId=" + optionalCourseId +
                ", workDayId=" + workDayId +
                "}";
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }
}
