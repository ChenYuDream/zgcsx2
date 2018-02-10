package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/21.
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 查全部教师，附带年级信息
     * @param page
     * @param queryMap
     * @return
     */
    Page<Teacher> selectAllTeacher(Page<Teacher> page, Map<String, Object> queryMap);

    /**
     * 查全部教师  只有id
     * @return
     */
    List<Teacher> selectAllTeacherIds();
}
