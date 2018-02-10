package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * 空间管理
 *
 * @author ChenYu
 */
@Data
@TableName("space_manage")
public class Space {

    /**
     * /数据自身ID
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 名称
     */
    @TableField("mc")
    private String mc;

    /**
     * 描述
     */
    @TableField("describe")
    private String describe;
    /**
     * 容量
     */
    @TableField("volume")
    private Integer volume;
    /**
     * 设备情况
     */
    @TableField("content")
    private String content;
    /**
     * 数据创建时间
     */
    @TableField("ctime")
    private Date ctime;
    /**
     * 数据修改时间
     */
    @TableField(value = "mtime")
    private Date mtime;
    /**
     * 地址
     */
    @TableField("addr")
    private String addr;
    @TableField("xxid")
    private String xxid;
    @TableField("xqid")
    private String xqid;
    /**
     * 是否允许重复
     */
    @TableField("flag")
    private String flag;
    /**
     * 校区
     */
    @TableField("campus")
    private String campus;
    /**
     * 建筑楼
     */
    @TableField("architecture")
    private String architecture;
    /**
     * 类型
     */
    @TableField("sptype")
    private String sptype;
    /**
     * 楼层
     */
    @TableField("floorid")
    private String floorid;
    /**
     * 编号
     */
    @TableField("buildingid")
    private String buildingid;

    /**
     * 校区
     */
    @TableField(exist = false)
    private String campusTag;
    /**
     * 建筑楼
     */
    @TableField(exist = false)
    private String architectureTag;
    /**
     * 类型
     */
    @TableField(exist = false)
    private String sptypeTag;
    /**
     * 楼层
     */
    @TableField(exist = false)
    private String flooridTag;

}
