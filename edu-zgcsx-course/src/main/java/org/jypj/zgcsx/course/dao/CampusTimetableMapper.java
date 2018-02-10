package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.CampusTimetable;

import java.util.List;

/**
 * <p>
 * 校区时间表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-12-15
 */
public interface CampusTimetableMapper extends BaseMapper<CampusTimetable> {
    List<CampusTimetable> selectByCampusId(@Param("campusId") String campusId);
}
