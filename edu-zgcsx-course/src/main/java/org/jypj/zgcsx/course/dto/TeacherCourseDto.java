package org.jypj.zgcsx.course.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jypj.zgcsx.common.utils.DateUtil;
import org.jypj.zgcsx.course.entity.CourseTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师课表信息
 *
 * @author yu_chen
 * @create 2017-11-22 18:30
 **/
@Data
public class TeacherCourseDto {

    /**
     * 周次数量
     */
    private int weekNum = 7;

    /**
     * 节次数组
     */
    private static String[] sectionValues = {"一", "二", "三", "四", "五", "六", "七", "八"};

    private String teacherId;
    private String teacherName;
    /**
     * 课表数据
     */
    private List<CourseSection> courseSections;

    /**
     * 初始化teacherCourseDto
     *
     * @return
     */
    public TeacherCourseDto initTeacherCourseDto(List<CourseTime> courseTimes) {
        List<CourseSection> courseSections = new ArrayList<>();
        for (int i = 0; i < courseTimes.size(); i++) {
            CourseTime courseTime = courseTimes.get(i);
            CourseSection courseSection = new CourseSection(String.format("第%s节", sectionValues[i]), courseTime.getKssj() + "~" + courseTime.getJssj());
            List<CourseSection.Course> courses = new ArrayList<>();
            for (int j = 0; j < weekNum; j++) {
                CourseSection.Course course = new CourseSection().new Course();
                if (DateUtil.getCurrentWeek() == j) {
                    course.setCurrentDay(true);
                }
                courses.add(course);
            }
            courseSection.setCourses(courses);
            courseSections.add(courseSection);
        }
        this.setCourseSections(courseSections);
        return this;
    }

    public TeacherCourseDto() {
    }

    public TeacherCourseDto(int weekNum) {
        this.weekNum = weekNum;
    }

    public TeacherCourseDto(String teacherId, String teacherName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    @Data
    public class CourseSection {
        /**
         * 节次
         */
        private String sectionIndex;
        /**
         * 节次时间
         */
        private String sectionTime;

        /**
         * 节次数据
         */
        private List<Course> courses;


        public CourseSection() {
        }

        public CourseSection(String sectionIndex, String sectionTime) {
            this.sectionIndex = sectionIndex;
            this.sectionTime = sectionTime;
        }

        /**
         * 单次课程的对象
         */
        @Data
        public class Course {

            private String workDayId = "";
            /**
             * 日程校区map
             */
            private Map<String, Object> workDayCampusMap = new HashMap<>();
            /**
             * 班级ID
             */
            private String classId = "";

            /**
             * className
             */
            private String className = "";
            /**
             * courseID
             */
            private String courseId = "";
            /**
             * courseName
             */
            private String courseName = "";

            /**
             * 教师名称
             */
            private String teacherName = "";

            /**
             * 校区ID
             */
            private String campusId = "";

            /**
             * 校区名称
             */
            private String campusName = "";

            /**
             * 前端需要的字段
             */
            private boolean addView = true;

            /**
             * 前端需要的字段
             */
            private boolean editView = false;

            /**
             * 是否当前天
             */
            private boolean currentDay = false;

        }
    }
}

