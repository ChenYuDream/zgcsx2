package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 空间管理表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("SPACE_MANAGE")
public class Space implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 地点名称
     */
    @TableField("MC")
    private String name;
    /**
     * 描述
     */
    @TableField("DESCRIBE")
    private String description;
    /**
     * 描述
     */
    @TableField("ADDR")
    private String address;
    /**
     * 容量
     */
    @TableField("VOLUME")
    private Integer volume;

    @TableField("CAMPUS")
    private String campusCode;
    @TableField("ARCHITECTURE")
    private String archCode;
    @TableField("SPTYPE")
    private String typeCode;
    @TableField("FLOORID")
    private String floorCode;
    @TableField("BUILDINGID")
    private String code;

    @TableField(exist = false)
    private String campusName;
    @TableField(exist = false)
    private String archName;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String floorName;
    @TableField("FLAG")
    private Boolean repeatable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getArchCode() {
        return archCode;
    }

    public void setArchCode(String archCode) {
        this.archCode = archCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getArchName() {
        return archName;
    }

    public void setArchName(String archName) {
        this.archName = archName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '"' +
                ",\"name\":\"" + name + '"' +
                ",\"description\":\"" + description + '"' +
                ",\"volume\":\"" + volume + '"' +
                ",\"campusCode\":\"" + campusCode + '"' +
                ",\"archCode\":\"" + archCode + '"' +
                ",\"typeCode\":\"" + typeCode + '"' +
                ",\"floorCode\":\"" + floorCode + '"' +
                ",\"campusName\":\"" + campusName + '"' +
                ",\"archName\":\"" + archName + '"' +
                ",\"typeName\":\"" + typeName + '"' +
                ",\"floorName\":\"" + floorName + '"' +
                '}';
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
