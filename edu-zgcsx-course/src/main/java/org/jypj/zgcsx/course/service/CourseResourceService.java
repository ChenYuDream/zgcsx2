package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.Attachment;
import org.jypj.zgcsx.course.entity.CourseResource;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程资源表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface CourseResourceService extends BaseService<CourseResource> {
    /**
     * 资源上传
     * @param courseResource
     * @return
     */
    int save(CourseResource courseResource, List<Attachment> attachments);

    /**
     * 资源删除
     * @param id           资源id
     * @param attachmentId 附件id
     * @return
     */
    int delete(String id, String attachmentId);

    /**
     * 查询资源
     * @param courseResource
     * @return
     */
    Page<CourseResource> selectResource(Page<CourseResource> page, CourseResource courseResource, Xnxq xnxq);
}
