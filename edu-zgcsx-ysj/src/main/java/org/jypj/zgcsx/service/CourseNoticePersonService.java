package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.CourseNoticePerson;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程通知人员表 服务类
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
public interface CourseNoticePersonService extends IService<CourseNoticePerson> {

    /**
     * 参与者（除去拒绝的人）
     * @param noticeId
     * @return
     */
    List<CourseNoticePerson> selectAllPerson(String noticeId);


    /**
     * 反馈分页查询
     * @param page
     * @param queryMap
     * @return
     */
    Page<CourseNoticePerson> selectListByPage(Page<CourseNoticePerson> page, Map<String,Object> queryMap);
}
