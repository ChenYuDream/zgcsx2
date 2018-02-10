package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.time.LocalDateTime;

/**
 * <p>
 * 学年学期表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface XnxqMapper extends BaseMapper<Xnxq> {
    /**
     * 获取当前学年学期
     *
     * @return
     */
    Xnxq selectCurrentXnxq(@Param("now") LocalDateTime now);
}
