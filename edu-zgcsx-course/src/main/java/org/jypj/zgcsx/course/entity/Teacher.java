package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 教师表
 *
 * @author yu_chen
 * @create 2017-11-22 9:43
 **/
@TableName("ZXJZ_JBXX")
@Data
public class Teacher {

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 教师姓名
     */
    @TableField("XM")
    private String name;
    /**
     * 教师曾用名
     */
    @TableField("CYM")
    private String oldName;
    /**
     * 教师别名
     */
    @TableField("BM")
    private String aliasName;
    /**
     * 性别
     */
    @TableField("XBM")
    private String sex;
    /**
     * 证件号码
     */
    @TableField("SFZJH")
    private String idCard;

    /**
     * 校区id
     */
    @TableField(exist = false)
    private String campusId;
    /**
     * 校区名称
     */
    @TableField(exist = false)
    private String campusName;
}
