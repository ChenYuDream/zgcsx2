package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 课程定义表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_BASE_COURSE")
public class BaseCourse extends BaseEntity<BaseCourse> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    @TableField("COURSE_NAME")
    private String courseName;
    /**
     * 课程介绍
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 三层定义
     */
    @TableField("KCDJ")
    private String kcdj;

    @TableField(exist = false)
    private String courseDefinition;
    /**
     * 课程层次
     */
    @TableField("KCCC")
    private String kccc;

    @TableField(exist = false)
    private String courseLevel;
    /**
     * 六类定义
     */
    @TableField("KCLB")
    private String kclb;

    @TableField(exist = false)
    private String courseCategory;
    /**
     * 学习类别
     */
    @TableField("XXLB")
    private String xxlb;

    @TableField(exist = false)
    private String studyType;
    /**
     * 适用年级
     */
    @TableField("LIMIT_GRADE")
    private String limitGrade;

    /**
     * 教师id
     */
    @TableField(exist = false)
    private String teacherId;

    /**
     * 课程属性id
     */
    @TableField(exist = false)
    private String optionalCourseId;

    /**
     * 课程属性别名
     */
    @TableField(exist = false)
    private String aliasName;

    public BaseCourse() {
    }

    public BaseCourse(String id, String courseName) {
        this.setId(id);
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKcdj() {
        return kcdj;
    }

    public void setKcdj(String kcdj) {
        this.kcdj = kcdj;
    }

    public String getKccc() {
        return kccc;
    }

    public void setKccc(String kccc) {
        this.kccc = kccc;
    }

    public String getKclb() {
        return kclb;
    }

    public void setKclb(String kclb) {
        this.kclb = kclb;
    }

    public String getXxlb() {
        return xxlb;
    }

    public void setXxlb(String xxlb) {
        this.xxlb = xxlb;
    }

    public String getLimitGrade() {
        return limitGrade;
    }

    public void setLimitGrade(String limitGrade) {
        this.limitGrade = limitGrade;
    }

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

    public String getCourseDefinition() {
        return courseDefinition;
    }

    public void setCourseDefinition(String courseDefinition) {
        this.courseDefinition = courseDefinition;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    @Override
    public String toString() {
        return "BaseCourse{" +
                ", courseName=" + courseName +
                ", description=" + description +
                ", kcdj=" + kcdj +
                ", kccc=" + kccc +
                ", kclb=" + kclb +
                ", xxlb=" + xxlb +
                ", limitGrade=" + limitGrade +
                "}";
    }
}
