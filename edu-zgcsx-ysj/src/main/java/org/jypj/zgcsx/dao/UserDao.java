package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.User;

/**
 * Created by jian_wu on 2017/11/15.
 */
public interface UserDao extends BaseMapper<User> {
    //查用户和学年学期
    User getObjectWithXnXq(String userId);
    //查教师的学校id和学区id
    User getTeacherXxidXqId(String userId);
    //查学生的学校id和学区id
    User getStudentXxidXqId(String userId);
    //查物业的学校id和学区id
    User getWuYeXxidXqId(String userId);
}
