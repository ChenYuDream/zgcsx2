package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.*;

import java.util.List;

/**
 * 教师评价学生资源表 接口
 */
public interface EvaluateService extends BaseService<Evaluate>  {

    /**
     * 保存，修改资源评价
     * @param courseResource
     * @return
     */
    int saveOrUpdate(CourseResource courseResource, UserInfo userInfo);
}
