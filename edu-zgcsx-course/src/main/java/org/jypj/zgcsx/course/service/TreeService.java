package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.Tree;
import org.jypj.zgcsx.course.entity.TreeTeacher;

/**
 * 公共空间service
 *
 * @author qi_ma
 * @create 2017-12-12 13:51
 **/
public interface TreeService extends BaseService<Tree> {
    Page<TreeTeacher> selectAllTreeTeachers(Page<TreeTeacher> page);
}
