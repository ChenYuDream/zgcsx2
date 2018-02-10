package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * 校区
 *
 * @author yu_chen
 * @create 2017-11-22 10:34
 **/
@Data
@TableName("ZXXX_XQ")
public class SchoolXq {

    /**
     * 校区ID
     */
    @TableId
    private String id;

    /**
     * 校区名称
     */
    @TableField("xqmc")
    private String name;

    /**
     * 学校ID
     */
    @TableField("XXID")
    private String schoolId;

    /**
     * 级部列表
     */
    @TableField(exist = false)
    private List<Grade> gradeList;


}
