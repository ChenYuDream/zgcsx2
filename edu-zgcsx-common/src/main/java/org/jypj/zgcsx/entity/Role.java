package org.jypj.zgcsx.entity;

import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.google.gson.Gson;
import lombok.Data;

/**
* 系统角色表
* @author yu_chen
* @create 2017-11-24 10:21
**/
@TableName(value="EIMS_ROLE_T")
@Data
public class Role implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

    /**角色表ID*/
    private String id;
    /**角色名称*/
    private String roleName;
    /**角色编码*/
    private String roleCode;
    /**角色类型*/
    private String roleType;
    /**描述*/
    private String descr;
    /**显示顺序*/
    private BigDecimal sortorder;
    /**所属单位*/
    private String org;
    /**所属系统*/
    private String roleSystem;
    /**标示*/
    private String flag;
    /**创建人*/
    private String createUser;
    /**创建时间*/
    private Date createTime;
    /**修改人*/
    private String modifyUser;
    /**修改时间*/
    private Date modifyTime;

}