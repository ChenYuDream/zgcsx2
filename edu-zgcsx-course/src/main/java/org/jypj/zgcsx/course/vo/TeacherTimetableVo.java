package org.jypj.zgcsx.course.vo;

import org.jypj.zgcsx.course.entity.*;

import java.io.Serializable;

/**
 * @author qi_ma
 * @version 1.0 2017/11/28 17:31
 */
public class TeacherTimetableVo implements Serializable {
    private static final long serialVersionUID = 2970210112624649927L;
    /**
     * 教师
     */
    private Teacher teacher;
    /**
     * 时间
     */
    private WorkDay workDay;
    /**
     * 课程定义
     */
    private BaseCourse baseCourse;
    /**
     * 课程详细
     */
    private OptionalCourse optionalCourse;
    /**
     * 上课地点
     */
    private Space space;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public BaseCourse getBaseCourse() {
        return baseCourse;
    }

    public void setBaseCourse(BaseCourse baseCourse) {
        this.baseCourse = baseCourse;
    }

    public OptionalCourse getOptionalCourse() {
        return optionalCourse;
    }

    public void setOptionalCourse(OptionalCourse optionalCourse) {
        this.optionalCourse = optionalCourse;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
