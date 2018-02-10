package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.entity.*;
import org.jypj.zgcsx.course.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qi_ma
 * @version 1.0 2017/12/8 17:47
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Resource
    private SchoolXqService schoolXqService;
    @Resource
    private GradeService gradeService;
    @Resource
    private ClazzService clazzService;
    @Resource
    private StudentService studentService;
    @Resource
    private CourseTimeService courseTimeService;
    @Resource
    private CodeMapDataService codeMapDataService;

    @Override
    public List<SchoolXq> selectCampuses(SchoolXq schoolXq) {
        EntityWrapper<SchoolXq> entityWrapper = new EntityWrapper<>();
        if (StringUtil.isNotEmpty(schoolXq.getId())) {
            entityWrapper.eq("ID", schoolXq.getId());
        }
        entityWrapper.eq("VALID", 1);
        return schoolXqService.selectList(entityWrapper);
    }

    @Override
    public List<SchoolXq> selectCampuses() {
        EntityWrapper<SchoolXq> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("VALID", 1);
        return schoolXqService.selectList(entityWrapper);
    }

    @Override
    public List<Grade> selectGrades(Grade grade) {
        EntityWrapper<Grade> entityWrapper = new EntityWrapper<>();
        if (StringUtil.isNotEmpty(grade.getId())) {
            entityWrapper.eq("ID", grade.getId());
        }
        if (StringUtil.isNotEmpty(grade.getCampusId())) {
            entityWrapper.eq("XQID", grade.getCampusId());
        }
        entityWrapper.eq("VALID", 1);
        entityWrapper.orderBy("SSNJ");
        return gradeService.selectList(entityWrapper);
    }

    @Override
    public List<Clazz> selectClazzes(Clazz clazz) {
        EntityWrapper<Clazz> entityWrapper = new EntityWrapper<>();
        if (StringUtil.isNotEmpty(clazz.getId())) {
            entityWrapper.eq("ID", clazz.getId());
        }
        if (StringUtil.isNotEmpty(clazz.getSchoolId())) {
            entityWrapper.eq("XXID", clazz.getSchoolId());
        }
        if (StringUtil.isNotEmpty(clazz.getCampusId())) {
            entityWrapper.eq("XQID", clazz.getCampusId());
        }
        if (StringUtil.isNotEmpty(clazz.getGradeId())) {
            entityWrapper.eq("JBID", clazz.getGradeId());
        }
        entityWrapper.eq("VALID", 1);
        entityWrapper.orderBy("to_number(BJMC)");
        return clazzService.selectList(entityWrapper);
    }

    @Override
    public Page<Student> selectStudents(Page<Student> page, Student student) {
        EntityWrapper<Student> entityWrapper = new EntityWrapper<>();
        if (StringUtil.isNotEmpty(student.getGradeId())) {
            entityWrapper.eq("JBID", student.getGradeId());
        }
        if (StringUtil.isNotEmpty(student.getClazzId())) {
            entityWrapper.eq("BJID", student.getClazzId());
        }
        entityWrapper.eq("VALID", 1);
        entityWrapper.orderBy("XM, ID");
        return studentService.selectPage(page, entityWrapper);
    }

    @Override
    public int selectStudentCount(Student student) {
        return studentService.selectCount(new EntityWrapper<Student>()
                .eq("BJID", student.getClazzId())
                .eq("VALID", 1)
        );
    }

    @Override
    public List<CourseTime> selectCourseTimeByCampusId(String campusId) {
        return courseTimeService.selectList(new EntityWrapper<CourseTime>()
                .eq("xqid", campusId)
                .eq("kczc", 1));
    }

    @Override
    public List<CodeMapData> selectCodeMapDataByCode(String code) {
        return codeMapDataService.selectCodeMapDataByCode(code);
    }

    @Override
    public List<CodeMapData> selectCodeMapDataByCode(String... codes) {
        return codeMapDataService.selectCodeMapDataByCodes(codes);
    }
}
