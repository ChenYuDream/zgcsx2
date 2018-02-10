package org.jypj.zgcsx.service.impl;

import org.jypj.zgcsx.dao.CommonTreeDao;
import org.jypj.zgcsx.entity.CommonTree;
import org.jypj.zgcsx.service.CommonTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/15.
 */
@Service
public class CommonTreeServiceImpl implements CommonTreeService {

    @Autowired
    private CommonTreeDao commonTreeDao;

    @Override
    public List<CommonTree> getSchoolTree() {
        //学校、校区、年级、班级
        List<CommonTree> schoolTree = commonTreeDao.getSchoolInfoTree();
        if(schoolTree!=null&&schoolTree.size()>0){
            for(CommonTree school:schoolTree){
                List<CommonTree> campusTree = commonTreeDao.getCampusInfoTree(school.getId());
                if(campusTree!=null&&campusTree.size()>0){
                    for(CommonTree campus:campusTree){
                        List<CommonTree> gradeTree = commonTreeDao.getGradeInfoTree(campus.getId());
                        if(gradeTree!=null&&gradeTree.size()>0){
                            for(CommonTree grade:gradeTree){
                                List<CommonTree> classTree = commonTreeDao.getClassInfoTree(grade.getId());
                                if(classTree!=null&&classTree.size()>0){
                                    grade.setSubTree(classTree);
                                }
                            }
                            campus.setSubTree(gradeTree);
                        }
                    }
                    school.setSubTree(campusTree);
                }
            }
        }
        return schoolTree;
    }
}
