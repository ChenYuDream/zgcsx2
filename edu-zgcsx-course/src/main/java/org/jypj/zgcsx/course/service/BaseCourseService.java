package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.BaseCourse;

/**
 * <p>
 * 课程定义表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface BaseCourseService extends BaseService<BaseCourse> {

    Page<BaseCourse> selectAll(Page<BaseCourse> page);
}
