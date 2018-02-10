package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.UserDao;
import org.jypj.zgcsx.entity.User;
import org.jypj.zgcsx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by jian_wu on 2017/11/15.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getObjectWithXnXq(String userId) {
        return userDao.getObjectWithXnXq(userId);
    }

    @Override
    public User getTeacherXxidXqId(String userId) {
        return userDao.getTeacherXxidXqId(userId);
    }

    @Override
    public User getStudentXxidXqId(String userId) {
        return userDao.getStudentXxidXqId(userId);
    }

    @Override
    public User getWuYeXxidXqId(String userId) {
        return userDao.getWuYeXxidXqId(userId);
    }
}
