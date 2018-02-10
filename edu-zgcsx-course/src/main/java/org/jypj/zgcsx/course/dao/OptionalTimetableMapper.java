package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.entity.OptionalTimetable;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;

/**
 * <p>
 * 课程时间表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface OptionalTimetableMapper extends BaseMapper<OptionalTimetable> {

    /**
     * 查询课后一小时选课信息
     * @param campusId
     * @param xnxq
     * @return
     */
    List<OptionalTimetable> selectAfterCourse(@Param("campusId") String campusId, @Param("clazzes") String[] clazzes, @Param("xnxq") Xnxq xnxq);
}
