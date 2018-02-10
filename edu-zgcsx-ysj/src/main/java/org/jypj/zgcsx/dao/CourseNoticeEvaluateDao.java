package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.entity.CourseNoticeEvaluate;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
public interface CourseNoticeEvaluateDao extends BaseMapper<CourseNoticeEvaluate> {

    /**
     *
     * @param page
     * @param queryMap
     * @return
     */
    List<CourseNoticeEvaluate> selectListByPage(Page<CourseNoticeEvaluate> page, Map<String,Object> queryMap);

}