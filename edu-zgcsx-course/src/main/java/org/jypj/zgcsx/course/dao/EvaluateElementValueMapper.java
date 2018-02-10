package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.EvaluateElementValue;

import java.util.List;

/**
 * <p>
 * 选修课评价要素值 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2018-01-03
 */
public interface EvaluateElementValueMapper extends BaseMapper<EvaluateElementValue> {
    /**
     * 根据评价要素ID查询评价要素值列表
     *
     * @param id 评价要素ID
     * @return 评价要素值列表
     */
    List<EvaluateElementValue> selectValuesByElementId(@Param("id") String id);

    /**
     * 根据选修课ID删除选修课评价要素值
     *
     * @param id
     * @return
     */
    int deleteByOptionalCourseId(String id);
}
