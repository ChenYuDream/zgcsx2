package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by jian_wu on 2017/11/15.
 */
@Data
@TableName("zxxs_jbxx")
public class Student {
    @TableId
    private String id;
    private String xm;      //姓名
    private String gjxjh;   //国家学籍号
    private String jyid;    //教育id
    private String sfzjh;    //身份证
    private String xb;       //性别
    private String xxid;    //学校id
    private String xqid;     //学区id
    private String jbid;     //级部id（年级id）
    private String bjid;     //班级id
}
