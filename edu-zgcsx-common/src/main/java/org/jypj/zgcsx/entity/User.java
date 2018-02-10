package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by jian_wu on 2017/11/16.
 */
@Data
@TableName("EIMS_USER")
public class User {
    private String id;
    private String userid;
    //登录名
    private String loginname;
    private String password;
    private String passwordReal;
    private String username;
    //姓名
    private String nickname;
    //角色代码  0000管理员，0001教师，0002学生
    private String usertype;
    /**
     * 背景图片编号
     */
    private String backgroundImage;
}
