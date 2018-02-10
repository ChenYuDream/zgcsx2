package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
    private String mzm;
    @TableField("SFZJH")
    private String idCard;
    @TableField("LXDH")
    private String phoneNum;
    private String xxid;
    private String xqid;
    /**
     * 教的课程名称
     */
    @TableField(exist = false)
    private String kcmc;
    //以下是非数据库字段
    /**
     * 年级（例如：一年级，二年级）
     */
    @TableField(exist = false)
    private String grade;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
