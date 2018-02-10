package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.CourseNoticeEvaluateDao;
import org.jypj.zgcsx.entity.CourseNoticeEvaluate;
import org.jypj.zgcsx.service.CourseNoticeEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Service
public class CourseNoticeEvaluateServiceImpl extends ServiceImpl<CourseNoticeEvaluateDao, CourseNoticeEvaluate> implements CourseNoticeEvaluateService {

    @Autowired
    private CourseNoticeEvaluateDao courseNoticeEvaluateDao;

    @Override
    public Page<CourseNoticeEvaluate> selectListByPage(Page<CourseNoticeEvaluate> page, Map<String, Object> queryMap) {
        List<CourseNoticeEvaluate> list = courseNoticeEvaluateDao.selectListByPage(page,queryMap);
        page.setRecords(list);
        return page;
    }
}
