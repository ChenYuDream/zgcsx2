package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.AttachmentMapper;
import org.jypj.zgcsx.course.dao.CourseResourceMapper;
import org.jypj.zgcsx.course.entity.Attachment;
import org.jypj.zgcsx.course.entity.CourseResource;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.CourseResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程资源表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class CourseResourceServiceImpl extends BaseServiceImpl<CourseResourceMapper, CourseResource> implements CourseResourceService {

    @Resource
    private AttachmentMapper attachmentMapper;

    @Override
    public int save(CourseResource courseResource, List<Attachment> attachments) {
        int i = 0;
        CourseResource _courseResource = null;
        for (Attachment attachment : attachments) {
            i = attachmentMapper.insert(attachment);
            _courseResource = new CourseResource();
            BeanUtils.copyProperties(courseResource, _courseResource);
            _courseResource.setAttachmentId(attachment.getId());
            i += baseMapper.insert(_courseResource);
        }
        return i;
    }

    @Override
    public int delete(String id, String attachmentId) {
        int i = baseMapper.deleteById(id);
        i += attachmentMapper.deleteById(attachmentId);
        return i;
    }

    @Override
    public Page<CourseResource> selectResource(Page<CourseResource> page, CourseResource courseResource, Xnxq xnxq) {
        page.setRecords(baseMapper.selectResource(page, courseResource, xnxq));
        return page;
    }
}
