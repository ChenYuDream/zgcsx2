package org.jypj.zgcsx.course.vo;

import org.jypj.zgcsx.course.entity.OptionalCourse;
import org.jypj.zgcsx.course.entity.Space;
import org.jypj.zgcsx.course.entity.WorkDay;

import java.io.Serializable;
import java.util.List;

/**
 * @author qi_ma
 * @version 1.0 2017/12/18 10:31
 */
public class SpaceTimetableVo implements Serializable {
    private static final long serialVersionUID = 2970210112624649927L;

    private String workDayId;
    /**
     * 时间
     */
    private WorkDay workDay;
    /**
     * 课程详细
     */
    private List<OptionalCourse> optionalCourses;
    /**
     * 上课地点
     */
    private Space space;

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public List<OptionalCourse> getOptionalCourses() {
        return optionalCourses;
    }

    public void setOptionalCourses(List<OptionalCourse> optionalCourses) {
        this.optionalCourses = optionalCourses;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public String getWorkDayId() {
        return workDayId;
    }

    public void setWorkDayId(String workDayId) {
        this.workDayId = workDayId;
    }
}
