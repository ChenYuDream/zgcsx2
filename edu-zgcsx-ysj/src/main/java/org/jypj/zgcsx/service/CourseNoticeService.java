package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.CourseNotice;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程通知表 服务类
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
public interface CourseNoticeService extends IService<CourseNotice> {

    int saveNotice(CourseNotice courseNotice, String[] teachers, String[] students);

    //条件查询（日历使用）
    List<CourseNotice> selectCalByMap(Map<String, Object> map);

    int updateNotice(CourseNotice courseNotice, String[] teachers, String[] students);

    /**
     * 分页查
     * @param map
     * @return
     */
    Page<CourseNotice> selectListByPage(Page<CourseNotice> page,Map<String, Object> map);

    /**
     * 删除事件
     * @param noticeId
     * @return
     */
    int delNotice(String noticeId);

}
