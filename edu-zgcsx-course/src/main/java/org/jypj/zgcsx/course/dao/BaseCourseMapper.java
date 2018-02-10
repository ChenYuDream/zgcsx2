package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.BaseCourse;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程定义表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface BaseCourseMapper extends BaseMapper<BaseCourse> {

    /**
     * 查询教师教授的基础课程
     *
     * @param baseCourse
     * @param xnxq
     * @return
     */
    List<BaseCourse> selectTeacherBasicCourse(@Param("baseCourse") BaseCourse baseCourse, @Param("xnxq") Xnxq xnxq);

    /**
     * 查询教师教授的选修课程
     *
     * @param baseCourse
     * @param xnxq
     * @return
     */
    List<BaseCourse> selectTeacherChooseCourse(@Param("baseCourse") BaseCourse baseCourse, @Param("xnxq") Xnxq xnxq);

    List<BaseCourse> selectAll(Pagination page, Map<String, Object> condition);
}
