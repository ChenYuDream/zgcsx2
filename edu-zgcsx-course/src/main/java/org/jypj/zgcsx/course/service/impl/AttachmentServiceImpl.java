package org.jypj.zgcsx.course.service.impl;

import org.jypj.zgcsx.course.entity.Attachment;
import org.jypj.zgcsx.course.dao.AttachmentMapper;
import org.jypj.zgcsx.course.service.AttachmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class AttachmentServiceImpl extends BaseServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {
    @Override
    public int saveAttachment(List<Attachment> attachments) {
        int i = 0;
        for (Attachment attachment : attachments) {
            i += baseMapper.insert(attachment);
        }
        return i;
    }
}
