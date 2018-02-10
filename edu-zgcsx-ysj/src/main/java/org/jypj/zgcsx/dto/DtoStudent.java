package org.jypj.zgcsx.dto;

import lombok.Data;
import org.jypj.zgcsx.cache.CommonCache;
import org.jypj.zgcsx.entity.CommonTree;
import org.jypj.zgcsx.entity.Student;

/**
 * Created by jian_wu on 2017/11/15.
 */
@Data
public class DtoStudent {

    private String id;
    private String name;  //前端为了统一格式，所以没有使用studentId和studentName
    private String sex;
    private String xjh;  //学籍号
    private Boolean checked = false;
    private String nodeName;

    public static DtoStudent fransfer(Student student){
        DtoStudent dtoStudent = new DtoStudent();
        dtoStudent.setId(student.getId());
        dtoStudent.setName(student.getXm());
        //学生所在节点处理
        StringBuffer sb = new StringBuffer();
        for(CommonTree commonTree: CommonCache.getCampusInfoTree()){
            if(commonTree.getId().equals(student.getXqid())){
                sb.append(commonTree.getNodeName()+"->");
            }
        }
        for(CommonTree commonTree: CommonCache.getGradeInfoTree()){
            if(commonTree.getId().equals(student.getJbid())){
                sb.append(commonTree.getNodeName()+"->");
            }
        }
        for(CommonTree commonTree: CommonCache.getClassInfoTree()){
            if(commonTree.getId().equals(student.getBjid())){
                sb.append(commonTree.getNodeName()+"->");
            }
        }
        if(sb.length()>0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        dtoStudent.setNodeName(sb.toString());
        if("1".equals(student.getXb())){
            dtoStudent.setSex("男");
        }else if("2".equals(student.getXb())){
            dtoStudent.setSex("女");
        }else {
            dtoStudent.setSex("未知");
        }
        dtoStudent.setXjh(student.getGjxjh());
        return dtoStudent;
    }

}
