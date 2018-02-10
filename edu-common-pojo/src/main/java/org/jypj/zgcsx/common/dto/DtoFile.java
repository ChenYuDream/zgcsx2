package org.jypj.zgcsx.common.dto;

import lombok.Data;

/**
 * Created by jian_wu on 2017/11/23.
 * @author jian_wu
 * 作为文件上传成功后的返回数据
 */
@Data
public class DtoFile {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件类型 带'.'
     */
    private String fileType;
    /**
     * 文件原始名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 缩略图path
     */
    private String smallImagePath;
    /**
     * 是否是缩略图
     * 默认不是
     */
    private Boolean smallImage = false;

}
