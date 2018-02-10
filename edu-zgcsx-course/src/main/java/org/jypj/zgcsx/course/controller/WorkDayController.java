package org.jypj.zgcsx.course.controller;


import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.config.xnxq.CurrentXnxq;
import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 日程表 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/workDay")
public class WorkDayController extends BaseController {

    @Resource
    private CourseProperties properties;

    @RequestMapping("save")
    @ResponseBody
    public Result save() {
        List<WorkDay> workDays = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            WorkDay workDay = new WorkDay();
            workDay.setId(String.valueOf(i));
            workDays.add(workDay);
        }
        boolean flag = workDayService.insertBatch(workDays);
        return render(flag, "course.ok");
    }

    /**
     * 获取系统当前时间的周
     *
     * @param xnxq
     * @return
     */
    @RequestMapping("week")
    @ResponseBody
    public Integer getWeekOfTerm(@CurrentXnxq Xnxq xnxq) {
        return workDayService.getWeekOfTerm(xnxq, LocalDate.now());
    }

    /**
     * 获取系统总周数
     *
     * @param xnxq
     * @return
     */
    @RequestMapping("week/count")
    @ResponseBody
    public Integer getWeekOfTermCount(@CurrentXnxq Xnxq xnxq) {
        return workDayService.getWeekOfTerm(xnxq, xnxq.getCourseEndDate());
    }

    /**
     * 根据学年学期和第几周计算周日期
     *
     * @param xnxq
     * @return
     */
    @RequestMapping("date")
    @ResponseBody
    public LocalDate[] getDate(@CurrentXnxq Xnxq xnxq, int weekOfTerm, String id) {
        if (StringUtils.isNotEmpty(id)) {
            xnxq = xnxqService.selectById(id);
        }
        LocalDate[] localDates = new LocalDate[7];
        for (int i = 0; i < localDates.length; i++) {
            if (i == 0) {
                localDates[0] = workDayService.getDate(xnxq, weekOfTerm, properties.getFirstDayOfWeek() != DayOfWeek.SUNDAY ? properties.getFirstDayOfWeek().getValue() : 0);
            } else {
                localDates[i] = localDates[0].plusDays(i);
            }
        }
        return localDates;
    }
}

