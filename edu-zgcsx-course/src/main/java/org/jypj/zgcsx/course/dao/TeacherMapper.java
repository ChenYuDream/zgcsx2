package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * 教师表
 *
 * @author yu_chen
 * @create 2017-11-22 14:32
 **/
public interface TeacherMapper extends BaseMapper<Teacher> {
    List<Teacher> selectTeachersByOptionalCourseId(String id);

    /******************分割线 避免冲突*****************************/
    /**
     * 根据校区id查询教师信息
     *
     * @param page
     * @param teacher 查询条件：姓名，性别
     * @return
     */
    List<Teacher> selectTeachers(Pagination page, Teacher teacher);

    List<Teacher> selectByTree(Pagination page, Map<String, Object> condition);

    /**
     * 根据班级id和日程id查询教师
     * @param workDayId
     * @param clazzId
     * @return
     */
    List<Teacher> selectTeachersByClazzId(@Param("workDayId") String workDayId, @Param("clazzId") String clazzId);
}
