package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.CourseResource;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;

/**
 * <p>
 * 课程资源表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface CourseResourceMapper extends BaseMapper<CourseResource> {

    /**
     * 查询资源信息
     * @param courseResource
     * @return
     */
    List<CourseResource> selectResource(Pagination page, @Param("courseResource") CourseResource courseResource, @Param("xnxq") Xnxq xnxq);
}
