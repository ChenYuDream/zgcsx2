package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.jypj.zgcsx.course.dao.XnxqMapper;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.error.CourseException;
import org.jypj.zgcsx.course.service.WorkDayService;
import org.jypj.zgcsx.course.service.XnxqService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 学年学期表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class XnxqServiceImpl extends BaseServiceImpl<XnxqMapper, Xnxq> implements XnxqService {
    @Resource
    private WorkDayService workDayService;

    @Override
    public Xnxq selectCurrentXnxq() {
        Xnxq xnxq;
        try {
            xnxq = baseMapper.selectCurrentXnxq(LocalDateTime.now());
        } catch (Exception e) {
            throw new CourseException("xnxq.repeat");
        }
        if (xnxq == null) {
            throw new CourseException("xnxq.not-found");
        }
        Integer weekCount = workDayService.getWeekOfTerm(xnxq, xnxq.getCourseEndDate());
        xnxq.setWeekCount(weekCount);
        return xnxq;
    }

    @Override
    public List<Xnxq> selectAll() {
        List<Xnxq> xnxqList = baseMapper.selectList(new EntityWrapper<>());
        xnxqList = xnxqList.stream().peek(xnxq -> xnxq.setWeekCount(workDayService.getWeekOfTerm(xnxq, xnxq.getCourseEndDate()))).sorted(Comparator.comparing(Xnxq::getXn).thenComparing(Xnxq::getXq)).collect(Collectors.toList());
        return xnxqList;
    }
}
