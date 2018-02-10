package org.jypj.zgcsx.service.impl;

import org.jypj.zgcsx.dao.ResourceDao;
import org.jypj.zgcsx.dto.DtoMenu;
import org.jypj.zgcsx.entity.Resource;
import org.jypj.zgcsx.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian_wu on 2017/11/24.
 * @author jian_wu
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public DtoMenu selcetMenu(String roleId) {
        List<Resource> resources = resourceDao.selectByRoleId(roleId);
        List<DtoMenu> menus = new ArrayList<>();
        List<DtoMenu> resultMenu = new ArrayList<>();
        if(resources!=null&&resources.size()>0){
            resources.stream().forEach(n->{
                menus.add(DtoMenu.transfer(n));
            });
            DtoMenu rootMenu;
            for(DtoMenu dtoMenu:menus){
                if("root".equals(dtoMenu.getParent())){
                    rootMenu=dtoMenu;
                    DtoMenu.toTree(menus,rootMenu);
                    resultMenu.addAll(rootMenu.getSubMenu());
                }
            }
        }
        DtoMenu dtoMenu = new DtoMenu();
        dtoMenu.setName("root");
        dtoMenu.setSubMenu(resultMenu);
        return dtoMenu;
    }


}
