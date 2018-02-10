package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 学生表
 *
 * @author yu_xiao
 * @create 2017-11-28
 **/
@TableName("ZXXS_JBXX")
@Data
public class Student {

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 学生姓名
     */
    @TableField("XM")
    private String name;
    /**
     * 学生别名
     */
    @TableField("CYM")
    private String aliasName;
    /**
     * 学籍号
     */
    @TableField("XJH")
    private String code;
    /**
     * 国家学籍号
     */
    @TableField("GJXJH")
    private String studentCode;
    /**
     * 国家学籍号
     */
    @TableField("SFZJH")
    private String idCard;
    /**
     * 性别
     */
    @TableField("XB")
    private String sex;
    /**
     * 校区ID
     */
    @TableField("XQID")
    private String campusId;
    /**
     * 年级ID
     */
    @TableField("JBID")
    private String gradeId;
    /**
     * 班级ID
     */
    @TableField("BJID")
    private String clazzId;
    /**
     * 联系电话
     */
    @TableField("LXDH")
    private String mobile;
    /**
     * 校区
     */
    @TableField(exist = false)
    private String campusName;
    /**
     * 年级
     */
    @TableField(exist = false)
    private String gradeName;
    /**
     * 班级
     */
    @TableField(exist = false)
    private String clazzName;
    /**
     * 日程id
     */
    @TableField(exist = false)
    private String workDayId;

}
