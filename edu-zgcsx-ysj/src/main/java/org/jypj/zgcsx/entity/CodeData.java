package org.jypj.zgcsx.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/10.
 */
@Data
public class CodeData {

    private String code;
    private String name;
    private List<CodeDataChild> codeDataChildren;
}

