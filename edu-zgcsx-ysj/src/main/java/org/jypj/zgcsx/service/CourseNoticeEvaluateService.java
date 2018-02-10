package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.CourseNoticeEvaluate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
public interface CourseNoticeEvaluateService extends IService<CourseNoticeEvaluate> {

    /**
     *
     * @param page
     * @param queryMap
     * @return
     */
    Page<CourseNoticeEvaluate> selectListByPage(Page<CourseNoticeEvaluate> page, Map<String,Object> queryMap);

}
