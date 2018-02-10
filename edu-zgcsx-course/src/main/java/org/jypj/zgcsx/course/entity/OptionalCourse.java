package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 选修课详情表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_OPTIONAL_COURSE")
public class OptionalCourse extends BaseEntity<OptionalCourse> {

    private static final long serialVersionUID = 1L;

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
     * 学校ID
     */
    @TableField("SCHOOL_ID")
    private String schoolId;
    /**
     * 校区ID
     */
    @TableField("CAMPUS_ID")
    @NotEmpty(message = "course.optional.non-campus")
    private String campusId;
    /**
     * 课程定义表ID
     */
    @TableField("COURSE_ID")
    @NotEmpty(message = "course.optional.non-choose-course")
    private String courseId;
    /**
     * 课程类型
     */
    @TableField("COURSE_TYPE")
    private String courseType;
    /**
     * 课程别名
     */
    @TableField("ALIAS_NAME")
    @NotBlank(message = "course.optional.non-alias-name")
    @Length(max = 30, message = "course.optional.length-alias-name")
    private String aliasName;
    /**
     * 课程介绍
     */
    @TableField("DESCRIPTION")
    @Length(max = 1000, message = "course.optional.length-description")
    private String description;
    /**
     * 课程发起人ID
     */
    @TableField("USER_ID")
    private String userId;
    /**
     * 空间管理表ID
     */
    @TableField("SPACE_ID")
    private String spaceId;
    /**
     * 课程事务提醒
     */
    @TableField("COURSE_REMIND")
    private String courseRemind;
    /**
     * 课程开始时间
     */
    @TableField("START_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;
    /**
     * 课程结束时间
     */
    @TableField("END_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
    /**
     * 课程状态
     */
    @TableField("COURSE_STATUS")
    private String courseStatus;
    /**
     * 封面ID
     */
    @TableField("COVER_ID")
    private String coverId;
    /**
     * 选课开始时间
     */
    @TableField("CHOOSE_START_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "course.optional.non-choose-start-time")
    private LocalDateTime chooseStartTime;
    /**
     * 选课结束时间
     */
    @TableField("CHOOSE_END_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "course.optional.non-choose-end-time")
    private LocalDateTime chooseEndTime;
    /**
     * 通知等级
     */
    @TableField("NOTICE_LEVEL")
    private String noticeLevel;
    /**
     * 人数限制
     */
    @TableField("LIMIT_COUNT")
    @Min(value = 1, message = "course.optional.min-limit-count")
    private Integer limitCount;
    /**
     * 课程允许的学生性别
     */
    @TableField("LIMIT_SEX")
    private String limitSex;
    /**
     * 所在空间
     */
    @TableField(exist = false)
    private Space space;
    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String schoolName;
    /**
     * 校区名称
     */
    @TableField(exist = false)
    private String campusName;
    /**
     * 课程定义
     */
    @TableField(exist = false)
    private BaseCourse baseCourse;
    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;
    /**
     * 学习类别
     */
    @TableField(exist = false)
    private String xxlb;
    /**
     * 选课学生人数
     */
    @TableField(exist = false)
    private Integer studentCount;
    /**
     * 课程未满数量（课程最大人数-已选学生数量）
     */
    @TableField(exist = false)
    private Integer lackCount;
    /**
     * 任课老师姓名
     */
    @TableField(exist = false)
    private List<Teacher> teachers;
    /**
     * 允许选修的班级列表
     */
    @TableField(exist = false)
    private List<Clazz> clazzes;
    /**
     * 时间表
     */
    @TableField(exist = false)
    private List<WorkDay> workDays;
    /**
     * 评价要素
     */
    @TableField(exist = false)
    private List<EvaluateElement> evaluateElements;

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getChooseStartTime() {
        return chooseStartTime;
    }

    public void setChooseStartTime(LocalDateTime chooseStartTime) {
        this.chooseStartTime = chooseStartTime;
    }

    public LocalDateTime getChooseEndTime() {
        return chooseEndTime;
    }

    public void setChooseEndTime(LocalDateTime chooseEndTime) {
        this.chooseEndTime = chooseEndTime;
    }

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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getCourseRemind() {
        return courseRemind;
    }

    public void setCourseRemind(String courseRemind) {
        this.courseRemind = courseRemind;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public String getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(String noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public String getLimitSex() {
        return limitSex;
    }

    public void setLimitSex(String limitSex) {
        this.limitSex = limitSex;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getXxlb() {
        return xxlb;
    }

    public void setXxlb(String xxlb) {
        this.xxlb = xxlb;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getLackCount() {
        return lackCount;
    }

    public void setLackCount(Integer lackCount) {
        this.lackCount = lackCount;
    }

    @Override
    public String toString() {
        return "OptionalCourse{" +
                ", xn=" + xn +
                ", xq=" + xq +
                ", schoolId=" + schoolId +
                ", campusId=" + campusId +
                ", courseId=" + courseId +
                ", courseType=" + courseType +
                ", aliasName=" + aliasName +
                ", description=" + description +
                ", userId=" + userId +
                ", spaceId=" + spaceId +
                ", courseRemind=" + courseRemind +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", courseStatus=" + courseStatus +
                ", coverId=" + coverId +
                ", chooseStartTime=" + chooseStartTime +
                ", chooseEndTime=" + chooseEndTime +
                ", noticeLevel=" + noticeLevel +
                ", limitCount=" + limitCount +
                ", limitSex=" + limitSex +
                "}";
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<WorkDay> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<WorkDay> workDays) {
        this.workDays = workDays;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public BaseCourse getBaseCourse() {
        return baseCourse;
    }

    public void setBaseCourse(BaseCourse baseCourse) {
        this.baseCourse = baseCourse;
    }

    public List<EvaluateElement> getEvaluateElements() {
        return evaluateElements;
    }

    public void setEvaluateElements(List<EvaluateElement> evaluateElements) {
        this.evaluateElements = evaluateElements;
    }
}
