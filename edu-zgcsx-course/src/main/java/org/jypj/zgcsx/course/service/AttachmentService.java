package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.Attachment;
import org.jypj.zgcsx.course.service.BaseService;

import java.util.List;

/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface AttachmentService extends BaseService<Attachment> {
    /**
     * 保存附件
     * @param attachments
     * @return
     */
    int saveAttachment(List<Attachment> attachments);
}
