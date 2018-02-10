package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.jypj.zgcsx.course.dao.WorkDayMapper;
import org.jypj.zgcsx.course.entity.CampusTimetable;
import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.CampusTimetableService;
import org.jypj.zgcsx.course.service.WorkDayService;
import org.jypj.zgcsx.course.vo.ClazzTimetableVo;
import org.jypj.zgcsx.course.vo.SpaceTimetableVo;
import org.jypj.zgcsx.course.vo.TeacherTimetableVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 日程表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class WorkDayServiceImpl extends BaseServiceImpl<WorkDayMapper, WorkDay> implements WorkDayService {
    @Resource
    private CampusTimetableService campusTimetableService;

    @Override
    public List<TeacherTimetableVo> selectTeacherTimetablesByTeacher(List<String> teacherIds, Xnxq xnxq) {
        return baseMapper.selectTeacherTimetablesByTeacher(teacherIds, xnxq);
    }

    @Override
    public List<SpaceTimetableVo> selectSpaceTimetablesBySpaceId(String spaceId, Xnxq xnxq) {
        return baseMapper.selectSpaceTimetablesBySpaceId(spaceId, xnxq);
    }

    @Override
    public List<WorkDay> selectDetailByWorkDay(WorkDay workDay) {
        return baseMapper.selectDetailByWorkDay(workDay);
    }

    @Override
    public int insertWorkDays(List<WorkDay> insertList) {
        int i = 0;
        for (WorkDay workDay : insertList) {
            i += baseMapper.insertWorkDay(workDay);
            if (workDay.getStartTime() == null && workDay.getEndTime() == null) {
                WorkDay day = selectById(workDay.getId());
                workDay.setStartTime(day.getStartTime());
                workDay.setEndTime(day.getEndTime());
            }
        }
        return i;
    }

    /**
     * 保存时间数据
     *
     * @param workDays 日程集合(
     *                 weekOfTerm 第几周(必须)
     *                 dayOfWeek 周几(必须),
     *                 period 第几节（type=2时）必须
     *                 startTime,endTime(type=5时)必须
     *                 )
     * @param xnxq     学年学期
     * @param campusId 校区ID
     * @param type     类型 2：选修课，5：课后一小时
     * @return
     */
    @Override
    public List<WorkDay> insertWorkDays(List<WorkDay> workDays, Xnxq xnxq, String campusId, String type) {
        List<CampusTimetable> campusTimetables = campusTimetableService.selectByCampusId(campusId);
        return workDays.stream().map(workDay -> {
            workDay.setCampusId(campusId);
            workDay.setXn(xnxq.getXn());
            workDay.setXq(xnxq.getXq());
            WorkDay w;
            switch (type) {
                case "2":
                    workDay.setId(null);
                    CampusTimetable campusTimetable = campusTimetables.stream().filter(ct -> Objects.equals(ct.getDayOfWeek(), workDay.getDayOfWeek())
                            && Objects.equals(ct.getPeriod(), workDay.getPeriod())).collect(Collectors.toList()).get(0);
                    workDay.setStartTime(campusTimetable.getStartTime());
                    workDay.setEndTime(campusTimetable.getEndTime());
                    w = insertOptional(workDay);
                    break;
                case "5":
                    workDay.setId(null);
                    w = insertAfter(workDay);
                    break;
                default:
                    return null;
            }
            w.setDate(getDate(xnxq, w.getWeekOfTerm(), w.getDayOfWeek()));
            return w;
        }).collect(Collectors.toList());
    }

    private WorkDay insertAfter(WorkDay workDay) {
        int i = baseMapper.insertAfter(workDay);
        if (i > 0) {
            return workDay;
        } else {
            return baseMapper.selectAfter(workDay).get(0);
        }
    }

    private WorkDay insertOptional(WorkDay workDay) {
        int i = baseMapper.insertOptional(workDay);
        if (i > 0) {
            return workDay;
        } else {
            return baseMapper.selectOptional(workDay).get(0);
        }
    }

    @Override
    public List<WorkDay> selectBySpaceId(String spaceId, Xnxq xnxq) {
        return baseMapper.selectBySpaceId(spaceId, xnxq);
    }


    @Override
    public Integer getWeekOfTerm(Xnxq xnxq, LocalDate date) {
        //一学期第一天是周几
        LocalDate startDate = xnxq.getStartDate();
        //将一学期第一天的日期调整至当前周的第一天
        while (startDate.getDayOfWeek() != properties.getFirstDayOfWeek()) {
            startDate = startDate.minusDays(1);
        }
        return (int) (date.toEpochDay() - startDate.toEpochDay()) / 7 + 1;
    }

    @Override
    public LocalDate getDate(Xnxq xnxq, int weekOfTerm, int dayOfWeek) {
        //一学期第一天是周几
        LocalDate startDate = xnxq.getStartDate();
        //将一学期第一天的日期调整至当前周的第一天
        while (startDate.getDayOfWeek() != properties.getFirstDayOfWeek()) {
            startDate = startDate.minusDays(1);
        }
        DayOfWeek week = DayOfWeek.of(dayOfWeek != 0 ? dayOfWeek : 7);
        int plusDays = 0;
        while (week != properties.getFirstDayOfWeek()) {
            plusDays++;
            week = week.minus(1);
        }
        plusDays += (weekOfTerm - 1) * 7;
        return startDate.plusDays(plusDays);
    }

    @Override
    public WorkDay selectDetailByNonIdAndWeekOfTerm(String workDayId, Integer weekOfTerm) {
        return baseMapper.selectDetailByNonIdAndWeekOfTerm(workDayId, weekOfTerm);
    }

    @Override
    public List<WorkDay> selectCurrentXnxqWorkDayList(Xnxq xnxq) {
        return baseMapper.selectList(new EntityWrapper<WorkDay>()
                .eq("xn", xnxq.getXn())
                .eq("xq", xnxq.getXq())
                .isNull("week_of_term"));
    }

    @Override
    public List<ClazzTimetableVo> selectAllClazzTimetable(Map<String, Object> condition) {
        return baseMapper.selectAllClazzTimetable(condition);
    }
}
