package org.jypj.zgcsx.course.util;

import org.jypj.zgcsx.course.dto.TeacherCourseDto;
import org.jypj.zgcsx.course.entity.WorkDay;

import java.util.List;

/**
 * 日程工具类
 *
 * @author yu_chen
 * @create 2018-01-08 11:17
 **/
public class WorkDayUtil {


    public static void initTeacherDto(TeacherCourseDto teacherCourseDto, List<WorkDay> workDays, boolean editable) {
        //将每个课程对应一个日程id
        workDays.forEach(workDay -> {
            Integer dayOfWeek = workDay.getDayOfWeek();
            try {
                TeacherCourseDto.CourseSection.Course course = teacherCourseDto.getCourseSections().get(workDay.getPeriod() - 1).getCourses().get(workDay.getDayOfWeek());
                boolean canEdit = editable && (dayOfWeek == 0 || dayOfWeek == 6);
                if (canEdit) {
                    course.setEditView(false);
                    course.setAddView(false);
                }
                course.getWorkDayCampusMap().put(workDay.getCampusId(), workDay.getId());
            } catch (Exception ignored) {

            }
        });
    }
}
