package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.TeacherMapper;
import org.jypj.zgcsx.course.entity.Teacher;
import org.jypj.zgcsx.course.service.TeacherService;
import org.springframework.stereotype.Service;

/**
 * @author yu_chen
 * @create 2017-11-22 11:09
 **/
@Service
public class TeacherServiceImpl extends BaseServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Page<Teacher> selectTeachers(Page<Teacher> page, Teacher teacher) {
        page.setRecords(baseMapper.selectTeachers(page, teacher));
        return page;
    }

    @Override
    public Page<Teacher> selectByTree(Page<Teacher> page) {
        page.setRecords(baseMapper.selectByTree(page, page.getCondition()));
        return page;
    }
}
