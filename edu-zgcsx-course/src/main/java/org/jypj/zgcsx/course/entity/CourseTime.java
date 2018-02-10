package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @author yu_chen
 * @create 2017-11-27 10:32
 **/
@TableName("kcxx_kc_sjhs")
@Data
public class CourseTime implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId
    private String id;
    /**
     * 校区id
     */
    @TableField("XQID")
    private String xqid;
    /**
     * 课程所在节次
     */
    @TableField("KCJC")
    private String kcjc;
    /**
     * 课程开始时间
     */
    @TableField("KSSJ")
    private String kssj;
    /**
     * 课程结束时间
     */
    @TableField("JSSJ")
    private String jssj;
    /**
     * 课程所属周次  1.2.3.4.5
     */
    @TableField("KCZC")
    private String kczc;

}