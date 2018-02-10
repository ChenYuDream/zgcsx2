package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.controller.WebsocketController;
import org.jypj.zgcsx.dao.CourseNoticeAttachmentDao;
import org.jypj.zgcsx.dao.CourseNoticeDao;
import org.jypj.zgcsx.dao.CourseNoticePersonDao;
import org.jypj.zgcsx.entity.CourseNotice;
import org.jypj.zgcsx.entity.CourseNoticeAttachment;
import org.jypj.zgcsx.entity.CourseNoticePerson;
import org.jypj.zgcsx.service.CourseNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程通知表 服务实现类
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Service
public class CourseNoticeServiceImpl extends ServiceImpl<CourseNoticeDao, CourseNotice> implements CourseNoticeService {

    @Autowired
    private CourseNoticeDao courseNoticeDao;

    @Autowired
    private CourseNoticePersonDao courseNoticePersonDao;

    @Autowired
    private CourseNoticeAttachmentDao noticeAttachmentDao;

    @Override
    public int saveNotice(CourseNotice courseNotice, String[] teachers, String[] students) {
        //1.先添加事件
        if(StringUtil.isEmpty(courseNotice.getId())){
            String id = StringUtil.getLowUUID();
            courseNotice.setId(id);
        }
        courseNotice.setCtime(new Date());
        //1.1为了和老系统通用，这个加上NOTICEDETAILURL字段和PROXY字段
        courseNotice.setNoticedetailurl("/notice/viewMyNotices.action?oper=feedback&jump=7&nid="+courseNotice.getId());
        courseNotice.setProxy(courseNotice.getCreater());
        int flag = courseNoticeDao.insert(courseNotice);
        //2.再添加参与人
        CourseNoticePerson noticePerson = new CourseNoticePerson();
        noticePerson.setCtime(new Date());
        noticePerson.setNoticeId(courseNotice.getId());
        noticePerson.setNoticeRead("1");
        convertData(noticePerson,teachers,students);
        return flag;
    }

    @Override
    public int updateNotice(CourseNotice courseNotice, String[] teachers, String[] students) {
        //1.更新时间
        courseNotice.setMtime(new Date());
        //2.更新参与人，先删后增
        courseNoticePersonDao.delete(new EntityWrapper<CourseNoticePerson>().eq("NOTICE_ID",courseNotice.getId()));
        CourseNoticePerson noticePerson = new CourseNoticePerson();
        noticePerson.setCtime(new Date());
        noticePerson.setNoticeId(courseNotice.getId());
        noticePerson.setNoticeRead("1");
        convertData(noticePerson,teachers,students);
        int flag = courseNoticeDao.updateById(courseNotice);
        return flag;
    }

    @Override
    public List<CourseNotice> selectCalByMap(Map<String, Object> map) {
        return courseNoticeDao.selectCalByMap(map);
    }

    //封装事件添加修改时的公共方法
    private void convertData(CourseNoticePerson noticePerson, String[] teachers, String[] students){
        if(teachers!=null){
            for(String teacherId:teachers){
                noticePerson.setId(StringUtil.getLowUUID());
                noticePerson.setNoticePersonType("1");
                noticePerson.setPersonId(teacherId);
                courseNoticePersonDao.insert(noticePerson);
            }
        }
        if(students!=null){
            for(String studentId:students){
                noticePerson.setId(StringUtil.getLowUUID());
                noticePerson.setNoticePersonType("2");
                noticePerson.setPersonId(studentId);
                courseNoticePersonDao.insert(noticePerson);
            }
        }
    }

    @Override
    public Page<CourseNotice> selectListByPage(Page<CourseNotice> page, Map<String, Object> map) {
        List<CourseNotice> list = courseNoticeDao.selectListByPage(page,map);
        page.setRecords(list);
        return page;
    }

    @Override
    public int delNotice(String noticeId) {
        int i = courseNoticeDao.deleteById(noticeId);
        courseNoticePersonDao.delete(new EntityWrapper<CourseNoticePerson>().eq("NOTICE_ID",noticeId));
        noticeAttachmentDao.delete(new EntityWrapper<CourseNoticeAttachment>().eq("COURSE_NOTICE_ID",noticeId));
        return i;
    }

}
