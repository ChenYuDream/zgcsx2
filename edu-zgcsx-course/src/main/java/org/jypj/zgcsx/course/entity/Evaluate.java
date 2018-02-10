package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 教师评价学生资源表
 * </p>
 *
 */
@TableName("KC_EVALUATE")
public class Evaluate extends BaseEntity<Evaluate> {

    private static final long serialVersionUID = 1L;
    /**
     * 课程资源ID
     */
    @TableField("COURSE_RESOURCE_ID")
    private String courseResourceId;
    /**
     * 学生ID
     */
    @TableField("STUDENT_ID")
    private String studentId;
    /**
     * 评价人ID
     */
    @TableField("TEACHER_ID")
    private String teacherId;
    /**
     * 评价内容 (kc_eva_level)
     */
    @TableField("STATE")
    private String state;
    /**
     * 评价时间
     */
    @TableField(value = "EVALUATE_TIME", fill = FieldFill.UPDATE)
    @JsonIgnore
    private LocalDateTime evaluateTime;

    /**
     * 评价内容 描述
     */
    @TableField(exist = false)
    private String stateName;

    public String getCourseResourceId() {
        return courseResourceId;
    }

    public void setCourseResourceId(String courseResourceId) {
        this.courseResourceId = courseResourceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(LocalDateTime evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
