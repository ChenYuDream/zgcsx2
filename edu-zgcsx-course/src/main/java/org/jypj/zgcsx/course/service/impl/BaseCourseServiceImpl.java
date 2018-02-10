package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.BaseCourseMapper;
import org.jypj.zgcsx.course.entity.BaseCourse;
import org.jypj.zgcsx.course.service.BaseCourseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程定义表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class BaseCourseServiceImpl extends BaseServiceImpl<BaseCourseMapper, BaseCourse> implements BaseCourseService {

    @Override
    public Page<BaseCourse> selectAll(Page<BaseCourse> page) {
        page.setRecords(baseMapper.selectAll(page, page.getCondition()));
        return page;
    }
}
