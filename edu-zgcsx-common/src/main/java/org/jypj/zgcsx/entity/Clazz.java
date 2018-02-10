package org.jypj.zgcsx.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.google.gson.Gson;
import lombok.Data;

/**
* 班级信息表
* @author yu_chen
* @create 2017-11-28 15:46
**/
@TableName("zxxx_bjxx")
@Data
public class Clazz implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

    /***/
    private String id;
    /**学校ID*/
    private String xxid;
    /**校区ID*/
    private String xqid;
    /**学制*/
    private String xz;
    /**双语教学模式码*/
    private String syjxmsm;
    /**建班年月*/
    private Date jbny;
    /**所属年级*/
    private String ssnj;
    /**文理类型*/
    private String wllx;
    /**是否少数民族双语教学班*/
    private String sfssmzsyjxb;
    /**毕业日期*/
    private Date byrq;
    /**班主任*/
    private String bzr;
    /**班主任ID*/
    private String bzrid;
    /**副班主任*/
    private String fbzr;
    /**副班主任ID*/
    private String fbzrid;
    /**班级名称*/
    private String bjmc;
    /**班级类别码*/
    private String bjlbm;
    /**班级荣誉称号*/
    private String bjrych;
    /***/
    private String bzid;
    /**班组群ID*/
    private String bzqid;
    /**级部ID*/
    private String jbid;
    /**校中校ID*/
    private String xzxid;
    /**班级状态*/
    private String bjzt;
    /**审核标记*/
    private String flag;
    /**是否有效*/
    private String valid;




}