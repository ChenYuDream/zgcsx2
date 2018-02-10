package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.entity.CourseNotice;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 课程通知表 Mapper 接口
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
public interface CourseNoticeDao extends BaseMapper<CourseNotice> {

    //条件查询（日历使用）
   List<CourseNotice> selectCalByMap(Map<String, Object> map);

    /**
     * 分页查
     * @param map
     * @return
     */
   List<CourseNotice> selectListByPage(Page<CourseNotice> page,Map<String, Object> map);

}