package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.Teacher;

import java.util.List;

/**
 * 教师
 *
 * @author
 * @create 2017-11-22 14:32
 **/
public interface TeacherService extends BaseService<Teacher>{
    /**
     * 根据校区id查询教师信息
     * @param page
     * @param teacher 查询条件：姓名，性别
     * @return
     */
    Page<Teacher> selectTeachers(Page<Teacher> page, Teacher teacher);

    Page<Teacher> selectByTree(Page<Teacher> page);
}
