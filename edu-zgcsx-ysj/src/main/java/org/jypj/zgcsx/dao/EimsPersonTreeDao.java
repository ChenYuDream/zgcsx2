package org.jypj.zgcsx.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.jypj.zgcsx.entity.EimsPersonTree;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/13.
 */
public interface EimsPersonTreeDao extends BaseMapper<EimsPersonTree> {
    /**
     * 根据教师树及其查询条件，分页查询教师
     * @param page
     * @param map
     * @return
     */
    List<EimsPersonTree> queryPersonTreeByMap(Pagination page, Map<String, Object> map);

    /**
     * 查询事件参与人教师
     * @param noticeId
     * @return
     */
    List<EimsPersonTree> queryNoticeTeacher(String noticeId);

    /**
     * 查单人
     * @param userId
     * @return
     */
    EimsPersonTree queryTeacher(String userId);

    /**
     * 非通讯录查教师
     * @param page
     * @param map
     * @return
     */
    List<EimsPersonTree> queryPersonTreeNoContactByMap(Pagination page, Map<String, Object> map);


    /**
     * 查教师(通讯录和非通讯录都查)
     * @param page
     * @param map
     * @return
     */
    List<EimsPersonTree> queryPersonTreeAllByMap(Pagination page, Map<String, Object> map);

    /**
     * 根据nodeid删教师
     * @param queryMap
     * @return
     */
    int delPersonByNodeIds(Map<String, Object> queryMap);

}
