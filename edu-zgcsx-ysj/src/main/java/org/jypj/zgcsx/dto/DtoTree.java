package org.jypj.zgcsx.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/13.
 */
@Data
public class DtoTree {

    private String name;
    private String code;
    private List<DtoTree> subTree;

}
