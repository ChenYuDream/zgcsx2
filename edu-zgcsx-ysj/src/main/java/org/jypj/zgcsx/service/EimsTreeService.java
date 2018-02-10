package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.EimsTree;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/10.
 */
public interface EimsTreeService extends IService<EimsTree> {

    List<EimsTree> getTreeData();

    List<EimsTree> getTreeDataWithOutNoContact();

    List<EimsTree> queryChildenTree(String nodeId);

    List<EimsTree> queryFatherTree(String nodeId);

    /**
     * 删除节点及其以下节点，及其节点的所有的教师
     * @param nodeId
     * @return
     */
    int delNodeWithTeacher(String nodeId);

    /**
     *
     * @return
     */
    int updateNode(EimsTree eimsTree);

}
