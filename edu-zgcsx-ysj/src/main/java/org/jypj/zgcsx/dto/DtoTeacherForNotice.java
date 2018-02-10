package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.entity.EimsPersonTree;
import org.jypj.zgcsx.entity.EimsTree;
import org.jypj.zgcsx.service.EimsPersonTreeService;
import org.jypj.zgcsx.service.EimsTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/13.
 */
@Data
@Component
public class DtoTeacherForNotice {

    private String id;
    private String name;
    private String sex;
    private String nodeName;
    private Boolean checked = false;


    @Autowired
    @JsonIgnore
    private EimsTreeService treeService;

    public  DtoTeacherForNotice transfer(EimsPersonTree eimsPersonTree){
        DtoTeacherForNotice dtoTeacherForNotice = new DtoTeacherForNotice();
        dtoTeacherForNotice.setId(eimsPersonTree.getTeacherId());
        //别名
        if(StringUtil.isNotEmpty(eimsPersonTree.getTeacherbm())){
            dtoTeacherForNotice.setName(eimsPersonTree.getTeacherName()+"("+eimsPersonTree.getTeacherbm()+")");
        }else{
            dtoTeacherForNotice.setName(eimsPersonTree.getTeacherName());
        }
        //节点名称
        List<EimsTree> trees = treeService.queryFatherTree(eimsPersonTree.getNodeId());
        StringBuffer sb = new StringBuffer();
        //不要中关村三小节点
        if(trees!=null&&trees.size()>1){
            for(int i=trees.size()-2;i>=0;i--){
                sb.append(trees.get(i).getText()+"->");
            }
            if(sb.length()>0) {
                sb.delete(sb.length() - 2, sb.length());
            }
            dtoTeacherForNotice.setNodeName(sb.toString());
        }
        if(trees==null||trees.size()==0){
            dtoTeacherForNotice.setNodeName("非通讯录");
        }

        //性别1男2女，其他全部处理为未知
        if("1".equals(eimsPersonTree.getSex())){
            dtoTeacherForNotice.setSex("男");
        }else if("2".equals(eimsPersonTree.getSex())){
            dtoTeacherForNotice.setSex("女");
        }else {
            dtoTeacherForNotice.setSex("未知");
        }
        return dtoTeacherForNotice;
    }

}
