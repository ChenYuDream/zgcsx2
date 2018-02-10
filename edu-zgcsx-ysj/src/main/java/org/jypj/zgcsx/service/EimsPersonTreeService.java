package org.jypj.zgcsx.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.entity.EimsPersonTree;

import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/10.
 */
public interface EimsPersonTreeService extends IService<EimsPersonTree> {

    //通讯录查教师分页
    Page<EimsPersonTree> queryPersonTreeByMap(Page<EimsPersonTree> page, Map<String, Object> map);

    //查事件参与教师list
    List<EimsPersonTree> queryNoticeTeacher(String noticeId);

    //查教师单条
    EimsPersonTree queryTeacher(String userId);

    //查非通讯录教师分页
    Page<EimsPersonTree> queryPersonTreeNoContactByMap(Page<EimsPersonTree> page, Map<String, Object> map);

    /**
     * 查教师(通讯录和非通讯录都查)
     * @param page
     * @param map
     * @return
     */
    Page<EimsPersonTree> queryPersonTreeAllByMap(Page<EimsPersonTree> page, Map<String, Object> map);

}
