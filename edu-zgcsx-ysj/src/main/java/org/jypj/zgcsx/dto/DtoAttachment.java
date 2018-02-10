package org.jypj.zgcsx.dto;

import lombok.Data;
import org.jypj.zgcsx.entity.CourseNoticeAttachment;

import java.text.SimpleDateFormat;

/**
 * Created by jian_wu on 2017/11/14.
 */
@Data
public class DtoAttachment {

    private String fileId;

    private String filePath;

    private String fileName;

    private String fileType;

    private String createTime;

    private String createUser;

    private String createUserId;

    public static DtoAttachment transfer(CourseNoticeAttachment courseNoticeAttachment){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DtoAttachment dtoAttachment = new DtoAttachment();
        dtoAttachment.setFileId(courseNoticeAttachment.getId());
        dtoAttachment.setFileName(courseNoticeAttachment.getFileName());
        dtoAttachment.setFilePath(courseNoticeAttachment.getPath());
        dtoAttachment.setFileType(courseNoticeAttachment.getSuffix());
        dtoAttachment.setCreateUser(courseNoticeAttachment.getCreateUser());
        dtoAttachment.setCreateTime(format.format(courseNoticeAttachment.getCtime()));
        dtoAttachment.setCreateUserId(courseNoticeAttachment.getCreateUserId());
        return dtoAttachment;
    }

}
