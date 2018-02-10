package org.jypj.zgcsx.course.vo;

import org.jypj.zgcsx.course.entity.BaseCourse;
import org.jypj.zgcsx.course.entity.Clazz;
import org.jypj.zgcsx.course.entity.Teacher;
import org.jypj.zgcsx.course.entity.WorkDay;

import java.io.Serializable;
import java.util.List;

/**
 * @author qi_ma
 * @version 1.0 2017/11/28 17:31
 */
public class ClazzTimetableVo implements Serializable {
    private static final long serialVersionUID = 2970210112624649927L;
    private String id;
    private String courseId;
    /**
     * 班级
     */
    private Clazz clazz;
    /**
     * 时间
     */
    private WorkDay workDay;
    /**
     * 课程定义
     */
    private BaseCourse baseCourse;
    /**
     * 任课教师列表
     */
    private List<Teacher> teachers;

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
