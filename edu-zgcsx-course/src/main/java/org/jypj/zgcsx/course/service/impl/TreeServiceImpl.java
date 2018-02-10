package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.TreeMapper;
import org.jypj.zgcsx.course.entity.Tree;
import org.jypj.zgcsx.course.entity.TreeTeacher;
import org.jypj.zgcsx.course.service.TreeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qi_ma
 * @create 2017-12-12 13:51
 **/
@Service
public class TreeServiceImpl extends BaseServiceImpl<TreeMapper, Tree> implements TreeService {
    @Override
    public List<Tree> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public Page<TreeTeacher> selectAllTreeTeachers(Page<TreeTeacher> page) {
        page.setRecords(baseMapper.selectAllTreeTeachers(page, page.getCondition()));
        return page;
    }
}
