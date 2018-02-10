package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 学生选课表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_STUDENT_COURSE")
public class StudentCourse extends BaseEntity<StudentCourse> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程属性表ID
     */
    @TableField("OPTIONAL_COURSE_ID")
    private String optionalCourseId;
    /**
     * 学生表ID
     */
    @TableField("STUDENT_ID")
    private String studentId;
    /**
     * 选课类别
     */
    @TableField("COURSE_TYPE")
    private String courseType;
    /**
     * 筛选教师ID
     */
    @TableField("TEACHER_ID")
    private String teacherId;
    /**
     * 筛选标识 1 通过 2 不通过
     */
    @TableField("STATUS")
    private String status;
    /**
     * 教师意见
     */
    @TableField("AUDIT_REASON")
    private String auditReason;
    /**
     * 是否自动分配
     */
    @TableField("AUTO_ALLOT")
    private String autoAllot;
    /**
     * 年级名称（冗余保存，用于历史记录查询）
     */
    @TableField("GRADE_NAME")
    private String gradeName;
    /**
     * 教师筛选时间
     */
    @TableField("AUDIT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    /**
     * 学生信息
     */
    @TableField(exist = false)
    private Student student;
    /**
     * 课程信息
     */
    @TableField(exist = false)
    private OptionalCourse optionalCourse;

    /**
     * 是否选课
     */
    @TableField(exist = false)
    private Boolean chosen;

    /**
     * 日程id
     */
    @TableField(exist = false)
    private String workDayId;

    /**
     * 考勤描述
     */
    @TableField(exist = false)
    private String description;

    /**
     * 是否显示缺勤学生信息
     * true ：过滤缺勤学生
     */
    @TableField(exist = false)
    private boolean notShow;

    /**
     * 学生姓名
     */
    @TableField(exist = false)
    private String studentName;

    public String getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(String optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getWorkDayId() {
        return workDayId;
    }

    public void setWorkDayId(String workDayId) {
        this.workDayId = workDayId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNotShow() {
        return notShow;
    }

    public void setNotShow(boolean notShow) {
        this.notShow = notShow;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                ", optionalCourseId=" + optionalCourseId +
                ", studentId=" + studentId +
                ", courseType=" + courseType +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", auditReason=" + auditReason +
                ", auditTime=" + auditTime +
                "}";
    }

    public OptionalCourse getOptionalCourse() {
        return optionalCourse;
    }

    public void setOptionalCourse(OptionalCourse optionalCourse) {
        this.optionalCourse = optionalCourse;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }

    public String getAutoAllot() {
        return autoAllot;
    }

    public void setAutoAllot(String autoAllot) {
        this.autoAllot = autoAllot;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
