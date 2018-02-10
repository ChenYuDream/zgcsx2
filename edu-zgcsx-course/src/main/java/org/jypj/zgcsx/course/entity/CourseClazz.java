package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 选修课班级限制表
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@TableName("KC_COURSE_CLAZZ")
public class CourseClazz extends BaseEntity<CourseClazz> {

    private static final long serialVersionUID = 1L;

    /**
     * 班级ID
     */
    @TableField("CLAZZ_ID")
    private String clazzId;
    /**
     * 课程属性表ID
     */
    @TableField("OPTIONAL_COURSE_ID")
    private String optionalCourseId;


    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
    }

    public String getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(String optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }


    @Override
    public String toString() {
        return "CourseClazz{" +
                ", clazzId=" + clazzId +
                ", optionalCourseId=" + optionalCourseId +
                "}";
    }
}
