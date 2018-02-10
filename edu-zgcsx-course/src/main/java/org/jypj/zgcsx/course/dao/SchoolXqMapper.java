package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Grade;
import org.jypj.zgcsx.course.entity.SchoolXq;

import java.util.List;

/**
 * 学校校区
 *
 * @author yu_chen
 * @create 2017-11-22 13:48
 **/
public interface SchoolXqMapper extends BaseMapper<SchoolXq> {
    List<SchoolXq> selectAllCampuses();

    List<Grade> selectGradesByCampusId(@Param("id") String id);

    SchoolXq selectCampusesById(@Param("id") String id);
}
