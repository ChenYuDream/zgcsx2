package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_ATTACHMENT")
public class Attachment extends BaseEntity<Attachment> {

    private static final long serialVersionUID = 1L;

    /**
     * 上传人ID
     */
    @TableField("USER_ID")
    private String userId;
    /**
     * 上传时间
     */
    @TableField("UPLOAD_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;
    /**
     * 文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;
    /**
     * 附件大小
     */
    @TableField("FILE_SIZE")
    private Double fileSize;
    /**
     * 附件描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 文件类型
     */
    @TableField("FILE_TYPE")
    private String fileType;
    /**
     * 附件地址
     */
    @TableField("FILE_PATH")
    private String filePath;
    /**
     * 缩略图地址
     */
    @TableField("FILE_PATH_S")
    private String filePathS;

    /**
     * 上传人名称
     */
    @TableField(exist = false)
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePathS() {
        return filePathS;
    }

    public void setFilePathS(String filePathS) {
        this.filePathS = filePathS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                ", userId=" + userId +
                ", uploadTime=" + uploadTime +
                ", fileName=" + fileName +
                ", fileSize=" + fileSize +
                ", description=" + description +
                ", fileType=" + fileType +
                ", filePath=" + filePath +
                ", filePathS=" + filePathS +
                "}";
    }
}
