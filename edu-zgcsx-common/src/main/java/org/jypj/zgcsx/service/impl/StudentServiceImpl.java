package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.StudentDao;
import org.jypj.zgcsx.entity.Student;
import org.jypj.zgcsx.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jian_wu on 2017/11/15.
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

}
