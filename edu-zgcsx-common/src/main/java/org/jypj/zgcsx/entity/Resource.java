package org.jypj.zgcsx.entity;

import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.google.gson.Gson;
import lombok.Data;

/**
* 系统资源维护表
* @author yu_chen
* @create 2017-11-24 10:36
**/
@TableName("EIMS_RESOURCE_T")
@Data
public class Resource implements java.io.Serializable{

    /**资源维护表ID*/
    private String id;
    /**上级应用*/
    private String parent;
    /**应用名称*/
    private String name;
    /**应用编码*/
    private String code;
    /**访问地址*/
    private String url;
    /**图片路径*/
    private String imageUrl;
    /**应用类型*/
    private String type;
    /**平台分类*/
    private String plattype;
    /**是否有效*/
    private String active;
    /**显示顺序*/
    private BigDecimal sortorder;
    /**描述*/
    private String descr;
    /**创建人*/
    private String createUser;
    /**创建时间*/
    private Date createTime;
    /**修改人*/
    private String modifyUser;
    /**修改时间*/
    private Date modifyTime;
    /**系统ID*/
    private String systemid;
    /**快捷图标路径*/
    private String shortcutUrl;
    /**是否叶子节点*/
    private String leaf;
    /**级别*/
    private BigDecimal grade;
    /**系统地址*/
    private String systemIp;


}