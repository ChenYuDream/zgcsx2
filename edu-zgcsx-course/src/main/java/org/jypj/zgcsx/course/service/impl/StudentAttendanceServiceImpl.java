package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.dao.StudentAttendanceMapper;
import org.jypj.zgcsx.course.dao.StudentCourseMapper;
import org.jypj.zgcsx.course.dao.StudentMapper;
import org.jypj.zgcsx.course.entity.Student;
import org.jypj.zgcsx.course.entity.StudentAttendance;
import org.jypj.zgcsx.course.entity.StudentCourse;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.StudentAttendanceService;
import org.jypj.zgcsx.course.service.WorkDayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class StudentAttendanceServiceImpl extends BaseServiceImpl<StudentAttendanceMapper, StudentAttendance> implements StudentAttendanceService {

    @Resource
    private WorkDayService workDayService;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentCourseMapper studentCourseMapper;

    @Override
    public int saveAttendance(StudentAttendance studentAttendance) {
        int l = 0;
        StudentAttendance _studentAttendance = null;
        StudentAttendance select = null;
        for (int i = 0; i <studentAttendance.getStudentIds().length; i++) {
            _studentAttendance = new StudentAttendance();
            _studentAttendance.setStudentId(studentAttendance.getStudentIds()[i]);
            _studentAttendance.setWorkDayId(studentAttendance.getWorkDayId());
            _studentAttendance.setCourseType("1");
            select = baseMapper.selectOne(_studentAttendance);
            if(select == null){ //记录不存在添加
                l += baseMapper.insert(_studentAttendance);
            }
        }
        return l;
    }

    @Override
    public int saveChooseAttendance(StudentAttendance studentAttendance) {
        int l = 0;
        StudentAttendance _studentAttendance = null;
        StudentAttendance select = null;
        List<Map<String, Object>> list = studentAttendance.getChooseAttendance();
        for (int i = 0; i <list.size(); i++) {
            _studentAttendance = new StudentAttendance();
            _studentAttendance.setStudentId((String) list.get(i).get("studentId"));
            _studentAttendance.setWorkDayId(studentAttendance.getWorkDayId());
            _studentAttendance.setCourseType(studentAttendance.getCourseType());
            select = baseMapper.selectOne(_studentAttendance);
            if(select == null){ //记录不存在添加
                _studentAttendance.setDescription((String) list.get(i).get("description"));
                l += baseMapper.insert(_studentAttendance);
            }else{
                select.setDescription((String) list.get(i).get("description"));
                l += baseMapper.updateById(select);
            }
        }
        return l;
    }

    @Override
    public Page<StudentAttendance> selectBasicStudentAttendance(Page<StudentAttendance> page, StudentAttendance studentAttendance, Xnxq xnxq) {
        List<StudentAttendance> list = baseMapper.selectBasicStudentAttendance(page, studentAttendance, xnxq);
        for (StudentAttendance attendance : list) {
            //根据周次，星期数，节次 计算出上课时间
            attendance.setCourseTime(workDayService.getDate(xnxq, attendance.getWorkDay().getWeekOfTerm(), attendance.getWorkDay().getDayOfWeek()));
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<StudentAttendance> selectChooseStudentAttendance(Page<StudentAttendance> page, StudentAttendance studentAttendance, Xnxq xnxq) {
        List<StudentAttendance> list = baseMapper.selectChooseStudentAttendance(page, studentAttendance, xnxq);
        List<StudentAttendance> copyList = new ArrayList<>();
        LocalDateTime localDateTime = null;
        for (StudentAttendance attendance : list) {
            //根据周次，星期数，节次 计算出上课时间
            attendance.setCourseTime(workDayService.getDate(xnxq, attendance.getWorkDay().getWeekOfTerm(), attendance.getWorkDay().getDayOfWeek()));
            localDateTime = LocalDateTime.of(attendance.getCourseTime().getYear(), attendance.getCourseTime().getMonth(), attendance.getCourseTime().getDayOfMonth(), attendance.getWorkDay().getStartTime().getHour(), attendance.getWorkDay().getStartTime().getMinute());
            if(localDateTime.compareTo(LocalDateTime.now()) <= 0){ //课程开始时间<=当前服务器时间
                copyList.add(attendance);
            }
        }
        page.setRecords(copyList);
        return page;
    }

    @Override
    public Page<Student> selectAttendanceStudents(Page<Student> page, Student student) {
        page.setRecords(studentMapper.selectAttendanceStudents(page, student));
        return page;
    }

    @Override
    public Page<StudentCourse> selectChooseAttendanceStudents(Page<StudentCourse> page, StudentCourse studentCourse) {
        page.setRecords(studentCourseMapper.selectChooseAttendanceStudents(page, studentCourse));
        return page;
    }
}
