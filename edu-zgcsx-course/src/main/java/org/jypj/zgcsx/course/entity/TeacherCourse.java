package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 老师任课表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_TEACHER_COURSE")
public class TeacherCourse extends BaseEntity<TeacherCourse> {

    private static final long serialVersionUID = 1L;

    /**
     * 教师ID
     */
    @TableField("TEACHER_ID")
    private String teacherId;
    /**
     * 课程属性表ID
     */
    @TableField("OPTIONAL_COURSE_ID")
    private String optionalCourseId;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 教师名称
     */
    @TableField(exist = false)
    private String teacherName;

    /**
     * 当前周
     */
    @TableField(exist = false)
    private String week;

    /**
     * 课程详情
     */
    @TableField(exist = false)
    private OptionalCourse optionalCourse;

    /**
     * 日程
     */
    @TableField(exist = false)
    private WorkDay workDay;

    /**
     * 已选学生数量
     */
    @TableField(exist = false)
    private Integer chooseStudentCount;

    /**
     * 课程层次
     */
    @TableField(exist = false)
    private String itemText;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(String optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public OptionalCourse getOptionalCourse() {
        return optionalCourse;
    }

    public void setOptionalCourse(OptionalCourse optionalCourse) {
        this.optionalCourse = optionalCourse;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getChooseStudentCount() {
        return chooseStudentCount;
    }

    public void setChooseStudentCount(Integer chooseStudentCount) {
        this.chooseStudentCount = chooseStudentCount;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    @Override
    public String toString() {
        return "TeacherCourse{" +
                ", teacherId=" + teacherId +
                ", optionalCourseId=" + optionalCourseId +
                "}";
    }
}
