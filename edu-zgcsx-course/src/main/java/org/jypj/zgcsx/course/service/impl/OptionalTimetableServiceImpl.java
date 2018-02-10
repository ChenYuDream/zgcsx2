package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.jypj.zgcsx.course.dao.OptionalTimetableMapper;
import org.jypj.zgcsx.course.entity.OptionalTimetable;
import org.jypj.zgcsx.course.service.OptionalTimetableService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程时间表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class OptionalTimetableServiceImpl extends BaseServiceImpl<OptionalTimetableMapper, OptionalTimetable> implements OptionalTimetableService {

    @Override
    public boolean deleteByOptionalCourseId(String id) {
        return delete(new EntityWrapper<OptionalTimetable>().eq("OPTIONAL_COURSE_ID", id));
    }
}
