package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程通知表
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Data
@TableName(value="COURSE_NOTICE",resultMap = "BaseResultMap")
public class CourseNotice extends Model<CourseNotice> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private String id;
    /**
     * 通知标题
     */
	private String noticeTitle;
    /**
     * 通知内容
     */
	private String noticeContent;
    /**
     * 通知有效期起
     */
	private Date noticeStart;
    /**
     * 通知有效期止
     */
	private Date noticeEnd;
    /**
     * 是否提醒
     */
	private Integer noticeRemind;
    /**
     * 是否置顶
     */
	private Integer noticeStick;
    /**
     * 置顶时间
     */
	private Date noticeStickTime;
    /**
     * 多少天结束置顶
     */
	private Integer noticeStickEnddays;
    /**
     * 通知搜索关键词
     */
	private String noticeKeyword;
    /**
     * 通知发布状态
     */
	private String noticepubstate;
    /**
     * 链接路径
     */
	private String noticedetailurl;
    /**
     * 附件路径
     */
	private String attachpath;
    /**
     * 发布通知学校
     */
	private String xxid;
    /**
     * 发布通知校区
     */
	private String xqid;
    /**
     * 数据创建时间
     */
	private Date ctime;
    /**
     * 数据修改时间
     */
	private Date mtime;
    /**
     * 地点
     */
	private String place;
    /**
     * 学年
     */
	private String cimsXn;
    /**
     * 学期
     */
	private String cimsXq;
    /**
     * 创建人
     */
	private String creater;
    /**
     * 通知等级
     */
	private String noticeLevel;
    /**
     * 代理事件发起人
     */
	private String proxy;
    /**
     * 事件体系（1课程发展，2学生发展，3学校发展，4教师发展，5学校日常管理）
     */
	private String evenType;
    /**
     * 事件类型(1普通消息类型 2活动消息类型)
     */
	private String evenStyle;
    /**
     * 事件级别(1班级事件 2班组群事件  3级部事件 4校中校事件 5校级事件)
     */
	private String evenLevel;
    /**
     * 事件性质 1个人事件 2部门事件
     */
	private String evenProperty;
    /**
     * 空间地点ID
     */
	private String placeId;
    /**
     * 发布类型：1、发布的事件2、选修课发布3、社团发布4、社团活动 5、课后一小时 6、管理班 7、主题活动
     */
	private String fbtype;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
