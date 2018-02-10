package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.User;

/**
 * Created by jian_wu on 2017/11/13.
 */
public interface UserService extends IService<User> {
    User getObjectWithXnXq(String userId);

    //查教师的学校id和学区id
    User getTeacherXxidXqId(String userId);
    //查学生的学校id和学区id
    User getStudentXxidXqId(String userId);
    //查物业的学校id和学区id
    User getWuYeXxidXqId(String userId);
}
