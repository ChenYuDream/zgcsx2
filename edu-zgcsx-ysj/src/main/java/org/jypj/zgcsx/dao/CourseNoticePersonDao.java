package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.entity.CourseNoticePerson;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 课程通知人员表 Mapper 接口
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
public interface CourseNoticePersonDao extends BaseMapper<CourseNoticePerson> {

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
    List<CourseNoticePerson> selectListByPage(Page<CourseNoticePerson> page, Map<String,Object> queryMap);
}