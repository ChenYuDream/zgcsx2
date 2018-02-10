package org.jypj.zgcsx.course.config.mybatis;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * @author qi_ma
 * @version 1.0 2017/11/23 17:30
 */
public class CourseMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createUser = getFieldValByName("createUser", metaObject);
        Object createTime = getFieldValByName("createTime", metaObject);
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException | ClassCastException e1) {
            userInfo = null;
        }
        if (createUser == null) {
            setFieldValByName("createUser", userInfo != null ? userInfo.getUserName() : "", metaObject);
        }
        if (createTime == null) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateUser = getFieldValByName("updateUser", metaObject);
        Object updateTime = getFieldValByName("updateTime", metaObject);
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            userInfo = null;
        }
        if (updateUser == null) {
            setFieldValByName("updateUser", userInfo != null ? userInfo.getUserName() : "", metaObject);
        }
        if (updateTime == null) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}