package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.TeacherDao;
import org.jypj.zgcsx.entity.Teacher;
import org.jypj.zgcsx.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/21.
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao,Teacher> implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public Page<Teacher> selectAllTeacher(Page<Teacher> page, Map<String, Object> queryMap) {
        List<Teacher> list = teacherDao.selectAllTeacher(page,queryMap);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<Teacher> selectAllTeacherIds() {
        return teacherDao.selectAllTeacherIds();
    }
}
