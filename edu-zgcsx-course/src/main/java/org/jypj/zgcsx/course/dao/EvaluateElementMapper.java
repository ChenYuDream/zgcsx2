package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.EvaluateElement;

import java.util.List;

/**
 * <p>
 * 选修课评价要素 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-03
 */
public interface EvaluateElementMapper extends BaseMapper<EvaluateElement> {
    /**
     * 根据选修课ID查询选修课评价要素
     * @param id 选修课ID
     * @return 评价要素列表
     */
    List<EvaluateElement> selectEvaluateElementsByOptionalCourseId(@Param("id") String id);

    /**
     * 根据选修课ID删除选修课评价要素
     * @param id 选修课ID
     * @return 影响行数
     */
    int deleteByOptionalCourseId(String id);
}
