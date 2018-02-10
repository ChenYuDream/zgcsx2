package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.EimsPersonTreeDao;
import org.jypj.zgcsx.entity.EimsPersonTree;
import org.jypj.zgcsx.service.EimsPersonTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/10.
 */
@Service
public class EimsPersonServiceImpl extends ServiceImpl<EimsPersonTreeDao, EimsPersonTree> implements EimsPersonTreeService {

    @Autowired
    private EimsPersonTreeDao eimsPersonTreeDao;

    @Override
    public Page<EimsPersonTree> queryPersonTreeByMap(Page<EimsPersonTree> page, Map<String, Object> map) {
        List<EimsPersonTree> list = eimsPersonTreeDao.queryPersonTreeByMap(page,map);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<EimsPersonTree> queryNoticeTeacher(String noticeId) {
        return eimsPersonTreeDao.queryNoticeTeacher(noticeId);
    }

    @Override
    public EimsPersonTree queryTeacher(String userId) {
        return eimsPersonTreeDao.queryTeacher(userId);
    }

    @Override
    public Page<EimsPersonTree> queryPersonTreeNoContactByMap(Page<EimsPersonTree> page, Map<String, Object> map) {
        List<EimsPersonTree> list = eimsPersonTreeDao.queryPersonTreeNoContactByMap(page,map);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<EimsPersonTree> queryPersonTreeAllByMap(Page<EimsPersonTree> page, Map<String, Object> map) {
        List<EimsPersonTree> list = eimsPersonTreeDao.queryPersonTreeAllByMap(page,map);
        page.setRecords(list);
        return page;
    }
}
