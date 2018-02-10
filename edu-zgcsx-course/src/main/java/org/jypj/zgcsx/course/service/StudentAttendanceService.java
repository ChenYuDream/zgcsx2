package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.Student;
import org.jypj.zgcsx.course.entity.StudentAttendance;
import org.jypj.zgcsx.course.entity.StudentCourse;
import org.jypj.zgcsx.course.entity.Xnxq;

/**
 * <p>
 * 考勤表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface StudentAttendanceService extends BaseService<StudentAttendance> {

    /**
     * 添加基础课程考勤
     * @param studentAttendance
     * @return
     */
    int saveAttendance(StudentAttendance studentAttendance);

    /**
     * 添加开放课程考勤
     * @param studentAttendance
     * @return
     */
    int saveChooseAttendance(StudentAttendance studentAttendance);

    /**
     * 获取基础缺勤记录
     * @param page
     * @param studentAttendance
     * @param xnxq
     * @return
     */
    Page<StudentAttendance> selectBasicStudentAttendance(Page<StudentAttendance> page, StudentAttendance studentAttendance, Xnxq xnxq);

    /**
     * 获取选修缺勤记录
     * @param page
     * @param studentAttendance
     * @param xnxq
     * @return
     */
    Page<StudentAttendance> selectChooseStudentAttendance(Page<StudentAttendance> page, StudentAttendance studentAttendance, Xnxq xnxq);

    /**
     * 查询缺勤学生
     * @param page
     * @param student
     * @return
     */
    Page<Student> selectAttendanceStudents(Page<Student> page, Student student);

    /**
     * 查询开放课程缺勤学生
     * @param page
     * @param studentCourse
     * @return
     */
    Page<StudentCourse> selectChooseAttendanceStudents(Page<StudentCourse> page, StudentCourse studentCourse);
}
