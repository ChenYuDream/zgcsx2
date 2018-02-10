package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程资源表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_COURSE_RESOURCE")
public class CourseResource extends BaseEntity<CourseResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程定义表ID
     */
    @TableField("COURSE_ID")
    private String courseId;
    /**
     * 课程属性表ID
     */
    @TableField("OPTIONAL_COURSE_ID")
    private String optionalCourseId;
    /**
     * 班级ID
     */
    @TableField("CLAZZ_ID")
    private String clazzId;

    /**
     * 年级ID
     */
    @TableField("GRADE_ID")
    private String gradeId;
    /**
     * 课程类型
     */
    @TableField("COURSE_TYPE")
    private String courseType;
    /**
     * 上传文件类型
     */
    @TableField("UPLOAD_FILE_TYPE")
    private String uploadFileType;
    /**
     * 上传对象类型
     */
    @TableField("UPLOAD_TYPE")
    private String uploadType;
    /**
     * 操作人类型
     */
    @TableField("CREATE_TYPE")
    private String createType;
    /**
     * 附件ID
     */
    @TableField("ATTACHMENT_ID")
    private String attachmentId;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;
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
     * 上传文件：课程名称
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 上传文件：文件类型
     */
    @TableField(exist = false)
    private String uploadFileName;

    /**
     * 课程别名
     */
    @TableField(exist = false)
    private String aliasName;

    /**
     * 校区名称
     */
    @TableField(exist = false)
    private String campusName;

    /**
     * 年级名称
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 班级名称
     */
    @TableField(exist = false)
    private String clazzName;

    /**
     * 学生姓名
     */
    @TableField(exist = false)
    private String studentName;

    /**
     * 上传文件：详情
     */
    @TableField(exist = false)
    private Attachment attachment;

    /**
     * 教师评价学生资源
     */
    @TableField(exist = false)
    private Evaluate evaluate;

    /**
     * 教师评价学生资源 集合
     */
    @TableField(exist = false)
    List<Map<String, String>> evaluates;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(String optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getUploadFileType() {
        return uploadFileType;
    }

    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }

    public List<Map<String, String>> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Map<String, String>> evaluates) {
        this.evaluates = evaluates;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "CourseResource{" +
                ", courseId=" + courseId +
                ", optionalCourseId=" + optionalCourseId +
                ", clazzId=" + clazzId +
                ", gradeId=" + gradeId +
                ", courseType=" + courseType +
                ", uploadFileType=" + uploadFileType +
                ", uploadType=" + uploadType +
                ", createType=" + createType +
                ", attachmentId=" + attachmentId +
                ", userId=" + userId +
                ", xn=" + xn +
                ", xq=" + xq +
                "}";
    }
}
