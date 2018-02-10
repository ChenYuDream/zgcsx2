package org.jypj.zgcsx.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jian_wu on 2017/11/24.
 * @author jian_wu
 * 公共菜单实体
 */
@Data
public class DtoMenu implements Serializable {
    private static final long serialVersionUID = -1148547058454725530L;
    /**
     * 地址
     */
    private String url;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 图标地址，也可以做div的class使用
     */
    private String imageUrl;
    /**
     * 子节点
     */
    private List<DtoMenu> subMenu;

}
