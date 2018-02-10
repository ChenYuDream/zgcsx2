package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.SpaceDao;
import org.jypj.zgcsx.entity.Space;
import org.jypj.zgcsx.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/13.
 */
@Service
public class SpaceServiceImpl extends ServiceImpl<SpaceDao, Space> implements SpaceService {

    @Autowired
    private SpaceDao spaceDao;

    @Override
    public List<Space> queryAllSpace(Map<String, Object> queryParameter) {
        return spaceDao.queryAllSpace(queryParameter);
    }
}
