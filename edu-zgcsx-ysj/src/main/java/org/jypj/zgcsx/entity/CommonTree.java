package org.jypj.zgcsx.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/15.
 * 用于处理一般的树形结构
 * 用途1：学校、年级、班级树结构
 */
@Data
public class CommonTree {

    private String id;
    private String nodeName;
    private List<CommonTree> subTree;

}
