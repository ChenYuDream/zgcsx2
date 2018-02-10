package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_STUDENT_ATTENDANCE")
@Data
public class StudentAttendance extends BaseEntity<StudentAttendance> {

    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @TableField("STUDENT_ID")
    private String studentId;
    /**
     * 日程ID
     */
    @TableField("WORK_DAY_ID")
    private String workDayId;
    /**
     * 缺勤描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 选课类别
     */
    @TableField("COURSE_TYPE")
    private String courseType;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;
    /**
     * 班级id
     */
    @TableField(exist = false)
    private String clazzid;
    /**
     * 选修课id
     */
    @TableField(exist = false)
    private String optionalCourseId;
    /**
     * 缺勤人数
     */
    @TableField(exist = false)
    private String attendanceCount;
    /**
     * 第几周
     */
    @TableField(exist = false)
    private Integer weekOfTerm;
    /**
     * 周一至周五
     */
    @TableField(exist = false)
    private Integer dayOfWeek;
    /**
     * 所属节次
     */
    @TableField(exist = false)
    private Integer period;
    /**
     * 课程时间
     */
    @TableField(exist = false)
    private LocalDate courseTime;
    /**
     * 日程表
     */
    @TableField(exist = false)
    private WorkDay workDay;

    /**
     * 记录学生id数组
     */
    @TableField(exist = false)
    private String[] studentIds;

    /**
     * 记录开放课程的考勤信息
     */
    @TableField(exist = false)
    List<Map<String, Object>> chooseAttendance;

    /**
     * 课程id
     */
    @TableField(exist = false)
    private String courseId;

    /**
     * 校区id
     */
    @TableField(exist = false)
    private String campusId;

    /**
     * 教师id
     */
    @TableField(exist = false)
    private String teacherId;

    @Override
    public String toString() {
        return "StudentAttendance{" +
                ", studentId=" + studentId +
                ", workDayId=" + workDayId +
                "}";
    }
}
