package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Data
@TableName(value="COURSE_NOTICE_ATTACHMENT",resultMap = "BaseResultMap")
public class CourseNoticeAttachment extends Model<CourseNoticeAttachment> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 通知id
     */
	private String courseNoticeId;
    /**
     * 路径
     */
	private String path;
    /**
     * 文件名称
     */
	private String fileName;
    /**
     * 文件后缀
     */
	private String suffix;
    /**
     * 创建人员
     */
	private String createUser;
	private Date ctime;
    /**
     * 修改人员
     */
	private String modifyUser;
    /**
     * 修改时间
     */
	private Date mtime;
    /**
     * 创建人ID
     */
	private String createUserId;



	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
