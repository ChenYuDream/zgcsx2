package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通讯录树形结构教师人员映射表
 *
 */
@Data
public class EimsPersonTree extends Model<EimsPersonTree> {
	
	private String id;
	//节点ID
	@TableField("NODEID")
	private String nodeId;
	//节点名称
	@TableField("NODENAME")
	private String nodeName;
	//教师ID
	@TableField("TEACHERID")
	private String teacherId;
	//教师名称
	@TableField("TEACHERNAME")
	private String teacherName;
	//教师别名
	private String teacherbm;
	/**
	 * 创建时间
	 */
	private Date ctime;
	@TableField("CREATERUSER")
	//创建人员
	private String creatUser;
	/**
	 * 修改时间
	 */
	private Date mtime;
	//修改人员
	@TableField("MODIFYUSER")
	private String modifyUser;
	//性别码
	@TableField(exist = false)
	private String sex;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
