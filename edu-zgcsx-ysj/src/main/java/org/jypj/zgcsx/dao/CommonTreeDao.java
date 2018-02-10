package org.jypj.zgcsx.dao;


import org.jypj.zgcsx.entity.CommonTree;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/15.
 */
public interface CommonTreeDao  {

    //1.学校、年级、班级树结构
    //一次sql查太过复杂，索性一级一级的查，在service层里面合并数据
    List<CommonTree> getSchoolInfoTree();
    List<CommonTree> getCampusInfoTree(String schoolId);
    List<CommonTree> getGradeInfoTree(String campusId);
    List<CommonTree> getClassInfoTree(String gradeId);
    List<CommonTree> getCampusAllInfoTree();
    List<CommonTree> getGradeAllInfoTree();
    List<CommonTree> getClassAllInfoTree();
}
