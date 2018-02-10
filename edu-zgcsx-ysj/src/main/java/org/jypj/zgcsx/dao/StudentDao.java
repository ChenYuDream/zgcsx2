package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.jypj.zgcsx.entity.Student;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/15.
 */
public interface StudentDao extends BaseMapper<Student> {

    //查参与事件的学生
    List<Student> queryNoticeStudent(String studentId);

}
