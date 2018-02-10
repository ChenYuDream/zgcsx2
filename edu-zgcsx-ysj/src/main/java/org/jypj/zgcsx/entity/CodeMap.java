package org.jypj.zgcsx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 代码映射表实体类
 *
 * @author huhao
 */
@Data
public class CodeMap implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1011965179779858429L;

    private String codeId;
    private String itemValue;
    private String itemText;


}
