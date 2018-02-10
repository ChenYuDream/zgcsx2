package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * 级部
 *
 * @author yu_chen
 * @create 2017-11-22 10:35
 **/
@Data
@TableName("ZXXX_NJXX")
public class Grade {
    /**
     * 级部ID
     */
    @TableId
    private String id;

    /**
     * 级部名称
     */
    @TableField("jbmc")
    private String name;

    /**
     * 校区id
     */
    @TableField("xqid")
    private String campusId;

    /**
     * 校区名称
     */
    @TableField(exist = false)
    private String campusName;

    /**
     * 所属年级
     */
    @TableField("SSNJ")
    private Integer gradeNum;

    /**
     * 班级列表
     */
    @TableField(exist = false)
    private List<Clazz> clazzList;
}