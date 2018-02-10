package org.jypj.zgcsx.course.config.export;

import org.jypj.zgcsx.course.entity.StudentCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCourseAutoAllotView extends CourseXlsView<StudentCourse> {
    private static final Map<String, String> SEXES = new HashMap<>();
    private static final Map<String, String> ALLOTS = new HashMap<>();

    static {
        SEXES.put("1", "男");
        SEXES.put("2", "女");
        ALLOTS.put("1", "已分配");
        ALLOTS.put("0", "未分配");
    }

    public StudentCourseAutoAllotView(List<StudentCourse> list) {
        super(list);
    }

    @Override
    List<String> getTitles() {
        List<String> list = new ArrayList<>();
        list.add("所属校区");
        list.add("所属班级");
        list.add("学生姓名");
        list.add("学生学号");
        list.add("学生性别");
        list.add("课程定义(课程名称)");
        list.add("是否已自动分配");
        return list;
    }

    @Override
    List<Object> getValues(StudentCourse course) {
        List<Object> list = new ArrayList<>();
        list.add(course.getStudent().getCampusName());
        list.add(course.getStudent().getGradeName() + course.getStudent().getClazzName() + "班");
        list.add(course.getStudent().getName());
        list.add(course.getStudent().getCode());
        list.add(SEXES.get(course.getStudent().getSex()));
        if(course.getOptionalCourse() != null){
            list.add(course.getOptionalCourse().getCourseName() + "(" + course.getOptionalCourse().getAliasName() + ")");
        }else{
            list.add("—");
        }
        course.setAutoAllot(course.getAutoAllot() == null ? "0" : course.getAutoAllot());
        list.add(ALLOTS.get(course.getAutoAllot()));
        return list;
    }

    @Override
    void init() {
        setName("选修课自动分配");
    }
}