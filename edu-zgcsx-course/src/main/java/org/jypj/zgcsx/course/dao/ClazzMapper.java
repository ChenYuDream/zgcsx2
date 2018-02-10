package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Clazz;

import java.util.List;

/**
 * 班级mapper
 *
 * @author
 * @create 2017-11-22 10:59
 **/
public interface ClazzMapper extends BaseMapper<Clazz> {

    List<Clazz> selectClazzesByOptionalCourseId(String id);

    List<Clazz> selectClazzesByGradeId(@Param("id") String id);

    /**
     * 根据班级id查询班级信息（翻译年级、校区名称）
     * @param id
     * @return
     */
    Clazz selectClazzByClazzId(@Param("id") String id);
}
