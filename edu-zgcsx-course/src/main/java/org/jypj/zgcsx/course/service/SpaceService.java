package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.Space;

import java.util.List;
import java.util.Map;

/**
 * 公共空间service
 *
 * @author qi_ma
 * @create 2017-12-12 13:51
 **/
public interface SpaceService extends BaseService<Space> {

    List<Space> selectAll(Map<String, Object> condition);

    Page<Space> selectAll(Page<Space> page);
}
