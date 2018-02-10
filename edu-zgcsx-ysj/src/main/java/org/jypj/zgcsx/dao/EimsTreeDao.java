package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.EimsTree;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/10.
 */
public interface EimsTreeDao extends BaseMapper<EimsTree> {

    List<EimsTree> getTreeData();

    List<EimsTree> queryChildenTree(String nodeId);

    List<EimsTree> queryFatherTree(String nodeId);

}
