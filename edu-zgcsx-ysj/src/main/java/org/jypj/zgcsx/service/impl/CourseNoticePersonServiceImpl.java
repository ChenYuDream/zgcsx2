package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.dao.CourseNoticePersonDao;
import org.jypj.zgcsx.entity.CourseNoticePerson;
import org.jypj.zgcsx.service.CourseNoticePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程通知人员表 服务实现类
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Service
public class CourseNoticePersonServiceImpl extends ServiceImpl<CourseNoticePersonDao, CourseNoticePerson> implements CourseNoticePersonService {

    @Autowired
    private CourseNoticePersonDao courseNoticePersonDao;

    @Override
    public List<CourseNoticePerson> selectAllPerson(String noticeId) {
        return courseNoticePersonDao.selectAllPerson(noticeId);
    }

    @Override
    public Page<CourseNoticePerson> selectListByPage(Page<CourseNoticePerson> page, Map<String, Object> queryMap) {
        List<CourseNoticePerson> list = courseNoticePersonDao.selectListByPage(page,queryMap);
        page.setRecords(list);
        return page;
    }
}
