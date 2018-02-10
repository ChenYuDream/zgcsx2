package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.Xnxq;

/**
 * <p>
 * 学年学期表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface XnxqService extends BaseService<Xnxq> {
    /**
     * 获取当前学年学期
     *
     * @return
     */
    Xnxq selectCurrentXnxq();
}
