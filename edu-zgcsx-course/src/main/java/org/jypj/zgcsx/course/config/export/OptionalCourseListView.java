package org.jypj.zgcsx.course.config.export;

import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.course.entity.OptionalCourse;
import org.jypj.zgcsx.course.entity.Teacher;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OptionalCourseListView extends CourseXlsView<OptionalCourse> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Map<String, String> COURSE_TYPES = new HashMap<>();
    private static final Map<String, String> COURSE_STATUSES = new HashMap<>();
    private String name;

    static {
        COURSE_TYPES.put("2", "选修课");
        COURSE_TYPES.put("5", "课后一小时");
        COURSE_STATUSES.put("1", "已录入");
        COURSE_STATUSES.put("2", "已发布");
    }

    public OptionalCourseListView(String name, List<OptionalCourse> list) {
        super(list);
        this.name = name;
    }

    @Override
    List<String> getTitles() {
        List<String> list = new ArrayList<>();
        list.add("所属校区");
        list.add("课程定义");
        list.add("课程名称");
        list.add("任课教师");
        list.add("课程地点");
        list.add("课程时间");
        list.add("课程班级");
        list.add("课程类型");
        list.add("已选学生数量");
        list.add("课程人数上限");
        list.add("课程状态");
        return list;
    }

    @Override
    List<Object> getValues(OptionalCourse course) {
        List<Object> list = new ArrayList<>();
        list.add(course.getCampusName());
        list.add(course.getCourseName());
        list.add(course.getAliasName());
        list.add(StringUtils.join(course.getTeachers().stream().map(Teacher::getName).collect(Collectors.toList()), ","));
        list.add(course.getSpace().getName());
        list.add(course.getStartTime().format(DATE_FORMATTER) + "~" + course.getEndTime().format(DATE_FORMATTER));
        list.add(StringUtils.join(course.getClazzes().stream().map(clazz -> clazz.getGradeName() + clazz.getName()).collect(Collectors.toList()), ","));
        list.add(COURSE_TYPES.get(course.getCourseType()));
        list.add(course.getStudentCount());
        list.add(course.getLimitCount());
        list.add(COURSE_STATUSES.get(course.getCourseStatus()));
        return list;
    }

    @Override
    void init() {
        setName(name);
    }
}