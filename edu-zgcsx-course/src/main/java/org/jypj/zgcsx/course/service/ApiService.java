package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.entity.*;

import java.util.List;

/**
 * @author qi_ma
 * @version 1.0 2017/12/8 17:46
 */
public interface ApiService {
    List<SchoolXq> selectCampuses(SchoolXq schoolXq);

    List<SchoolXq> selectCampuses();

    List<Grade> selectGrades(Grade grade);

    List<Clazz> selectClazzes(Clazz clazz);

    /**
     * 查询学生不带翻译
     *
     * @param page
     * @param student
     * @return
     */
    Page<Student> selectStudents(Page<Student> page, Student student);

    int selectStudentCount(Student student);

    List<CourseTime> selectCourseTimeByCampusId(String campusId);

    List<CodeMapData> selectCodeMapDataByCode(String code);

    List<CodeMapData> selectCodeMapDataByCode(String... codes);
}
