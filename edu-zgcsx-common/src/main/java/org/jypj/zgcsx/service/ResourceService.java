package org.jypj.zgcsx.service;

import org.jypj.zgcsx.dto.DtoMenu;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/24.
 */
public interface ResourceService {

    /**
     *  查菜单集合
     *  return dtoMenu
     */
    DtoMenu selcetMenu(String roleId);
}
