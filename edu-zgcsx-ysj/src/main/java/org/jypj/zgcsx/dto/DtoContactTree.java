package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jypj.zgcsx.common.dto.DtoMenu;
import org.jypj.zgcsx.entity.EimsPersonTree;
import org.jypj.zgcsx.entity.EimsTree;
import org.jypj.zgcsx.service.EimsPersonTreeService;
import org.jypj.zgcsx.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/12/12.
 * @author jian_wu
 * 通讯录树结构
 */
@Data
@Component
public class DtoContactTree {

    private String nodeId;
    private String nodeName;
    private String parentId;
    private List<Map<String,String>> teacherNode;
    private List<DtoContactTree> childrenNode;

    public static List<DtoContactTree> transfer(List<EimsTree> list, List<EimsPersonTree> personTrees){
        List<DtoContactTree> resultTree = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)){
            list.stream().forEach(n->{
                DtoContactTree dtoMenu = new DtoContactTree();
                dtoMenu.setNodeId(n.getId());
                dtoMenu.setParentId(n.getParentId());
                dtoMenu.setNodeName(n.getText());
                if(CollectionUtil.isNotEmpty(personTrees)){
                    List<Map<String,String>> teacherNode = new ArrayList<>();
                    personTrees.stream().forEach(m->{
                        if(m.getNodeId().equals(dtoMenu.getNodeId())){
                            Map<String,String> teacherMap = new HashMap<>(16);
                            teacherMap.put("teacherId",m.getTeacherId());
                            String teacherName = m.getTeacherName();
                            if(m.getTeacherbm() != null){
                                teacherName += "("+m.getTeacherbm()+")" ;
                            }
                            teacherMap.put("teacherName",teacherName);
                            teacherNode.add(teacherMap);
                        }
                    });
                    dtoMenu.setTeacherNode(teacherNode);
                }
                resultTree.add(dtoMenu);
            });
        }
        return resultTree;
    }

    /**
     * 封装:将id，parent形式的树结构，转化成父类、子类的树结构
     * 递归赋值
     */
    public static void toTree(List<DtoContactTree> oldList,DtoContactTree rootContact){
        List<DtoContactTree> list = new ArrayList<>();
        for(DtoContactTree dtoContactTree:oldList){
            if(dtoContactTree.getParentId().equals(rootContact.getNodeId())){
                list.add(dtoContactTree);
            }
        }
        rootContact.setChildrenNode(list);
        if(list!=null&&list.size()>0){
            for(DtoContactTree dtoContactTree:rootContact.getChildrenNode()){
                toTree(oldList,dtoContactTree);
            }
        }
    }

}
