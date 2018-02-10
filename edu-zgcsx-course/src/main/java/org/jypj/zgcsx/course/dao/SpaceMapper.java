package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Space;

import java.util.List;
import java.util.Map;

/**
 * 空间mapper
 *
 * @author qi_ma
 * @create 2017-11-22 10:59
 **/
public interface SpaceMapper extends BaseMapper<Space> {

    List<Space> selectAll(Map<String, Object> condition);

    List<Space> selectAll(Pagination page, Map<String, Object> condition);
}
