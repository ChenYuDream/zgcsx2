package org.jypj.zgcsx.service;

import org.jypj.zgcsx.common.dto.Result;

/**
 * Created by jian_wu on 2017/11/28.
 */
public interface PurviewService {

    /**
     * 查数据范围
     *
     * @return
     */
     Result getUserData(String loginName, String roleId);

}
