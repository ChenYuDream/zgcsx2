package org.jypj.zgcsx.dto;


import lombok.Data;
import org.jypj.zgcsx.cache.CommonCache;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.entity.CodeDataChild;
import org.jypj.zgcsx.entity.Teacher;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/21.
 *
 * @author jian_wu
 *         通讯录添加教师时，查询使用
 */
@Data
public class DtoTeacherForContact {

    private String teacherId;
    private String teacherName;
    private String sex;
    private String phoneNum;
    private String bm;
    private String grade;
    private String mzm;
    private String kcmc;
    private Boolean checked = false;
    public static DtoTeacherForContact transfer(Teacher teacher) {
        DtoTeacherForContact teacherForContact = new DtoTeacherForContact();
        BeanUtils.copyProperties(teacher, teacherForContact);
        //0.teacherId
        teacherForContact.setTeacherId(teacher.getId());
        //1.性别
        if (StringUtil.isNotEmpty(teacherForContact.getSex())) {
            String realSex = StringUtil.transferSex(teacherForContact.getSex());
            teacherForContact.setSex(realSex);
        }
        //2.名族
        if (StringUtil.isNotEmpty(teacherForContact.getMzm())) {
            @SuppressWarnings("unchecked")
            List<CodeDataChild> codeDataChildren = (List<CodeDataChild>) CommonCache.getCodeMap().get("cims_mz");
            for(CodeDataChild dataChild:codeDataChildren){
                if(dataChild.getValue().equals(teacherForContact.getMzm())){
                    teacherForContact.setMzm(dataChild.getText());
                    break;
                }
            }
        }
        return teacherForContact;
    }

}
