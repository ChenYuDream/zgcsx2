package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 班级表
 *
 * @author yu_chen
 * @create 2017-11-22 10:52
 **/
@Data
@TableName("ZXXX_BJXX")
public class Clazz {

    @TableId
    private String id;
    /**
     * 级部ID
     */
    @TableField("jbid")
    private String gradeId;
    /**
     * 班级名称
     */
    @TableField("bjmc")
    private String name;

    /**
     * 年级名称
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 学校ID
     */
    @TableField(exist = false)
    private String schoolId;

    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String schoolName;

    /**
     * 校区ID
     */
    @TableField("XQID")
    private String campusId;

    /**
     * 校区名称
     */
    @TableField(exist = false)
    private String campusName;
}
