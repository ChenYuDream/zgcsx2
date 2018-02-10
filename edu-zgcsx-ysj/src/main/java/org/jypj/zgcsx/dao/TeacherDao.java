package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/21.
 */

public interface TeacherDao extends BaseMapper<Teacher> {

    /**
     * 查全部教师，附带年级信息
     * @param page
     * @param queryMap
     * @return
     */
    List<Teacher> selectAllTeacher(Page<Teacher> page, Map<String, Object> queryMap);

    /**
     * 查全部教师  只有id
     * @return
     */
    List<Teacher> selectAllTeacherIds();

}
