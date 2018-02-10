package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.Space;

import java.util.List;
import java.util.Map;

/**
 * 空间管理
 * @author ChenYu
 */
public interface SpaceDao extends BaseMapper<Space> {


    /**
     * 通过查询条件查出空间信息
     *
     * @param queryParameter
     * @return
     */
    List<Space> queryAllSpace(Map<String, Object> queryParameter);

}
