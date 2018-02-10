package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.dao.EvaluateMapper;
import org.jypj.zgcsx.course.entity.CourseResource;
import org.jypj.zgcsx.course.entity.Evaluate;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.jypj.zgcsx.course.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * 教师评价学生资源表 实现类
 */
@Service
public class EvaluateServiceImpl  extends BaseServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {

    @Override
    public int saveOrUpdate(CourseResource courseResource, UserInfo userInfo) {
        int i = 0;
        Evaluate evaluate = null;
        Evaluate find = null;
        List<Map<String, String>> evaluateList = courseResource.getEvaluates();
        if(StringUtil.isNotEmpty(evaluateList) && evaluateList.size() > 0){
            for (int j = 0; j < evaluateList.size(); j++) {
                evaluate = new Evaluate();
                evaluate.setId(evaluateList.get(j).get("evaluateId"));
                evaluate.setState(evaluateList.get(j).get("state"));
                evaluate.setStudentId(evaluateList.get(j).get("studentId"));
                evaluate.setTeacherId(userInfo.getId());
                evaluate.setCourseResourceId(evaluateList.get(j).get("courseResourceId"));
                evaluate.setEvaluateTime(LocalDateTime.now());

                if(StringUtil.isNotEmpty(evaluate.getId())){
                    evaluate.setUpdateTime(LocalDateTime.now());
                    evaluate.setUpdateUser(userInfo.getUserName());
                    i += baseMapper.updateById(evaluate);
                }else{
                    evaluate.setCreateTime(LocalDateTime.now());
                    evaluate.setCreateUser(userInfo.getUserName());
                    i += baseMapper.insert(evaluate);
                }
            }
        }
        return i;
    }
}
