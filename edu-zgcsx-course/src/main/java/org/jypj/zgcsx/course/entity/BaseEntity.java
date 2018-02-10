package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 20:16
 */
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = -7011380914185428110L;

    @TableId("ID")
    private String id;

    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    @JsonIgnore
    private String createUser;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;

    @TableField(value = "UPDATE_USER", fill = FieldFill.UPDATE)
    @JsonIgnore
    private String updateUser;

    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;

    /**
     * 清除base数据
     */
    public void clearBaseEntity() {
        setId(null);
        setCreateUser(null);
        setUpdateUser(null);
        setCreateTime(null);
        setUpdateTime(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
