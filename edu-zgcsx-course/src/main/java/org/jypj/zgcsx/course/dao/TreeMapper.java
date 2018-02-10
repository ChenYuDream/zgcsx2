package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Tree;
import org.jypj.zgcsx.course.entity.TreeTeacher;

import java.util.List;
import java.util.Map;

/**
 * 通讯录dao接口
 *
 * @author qi_ma
 * @create 2017-11-22 10:59
 **/
public interface TreeMapper extends BaseMapper<Tree> {
    List<Tree> selectAll();

    List<Tree> selectParentsById(@Param("id") String id);

    List<TreeTeacher> selectAllTreeTeachers(Pagination page, Map<String, Object> condition);
}
