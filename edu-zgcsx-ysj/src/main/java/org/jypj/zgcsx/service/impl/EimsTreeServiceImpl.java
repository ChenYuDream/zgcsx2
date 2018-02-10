package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.EimsPersonTreeDao;
import org.jypj.zgcsx.dao.EimsTreeDao;
import org.jypj.zgcsx.entity.EimsPersonTree;
import org.jypj.zgcsx.entity.EimsTree;
import org.jypj.zgcsx.service.EimsTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/10.
 */
@Service
public class EimsTreeServiceImpl extends ServiceImpl<EimsTreeDao, EimsTree> implements EimsTreeService {

    @Autowired
    private EimsTreeDao eimsTreeDao;
    @Autowired
    private EimsPersonTreeDao personTreeDao;
    @Override
    public List<EimsTree> getTreeData() {
        List<EimsTree> list = eimsTreeDao.getTreeData();
        //非通讯录教师节点
       /* EimsTree eimsTree = new EimsTree();
        eimsTree.setId("NoContact");
        eimsTree.setText("非通讯录");
        List<EimsTree> parentTree = eimsTreeDao.selectList(new EntityWrapper<EimsTree>().eq("PARENTID","0"));
        if(parentTree!=null&&parentTree.size()>0){
            eimsTree.setParentId(parentTree.get(0).getId());
        }
        eimsTree.setOrderNum(1);
        list.add(eimsTree);*/
        List<EimsTree> resultList = new ArrayList<>();
        //1.总节点
        EimsTree eimsTree = new EimsTree();
        eimsTree.setId("All");
        eimsTree.setText("中关村三小");
        eimsTree.setParentId("0");
        resultList.add(eimsTree);
        //2. 2级节点
        eimsTree = new EimsTree();
        eimsTree.setId("Contact");
        eimsTree.setText("通讯录");
        eimsTree.setParentId("All");
        resultList.add(eimsTree);
        eimsTree = new EimsTree();
        eimsTree.setId("NoContact");
        eimsTree.setText("非通讯录");
        eimsTree.setParentId("All");
        resultList.add(eimsTree);
        // 3.级节点， 通讯录
        List<EimsTree> parentTree = eimsTreeDao.selectList(new EntityWrapper<EimsTree>().eq("PARENTID","0"));
        if(parentTree!=null&&parentTree.size()>0){
            eimsTree =parentTree.get(0);
            String baseParentId = eimsTree.getId();
            //删除中关村三小节点
            int index;
            for(index = 0; index<list.size();index++){
                if(("0").equals(list.get(index).getParentId())){
                    break;
                }
            }
            list.remove(index);
            //parentID为中关村三小的节点变更为Contact
            list.stream().forEach(n->{
                if(n.getParentId().equals(baseParentId)){
                    n.setParentId("Contact");
                }
            });
        }
        resultList.addAll(list);
        return resultList;
    }

    @Override
    public List<EimsTree> getTreeDataWithOutNoContact() {
        return eimsTreeDao.getTreeData();
    }

    @Override
    public List<EimsTree> queryChildenTree(String nodeId) {
        return eimsTreeDao.queryChildenTree(nodeId);
    }

    @Override
    public List<EimsTree> queryFatherTree(String nodeId) {
         return eimsTreeDao.queryFatherTree(nodeId);
    }

    @Override
    public int delNodeWithTeacher(String nodeId) {
        //删除节点
        //1.查出此节点及其所有的子节点
        List<EimsTree> nodeList = this.queryChildenTree(nodeId);
        List<String> nodeIdList = new ArrayList<>();
        nodeList.stream().forEach(n->{
            nodeIdList.add(n.getId());
        });
        //2.先根据nodeids删教师
        if(nodeIdList.size()>0){
            Map<String,Object> queryMap =new HashMap<>(16);
            queryMap.put("nodeIds",nodeIdList);
            personTreeDao.delPersonByNodeIds(queryMap);
        }
        //3.再删节点
        int i =eimsTreeDao.deleteBatchIds(nodeIdList);
        return i;
    }

    @Override
    public int updateNode(EimsTree eimsTree) {
        //1.先更新节点
        int i =eimsTreeDao.updateById(eimsTree);
        //2.更新person_tree中的nodeName
        EimsPersonTree eimsPersonTree = new EimsPersonTree();
        eimsPersonTree.setNodeName(eimsTree.getText());
        personTreeDao.update(eimsPersonTree,new EntityWrapper<EimsPersonTree>().eq("NODEID",eimsTree.getId()));
        return i;
    }

}
