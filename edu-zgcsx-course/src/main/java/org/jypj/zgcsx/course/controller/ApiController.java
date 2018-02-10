package org.jypj.zgcsx.course.controller;


import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jypj.zgcsx.course.constant.DictionariesConstant.*;

/**
 * <p>
 * 公用方法 控制器
 * </p>
 *
 * @author yu_xiao
 * @since 2017-11-27
 */
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Resource
    private CourseProperties properties;

    /**
     * 查询校区
     *
     * @param schoolXq
     * @return
     */
    @RequestMapping("/campus")
    @ResponseBody
    public Result selectCampus(SchoolXq schoolXq) {
        return render(apiService.selectCampuses(schoolXq));
    }

    /**
     * 查询年级
     *
     * @param grade
     * @return
     */
    @RequestMapping("/grade")
    @ResponseBody
    public Result selectGrade(Grade grade) {
        return render(apiService.selectGrades(grade));
    }

    /**
     * 根据条件查询班级信息
     *
     * @param clazz
     * @return
     */
    @RequestMapping("/clazz")
    @ResponseBody
    public Result selectClazz(Clazz clazz) {
        return render(apiService.selectClazzes(clazz));
    }

    /**
     * 根据校区id查询教师信息
     *
     * @param page
     * @param teacher 查询条件：姓名，性别
     * @return
     */
    @RequestMapping("/teachers")
    @ResponseBody
    public Result selectTeachers(Page<Teacher> page, Teacher teacher) {
        return render(teacherService.selectTeachers(page, teacher));
    }

    /**
     * 根据班级id查询学生信息
     *
     * @param page
     * @param student 查询条件：班级id
     * @return
     */
    @RequestMapping("/students")
    @ResponseBody
    public Result selectStudents(Page<Student> page, Student student) {
        return render(apiService.selectStudents(page, student));
    }

    /**
     * 根据班级id查询学生 翻译校区，年级，班级
     *
     * @param page
     * @param student 查询条件：班级id
     * @return
     */
    @RequestMapping("/students/all")
    @ResponseBody
    public Result selectStudent(Page<Student> page, Student student) {
        return render(studentService.selectStudents(page, student));
    }

    /**
     * 根据班级id查询学生人数
     *
     * @param student 查询条件：班级id
     * @return
     */
    @RequestMapping("/student/count")
    @ResponseBody
    public Result selectStudents(Student student) {
        return render(apiService.selectStudentCount(student));
    }

    /**
     * 获取校区节次时间
     *
     * @param campusId :校区id
     * @return 返回接口数据 : isAll:-1表示非学校权限; 0表示学校权限
     */
    @RequestMapping("/course/time")
    @ResponseBody
    public List<CourseTime> getCourseTime(String campusId) {
        return apiService.selectCourseTimeByCampusId(campusId);
    }

    /**
     * 查询字典表
     *
     * @param code
     * @return
     */
    @RequestMapping("/codemap")
    @ResponseBody
    public List<CodeMapData> selectCodeMapData(String[] code) {
        return apiService.selectCodeMapDataByCode(code);
    }

    /**
     * 查询当前学年学期
     *
     * @return
     */
    @RequestMapping("/xnxq")
    @ResponseBody
    public Xnxq selectCurrentXnxq() {
        return xnxqService.selectCurrentXnxq();
    }

    /**
     * 查询所有学年学期
     *
     * @return
     */
    @RequestMapping("/xnxqs")
    @ResponseBody
    public List<Xnxq> xnxqs() {
        return xnxqService.selectAll();
    }

    /**
     * 通讯录顶级节点的parentId
     */
    private final static String TREE_TOP_PARENT_ID = "0";

    /**
     * 查询所有通讯录
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> selectAllTree() {
        return treeService.selectAll();
    }

    /**
     * 查询所有空间
     */
    @RequestMapping("/space")
    @ResponseBody
    public List<Map<String, Object>> selectAllSpaces() {
        List<Space> spaces = spaceService.selectAll();
        List<CodeMapData> codeMapDataList = apiService.selectCodeMapDataByCode(SPACE_CAMPUS, SPACE_FLOOR, SPACE_TYPE, SPACE_ARCH);
        Map<String, Map<String, String>> codeDataMap = codeMapDataList.stream().collect(Collectors.groupingBy(CodeMapData::getCode, Collectors.toMap(CodeMapData::getItemValue, CodeMapData::getItemText)));
        for (Space space : spaces) {
            space.setArchName(codeDataMap.get(SPACE_ARCH).get(space.getArchCode()));
            space.setCampusName(codeDataMap.get(SPACE_CAMPUS).get(space.getCampusCode()));
            space.setFloorName(codeDataMap.get(SPACE_FLOOR).get(space.getFloorCode()));
            space.setTypeName(codeDataMap.get(SPACE_TYPE).get(space.getTypeCode()));
        }
        return spaces.stream().collect(Collectors.groupingBy(Space::getCampusCode, Collectors.groupingBy(Space::getArchCode, Collectors.groupingBy(Space::getFloorCode))))
                .entrySet().stream().map(campus -> {
                    Map<String, Object> _map = new HashMap<>();
                    _map.put("code", campus.getKey());
                    _map.put("name", codeDataMap.get(SPACE_CAMPUS).get(campus.getKey()));
                    _map.put("list", campus.getValue().entrySet().stream().map(arch -> {
                        Map<String, Object> _arch = new HashMap<>();
                        _arch.put("code", arch.getKey());
                        _arch.put("name", codeDataMap.get(SPACE_ARCH).get(arch.getKey()));
                        _arch.put("list", arch.getValue().entrySet().stream().map(floor -> {
                            Map<String, Object> _floor = new HashMap<>();
                            _floor.put("code", floor.getKey());
                            _floor.put("name", codeDataMap.get(SPACE_FLOOR).get(floor.getKey()));
                            _floor.put("list", floor.getValue().stream().sorted(Comparator.comparing(Space::getTypeCode)));
                            return _floor;
                        }).sorted(Comparator.comparing(map -> (String) map.get("code"))));
                        return _arch;
                    }).sorted(Comparator.comparing(map -> (String) map.get("code"))));
                    return _map;
                }).sorted(Comparator.comparing(map -> (String) map.get("code"))).collect(Collectors.toList());
    }

    /**
     * 查询所有空间
     */
    @RequestMapping("/spaces")
    @ResponseBody
    public Result selectAllSpaces(Page<Space> page) {
        return new Result(spaceService.selectAll(page));
    }

    /**
     * 查询评价下拉
     */
    @RequestMapping("/evaluate")
    @ResponseBody
    public List<CodeMapData> selectEvaluate() {
        return apiService.selectCodeMapDataByCode(EVA_LEVEL);
    }
}

