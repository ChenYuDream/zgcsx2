package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.OptionalCourse;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 选修课详情表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface OptionalCourseMapper extends BaseMapper<OptionalCourse> {

    List<OptionalCourse> selectAll(Pagination page, Map<String, Object> condition);

    List<OptionalCourse> selectAll(Map<String, Object> condition);


    /******************  分割线，避免冲突**************************/

    /**
     * 查询是否所有的选修课选课都已经结束
     * @param campusId 校区id
     * @param xnxq
     * @return count > 0 :存在未结束的选修课
     */
    Integer selectChooseCourseIsEnd(@Param("campusId") String campusId, @Param("xnxq") Xnxq xnxq, @Param("now") LocalDateTime now);

    /**
     * 查询所有报名未满的选修课
     * @param campusId 校区id
     * @param xnxq
     * @return
     */
    List<OptionalCourse> selectLaskChooseCourse(@Param("campusId") String campusId, @Param("xnxq") Xnxq xnxq);

    /**
     * 查询所有选修课的班级数据范围
     * @param campusId 校区id
     * @param xnxq
     * @return
     */
    List<OptionalCourse> selectChooseCourseClazz(@Param("campusId") String campusId, @Param("xnxq") Xnxq xnxq);
}
