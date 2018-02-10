package org.jypj.zgcsx.course.config.export;

import org.jypj.zgcsx.course.entity.StudentCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCourseView extends CourseXlsView<StudentCourse> {
    private static final Map<String, String> SEXES = new HashMap<>();
    private static final Map<Boolean, String> CHOSENS = new HashMap<>();
    private static final Map<String, String> COURSE_TYPES = new HashMap<>();

    static {
        SEXES.put("1", "男");
        SEXES.put("2", "女");
        CHOSENS.put(true, "是");
        CHOSENS.put(false, "否");
        COURSE_TYPES.put("2", "选修课");
        COURSE_TYPES.put("5", "课后一小时");
    }

    public StudentCourseView(List<StudentCourse> list) {
        super(list);
    }

    @Override
    List<String> getTitles() {
        List<String> list = new ArrayList<>();
        list.add("所属校区");
        list.add("所属班级");
        list.add("学生姓名");
        list.add("学生性别");
        list.add("是否选课");
        list.add("课程定义(课程名称)");
        list.add("课程类型");
        return list;
    }

    @Override
    List<Object> getValues(StudentCourse course) {
        List<Object> list = new ArrayList<>();
        list.add(course.getStudent().getCampusName());
        list.add(course.getStudent().getGradeName() + course.getStudent().getClazzName() + "班");
        list.add(course.getStudent().getName());
        list.add(SEXES.get(course.getStudent().getSex()));
        list.add(CHOSENS.get(course.getChosen()));
        if (course.getOptionalCourse() != null) {
            list.add(course.getOptionalCourse().getCourseName() + "(" + course.getOptionalCourse().getAliasName() + ")");
            list.add(COURSE_TYPES.get(course.getOptionalCourse().getXxlb()));
        } else {
            list.add("—");
            list.add("—");
        }
        return list;
    }

    @Override
    void init() {
        setName("学生选课列表");
    }
}