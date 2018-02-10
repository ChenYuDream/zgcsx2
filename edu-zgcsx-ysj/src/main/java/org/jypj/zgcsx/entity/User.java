package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.jypj.zgcsx.common.dto.DtoMenu;

import java.util.List;


/**
 * Created by jian_wu on 2017/11/16.
 */
@Data
@TableName("EIMS_USER")
public class User {

    private String userid;
    private String loginname;//登录名
    private String password;
    private String username;
    private String nickname; //姓名
    private String usertype;//角色代码  0000管理员，0001教师，0002学生

    //以下字段不自动匹配数据库
    @TableField(exist = false)
    private String xqid;  //校区id
    @TableField(exist = false)
    private String xxid;  //学校id
    @TableField(exist = false)
    private String xn;    //学年
    @TableField(exist = false)
    private String xq;    //学期

    //以下为角色菜单字段
    @TableField(exist = false)
    private List<DtoMenu> menuList;
    @TableField(exist = false)
    private String roleSize;
    @TableField(exist = false)
    private String roleName;
    @TableField(exist = false)
    private String roleId;

}
