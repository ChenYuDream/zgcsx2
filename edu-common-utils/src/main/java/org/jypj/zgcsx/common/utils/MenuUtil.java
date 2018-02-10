package org.jypj.zgcsx.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.jypj.zgcsx.common.dto.DtoMenu;

import java.util.List;


/**
 * Created by jian_wu on 2017/11/24.
 * @author jian_wu
 * 此类负责将接口得到的菜单数据转化成公用的菜单属性
 * 实例化时传入公共项目地址 类似：http://172.16.32.125:8080/
 */

public class MenuUtil {

    /**
     * 公用模块项目地址
     */
    private  String commonProjectUrl;

    public MenuUtil(String commonProjectUrl){
        this.commonProjectUrl=commonProjectUrl;
    }

    public MenuUtil(){}

    public void setCommonProjectUrl(String commonProjectUrl) {
        this.commonProjectUrl = commonProjectUrl;
    }

    public String getCommonProjectUrl() {
        return commonProjectUrl;
    }

    /**
     * 获取角色菜单信息
     * @param roleId
     * @return
     */
    public List<DtoMenu> getMenu(String roleId){
        JSONObject jsonObject = HttpUtil.getJson(commonProjectUrl+"/api/menu?roleId="+roleId);
        if(jsonObject!=null){
            DtoMenu menu = jsonObject.getObject("result",DtoMenu.class);
            if(menu.getSubMenu()!=null&&menu.getSubMenu().size()>0){
                return menu.getSubMenu();
            }
        }
        return null;
    }

}
