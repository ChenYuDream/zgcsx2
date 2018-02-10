package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.Space;

import java.util.List;
import java.util.Map;

/**
 * @author jianWu
 */
public interface SpaceService extends IService<Space> {

    /**
     * 查询所有空间管理的数据
     *
     * @param queryParameter
     * @return
     */
    List<Space> queryAllSpace(Map<String, Object> queryParameter);

}
