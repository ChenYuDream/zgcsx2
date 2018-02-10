package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程通知人员表
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Data
@TableName(value="course_notice_person",resultMap = "BaseResultMap")
public class CourseNoticePerson extends Model<CourseNoticePerson> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 通知表主表ID
     */
	private String noticeId;
    /**
     * 通知人员ID
     */
	private String personId;
    /**
     * 通知人员类型
     */
	private String noticePersonType;
    /**
     * 通知是否已读标示
     */
	private String noticeRead;
    /**
     * 数据创建时间
     */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date ctime;
    /**
     * 数据修改时间
     */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date mtime;
    /**
     * 事件反馈操作 1同意该事件 2不同意该事件
     */
	private String oper;
    /**
     * 操作理由
     */
	private String reason;

	/**
	 * 姓名
	 */
	@TableField(exist = false)
	private String name;
	/**
	 * 别名
	 */
	@TableField(exist = false)
	private String bm;
	/**
	 * 类型
	 */
	@TableField(exist = false)
	private String type;
	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
