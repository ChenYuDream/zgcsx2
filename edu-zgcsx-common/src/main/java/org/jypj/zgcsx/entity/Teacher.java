package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;


/**
 * Created by jian_wu on 2017/11/21.
 */
@Data
@TableName(value="ZXJZ_JBXX")
public class Teacher extends Model<Teacher> {

    private String id;
    @TableField("XM")
    private String teacherName;
    /**
     * 别名
     */
    private String bm;
    @TableField("XBM")

    private String sex;
    /**
     * 名族
     */
    @JsonIgnore
    private String mzm;
    @TableField("SFZJH")
    @JsonIgnore
    private String idCard;
    @TableField("LXDH")
    @JsonIgnore
    private String phoneNum;
    @JsonIgnore
    private String xxid;
    @JsonIgnore
    private String xqid;

    //以下是非数据库字段
    /**
     * 年级（例如：一年级，二年级）
     */
    @TableField(exist = false)
    @JsonIgnore
    private String grade;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
