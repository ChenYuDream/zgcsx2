package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 班级课程表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Data
@TableName("KC_CLAZZ_TIMETABLE")
public class ClazzTimetable extends BaseEntity<ClazzTimetable> {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    @TableField("CLAZZ_ID")
    private String clazzId;
    /**
     * 日程ID
     */
    @TableField("WORK_DAY_ID")
    private String workDayId;
    /**
     * 教当前这次课的教师ID
     */
    @TableField("TEACHER_ID")
    private String teacherId;
    /**
     * 课程定义表ID
     */
    @TableField("COURSE_ID")
    private String courseId;
    /**
     * 班级名称
     */
    @TableField(exist = false)
    private String clazzName;
    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 年级Id
     */
    @TableField(exist = false)
    private String gradeId;
    /**
     * 年级名称
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 教师名称
     */
    @TableField(exist = false)
    private String teacherName;

    /**
     * 周次索引
     */
    @TableField(exist = false)
    private Integer weekIndex;

    /**
     * 节次索引
     */
    @TableField(exist = false)
    private Integer sectionIndex;

    /**
     * 日程对象
     */
    @TableField(exist = false)
    private WorkDay workDay;

    /**
     * 学生人数
     */
    @TableField(exist = false)
    private Integer studentCount;

    /**
     * 已选学生人数
     */
    @TableField(exist = false)
    private Integer chooseStudentCount;

    /**
     * 校区id
     */
    @TableField(exist = false)
    private String campusId;

    /**
     * 校区名称
     */
    @TableField(exist = false)
    private String campusName;

    /**
     * 课程层次
     */
    @TableField(exist = false)
    private String kccc;

    /**
     * 课程等级
     */
    @TableField(exist = false)
    private String kcdj;

    /**
     * 课程类别
     */
    @TableField(exist = false)
    private String kclb;

    /**
     * 学习类别
     */
    @TableField(exist = false)
    private String xxlb;

    /**
     * 课程介绍
     */
    @TableField(exist = false)
    private String description;

    /**
     * 被修改的教师
     */
    @TableField(exist = false)
    private String oldTeacherId;

    /**
     * 教师
     */
    @TableField(exist = false)
    List<Teacher> teachers;
}
