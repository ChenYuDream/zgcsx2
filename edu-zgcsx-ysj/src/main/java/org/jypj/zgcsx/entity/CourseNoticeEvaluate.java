package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
public class CourseNoticeEvaluate extends Model<CourseNoticeEvaluate> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 通知表ID
     */
	private String noticeId;
    /**
     * 通知人员ID
     */
	private String noticePersonId;
    /**
     * 评价 1、优秀 2、良好 3、合格 4、不合格
     */
	private String evaluate;
    /**
     * 评价内容
     */
	private String content;
    /**
     * 创建时间
     */
	private Date ctime;
    /**
     * 创建人员
     */
	private String createuser;

	@TableField(exist = false)
	private String name;

	@TableField(exist = false)
	private String bm;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
