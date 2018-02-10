package org.jypj.zgcsx.dto;

import lombok.Data;
import org.jypj.zgcsx.entity.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian_wu on 2017/11/24.
 * @author jian_wu
 */
@Data
public class DtoMenu {
    private String id;
    private String parent;
    private String url;
    private String name;
    /**
     * 图标地址
     */
    private String imageUrl;
    /**
     * 节点class
     */
    private String divClass;

    /**
     * 子节点
     */
    private List<DtoMenu> subMenu;

    public static DtoMenu transfer(Resource resource){
        DtoMenu dtoMenu = new DtoMenu();
        dtoMenu.setName(resource.getName());
        dtoMenu.setUrl(resource.getUrl());
        dtoMenu.setImageUrl(resource.getImageUrl());
        dtoMenu.setId(resource.getId());
        dtoMenu.setParent(resource.getParent());
        return dtoMenu;
    }

    /**
     * 封装:将id，parent形式的树结构，转化成父类、子类的树结构
     * 递归赋值
     */
    public static void  toTree(List<DtoMenu> oldMenus,DtoMenu rootDtoNemu){
        List<DtoMenu> list = new ArrayList<>();
        for(DtoMenu dtoMenu:oldMenus){
            if(dtoMenu.getParent().equals(rootDtoNemu.getId())){
                list.add(dtoMenu);
            }
        }
        rootDtoNemu.setSubMenu(list);
        if(list!=null&&list.size()>0){
            for(DtoMenu dtoMenu:rootDtoNemu.getSubMenu()){
                toTree(oldMenus,dtoMenu);
            }
        }
    }

}
