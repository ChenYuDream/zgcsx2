package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.dao.*;
import org.jypj.zgcsx.course.entity.*;
import org.jypj.zgcsx.course.error.CourseException;
import org.jypj.zgcsx.course.service.*;
import org.jypj.zgcsx.course.vo.SpaceTimetableVo;
import org.jypj.zgcsx.course.vo.TeacherTimetableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 选修课详情表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class OptionalCourseServiceImpl extends BaseServiceImpl<OptionalCourseMapper, OptionalCourse> implements OptionalCourseService {
    @Resource
    private TeacherCourseService teacherCourseService;
    @Resource
    private OptionalTimetableService optionalTimetableService;
    @Resource
    private SchoolXqService schoolXqService;
    @Resource
    private CourseClazzService courseClazzService;
    @Resource
    private StudentCourseService studentCourseService;
    @Resource
    private SpaceService spaceService;
    @Resource
    private EvaluateElementService evaluateElementService;
    @Resource
    private EvaluateElementValueService evaluateElementValueService;

    @Override
    public OptionalCourse selectById(Serializable id) {
        OptionalCourse course = super.selectById(id);
        if (course == null) {
            return null;
        }
        if (course.getWorkDays() != null && course.getWorkDays().size() != 0) {
            List<WorkDay> workDays = course.getWorkDays();
            workDays = workDays.stream().peek(workDay -> {
                workDay.setDate(workDayService.getDate(workDay.getXnxq(), workDay.getWeekOfTerm(), workDay.getDayOfWeek()));
            }).collect(Collectors.toList());
            course.setWorkDays(workDays);
        }
        return course;
    }

    @Override
    public Page<OptionalCourse> selectAll(Page<OptionalCourse> page) {
        if (page.getCondition() == null) {
            page.setCondition(new HashMap<>());
        }
        page.setRecords(baseMapper.selectAll(page, page.getCondition()));
        return page;
    }

    @Override
    public List<OptionalCourse> selectAll(Map<String, Object> condition) {
        return baseMapper.selectAll(condition);
    }

    @Override
    public boolean deleteById(Serializable sid) {
        String id = (String) sid;
        courseClazzService.deleteByOptionalCourseId(id);
        optionalTimetableService.deleteByOptionalCourseId(id);
        studentCourseService.deleteById(id);
        teacherCourseService.deleteByOptionalCourseId(id);
        return super.deleteById(id);
    }

    @Override
    public boolean save(OptionalCourse optionalCourse, Xnxq xnxq, UserInfo userInfo) {
        boolean flag = true;
        checkOptionalCourse(optionalCourse);//检查
        insertDefault(optionalCourse, xnxq, userInfo);//插入默认值
        List<WorkDay> workDays = optionalCourse.getWorkDays();//时间表
        List<Teacher> teachers = optionalCourse.getTeachers();//任课教师列表
        List<Clazz> clazzes = optionalCourse.getClazzes();//班级限制列表
        //保存日期信息
        workDays = workDayService.insertWorkDays(workDays, xnxq, optionalCourse.getCampusId(), optionalCourse.getCourseType());
        workDays.sort(Comparator.comparing(WorkDay::getWeekOfTerm).thenComparing(WorkDay::getDayOfWeek));
        //如果是编辑
        if (StringUtils.isNotEmpty(optionalCourse.getId())) {
            deleteBeforeEdit(optionalCourse);
        } else {//如果是新增
        }
        optionalCourse.setWorkDays(workDays);
        //验证教师时间冲突
        checkTeacherTime(optionalCourse, xnxq);
        //验证上课地点冲突
        checkSpaceMerge(optionalCourse, workDays, xnxq);
        //设置开始时间结束时间optionaloptional
        optionalCourse.setStartTime(workDays.get(0).getDate());
        optionalCourse.setEndTime(workDays.get(workDays.size() - 1).getDate());
        if (optionalCourse.getStartTime().atTime(workDays.get(0).getStartTime()).compareTo(optionalCourse.getChooseEndTime()) < 0) {
            throw new CourseException("course.optional.merge-start-time-choose-end-time");
        }
        if (optionalCourse.getEndTime().compareTo(xnxq.getCourseEndDate()) > 0) {
            throw new CourseException("course.optional.merge-end-time-xnxq-course-end-time");
        }
        //保存选课信息
        flag = flag & this.insertOrUpdateAllColumn(optionalCourse);
        //根据教师列表生成任课教师集合
        List<TeacherCourse> teacherCourses = teachers.stream().map(teacher -> {
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setTeacherId(teacher.getId());
            teacherCourse.setOptionalCourseId(optionalCourse.getId());
            return teacherCourse;
        }).collect(Collectors.toList());
        //保存任课教师信息
        flag = flag & teacherCourseService.insertBatch(teacherCourses);
        //生成时间表信息
        List<OptionalTimetable> optionalTimetables = workDays.stream().map(workDay -> {
            OptionalTimetable optionalTimetable = new OptionalTimetable();
            optionalTimetable.setOptionalCourseId(optionalCourse.getId());
            optionalTimetable.setWorkDayId(workDay.getId());
            return optionalTimetable;
        }).collect(Collectors.toList());
        //保存课程时间表信息
        flag = flag & optionalTimetableService.insertBatch(optionalTimetables);
        //生成班级限制列表
        List<CourseClazz> courseClazzes = clazzes.stream().map(clazz -> {
            CourseClazz courseClazz = new CourseClazz();
            courseClazz.setClazzId(clazz.getId());
            courseClazz.setOptionalCourseId(optionalCourse.getId());
            return courseClazz;
        }).collect(Collectors.toList());
        //保存班级限制列表
        flag = flag & courseClazzService.insertBatch(courseClazzes);
        //保存学业评价量规
        flag = flag & saveEvaluateElement(optionalCourse);
        return flag;
    }

    /**
     * 保存学业评价量规
     */
    private boolean saveEvaluateElement(OptionalCourse optionalCourse) {
        if (Objects.equals("5", optionalCourse.getCourseType())) {
            return true;
        }
        final int[] i = {0};
        boolean flag;
        List<EvaluateElement> evaluateElements = optionalCourse.getEvaluateElements();//学业评价要素
        evaluateElements.forEach(evaluateElement -> {
            evaluateElement.setOptionalCourseId(optionalCourse.getId());
            evaluateElement.setSortOrder(++i[0]);
        });
        flag = evaluateElementService.insertBatch(evaluateElements);
        List<EvaluateElementValue> evaluateElementValues = evaluateElements.stream().flatMap(
                evaluateElement -> evaluateElement.getEvaluateElementValues().stream().peek(
                        evaluateElementValue -> {
                            evaluateElementValue.setElementId(evaluateElement.getId());
                        })).collect(Collectors.toList());
        flag = flag & evaluateElementValueService.insertBatch(evaluateElementValues);
        return flag;
    }

    private void deleteBeforeEdit(OptionalCourse optionalCourse) {
        String id = optionalCourse.getId();
        //删除班级限制信息
        courseClazzService.deleteByOptionalCourseId(id);
        //删除时间表信息
        optionalTimetableService.deleteByOptionalCourseId(id);
        //删除任课教师信息
        teacherCourseService.deleteByOptionalCourseId(id);
        //删除学业评价量规
        evaluateElementService.deleteByOptionalCourseId(id);
    }

    /**
     * 基础数据检查
     */
    private void checkOptionalCourse(OptionalCourse optionalCourse) {
        List<WorkDay> workDays = optionalCourse.getWorkDays();
        Space space = optionalCourse.getSpace();
        BaseCourse baseCourse = optionalCourse.getBaseCourse();
        if (StringUtils.isEmpty(optionalCourse.getAliasName()))
            throw new CourseException("course.optional.non-alias-name");
        if (optionalCourse.getChooseStartTime() == null)
            throw new CourseException("course.optional.non-choose-start-time");
        if (optionalCourse.getChooseEndTime() == null)
            throw new CourseException("course.optional.non-choose-end-time");
        if (optionalCourse.getChooseStartTime().compareTo(optionalCourse.getChooseEndTime()) > 0)
            throw new CourseException("course.optional.merge-choose-start-end");
        if (StringUtils.isEmpty(optionalCourse.getCampusId()))
            throw new CourseException("course.optional.non-campus");
        if (baseCourse == null || StringUtils.isEmpty(baseCourse.getId()))
            throw new CourseException("course.optional.non-choose-course");
        if (workDays == null || workDays.size() == 0)
            throw new CourseException("course.optional.non-choose-work-day");
        if (StringUtils.isEmpty(optionalCourse.getCourseType()))
            throw new CourseException("course.optional.non-course-type");
        if (!"2".equals(optionalCourse.getCourseType()) && !"5".equals(optionalCourse.getCourseType()))
            throw new CourseException("course.optional.un-support-course-type");
        if (space == null || StringUtils.isEmpty(space.getId()))
            throw new CourseException("course.optional.non-space");
        if (optionalCourse.getLimitCount() == null)
            throw new CourseException("course.optional.non-limit-count");
        if (optionalCourse.getLimitCount() > space.getVolume())
            throw new CourseException("course.optional.merge-limit-count-space");
        if (optionalCourse.getTeachers() == null || optionalCourse.getTeachers().size() == 0)
            throw new CourseException("course.optional.non-choose-teacher");
        if (optionalCourse.getClazzes() == null || optionalCourse.getClazzes().size() == 0)
            throw new CourseException("course.optional.non-choose-clazz");
        checkEvaluateElement(optionalCourse);
    }

    /**
     * 学业评价要素检查
     */
    private void checkEvaluateElement(OptionalCourse optionalCourse) {
        if (Objects.equals("5", optionalCourse.getCourseType())) {
            return;
        }
        List<EvaluateElement> evaluateElements = optionalCourse.getEvaluateElements();
        if (evaluateElements == null || evaluateElements.size() == 0) {
            throw new CourseException("course.optional.non-evaluate-element");
        }
        for (EvaluateElement evaluateElement : evaluateElements) {
            if (StringUtils.isEmpty(evaluateElement.getElement())) {
                throw new CourseException("course.optional.non-evaluate-element-element");
            }
            for (EvaluateElementValue evaluateElementValue : evaluateElement.getEvaluateElementValues()) {
                if (StringUtils.isEmpty(evaluateElementValue.getContent())) {
                    throw new CourseException("course.optional.non-evaluate-element-value");
                }
            }
        }
        Map<String, Long> elementsCount = evaluateElements.stream().collect(Collectors.groupingBy(EvaluateElement::getElement, Collectors.counting())).entrySet().stream().filter(
                entry -> entry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
        );
        if (elementsCount.size() > 0) {
            throw new CourseException("course.optional.merge-evaluate-element");
        }
    }

    //默认值设置
    private void insertDefault(OptionalCourse optionalCourse, Xnxq xnxq, UserInfo userInfo) {
        BaseCourse course = optionalCourse.getBaseCourse();
        Space space = optionalCourse.getSpace();
        optionalCourse.setCourseId(course.getId());
        optionalCourse.setUserId(userInfo.getId());
        optionalCourse.setSpaceId(space.getId());
        optionalCourse.setCourseStatus("1");
        optionalCourse.setSchoolId(schoolXqService.selectById(optionalCourse.getCampusId()).getSchoolId());
        optionalCourse.setXn(xnxq.getXn());
        optionalCourse.setXq(xnxq.getXq());
    }

    private void checkSpaceMerge(OptionalCourse optionalCourse, List<WorkDay> workDays, Xnxq xnxq) {
        Space space = spaceService.selectById(optionalCourse.getSpaceId());
        if (space.getRepeatable()) {
            if (space.getVolume() < optionalCourse.getLimitCount()) {
                throw new CourseException("course.optional.max-space-volume", space.getVolume());
            }
        }
        //根据地点ID和学年学期查询所有占用该地点的时间信息
        List<SpaceTimetableVo> spaceTimetables = workDayService.selectSpaceTimetablesBySpaceId(optionalCourse.getSpaceId(), xnxq);
        spaceTimetables = spaceTimetables.stream().map(timetable -> {
            List<OptionalCourse> courses = timetable.getOptionalCourses().stream().filter(course -> !Objects.equals(course.getId(), optionalCourse.getId())).collect(Collectors.toList());
            if (courses == null || courses.size() == 0) {
                return null;
            } else {
                timetable.setOptionalCourses(courses);
            }
            return timetable;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        for (SpaceTimetableVo timetable : spaceTimetables) {
            for (WorkDay day2 : workDays) {
                if (merge(timetable.getWorkDay(), day2)) {
                    if (!space.getRepeatable()) {
                        throw new CourseException("course.optional.merge-space",
                                workDayService.getDate(xnxq, timetable.getWorkDay().getWeekOfTerm(), timetable.getWorkDay().getDayOfWeek()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                timetable.getWorkDay().getStartTime(),
                                timetable.getWorkDay().getEndTime(),
                                timetable.getOptionalCourses().get(0).getCourseName(),
                                timetable.getOptionalCourses().get(0).getAliasName());
                    } else {
                        int existsCount = timetable.getOptionalCourses().stream().mapToInt(OptionalCourse::getLimitCount).sum();
                        if (existsCount + optionalCourse.getLimitCount() > space.getVolume()) {
                            throw new CourseException("course.optional.max-space-volume-exists",
                                    workDayService.getDate(xnxq, timetable.getWorkDay().getWeekOfTerm(), timetable.getWorkDay().getDayOfWeek()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    timetable.getWorkDay().getPeriod(),
                                    existsCount, space.getVolume());
                        }
                    }
                }
            }
        }
    }

    /**
     * 验证时间冲突
     *
     * @param optionalCourse 课程详情
     * @param xnxq           学年学期
     */
    private void checkTeacherTime(OptionalCourse optionalCourse, Xnxq xnxq) {
        List<String> teacherIds = optionalCourse.getTeachers().stream().map(Teacher::getId).collect(Collectors.toList());
        List<WorkDay> workDays = optionalCourse.getWorkDays();
        List<TeacherTimetableVo> timetables = workDayService.selectTeacherTimetablesByTeacher(teacherIds, xnxq);
        timetables = timetables.stream().filter(timetable -> timetable.getOptionalCourse() == null || !Objects.equals(timetable.getOptionalCourse().getId(), optionalCourse.getId())).collect(Collectors.toList());
        for (TeacherTimetableVo timetable : timetables) {
            WorkDay day = timetable.getWorkDay();
            for (WorkDay workDay : workDays) {
                if (workDay.getDate().compareTo(xnxq.getCourseEndDate()) > 0) {
                    throw new CourseException("course.optional.merge-end-time-xnxq-course-end-time");
                }
                if (merge(workDay, day)) {
                    if (day.getWeekOfTerm() == null) {//基础课程
                        throw new CourseException("course.optional.merge-work-day-base",
                                timetable.getTeacher().getName(),
                                workDay.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                day.getPeriod(),
                                timetable.getBaseCourse().getCourseName());
                    } else if (day.getPeriod() == null) {//课后一小时
                        throw new CourseException("course.optional.merge-work-day-after",
                                timetable.getTeacher().getName(),
                                workDay.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                day.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                day.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                timetable.getBaseCourse().getCourseName(),
                                timetable.getOptionalCourse().getAliasName());
                    } else {//选修课
                        throw new CourseException("course.optional.merge-work-day-optional",
                                timetable.getTeacher().getName(),
                                workDay.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                day.getPeriod(),
                                timetable.getBaseCourse().getCourseName(),
                                timetable.getOptionalCourse().getAliasName());
                    }
                }
            }
        }
    }

    /**
     * 两个时间是否有冲突
     */
    private boolean merge(WorkDay day1, WorkDay day2) {
        if (day1.getWeekOfTerm() != null && day2.getWeekOfTerm() != null && !day1.getWeekOfTerm().equals(day2.getWeekOfTerm())) {
            return false;
        }
        if (!day1.getDayOfWeek().equals(day2.getDayOfWeek())) {
            return false;
        }
        if (day1.getStartTime().compareTo(day2.getStartTime()) == 0) {
            return true;
        }
        if (day1.getStartTime().compareTo(day2.getStartTime()) > 0) {
            return day1.getStartTime().compareTo(day2.getEndTime()) < 0;
        }
        return day1.getStartTime().compareTo(day2.getStartTime()) < 0 && day1.getEndTime().compareTo(day2.getStartTime()) > 0;
    }


    /******************  分割线，避免冲突**************************/
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private GradeTimetableMapper gradeTimetableMapper;
    @Resource
    private OptionalTimetableMapper optionalTimetableMapper;
    @Resource
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private RedisTemplate redisTemplate; //redis

    private static final long DEFAULT_EXPIRE = 1800;//30分钟 默认过期时间
    private final String AUTO_COURSE_LOCK = "AUTO_COURSE_LOCK"; //setNX值

    @Override
    public Result autoSetElectiveCourse(String campusId, String[] clazzes, Xnxq xnxq) {
        int i = 0;
        RedisConnection conn = null; //redis连接
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            if (conn.setNX(AUTO_COURSE_LOCK.getBytes(), AUTO_COURSE_LOCK.getBytes())) { //返回 true 当 key 的值被设置
                conn.expire(AUTO_COURSE_LOCK.getBytes(), DEFAULT_EXPIRE);   //设置过期时间，避免死锁
            } else { //返回 false 当 key 的值没被设置
                return new Result(false, "同步处理异常，请稍后重试！");
            }
            if (StringUtil.isEmpty(xnxq.getXn()) || StringUtil.isEmpty(xnxq.getXq())) {
                return new Result(false, "当前学年学期未定义。");
            }
            //1.查询是否所有的选修课选课都已经结束
            int count = baseMapper.selectChooseCourseIsEnd(campusId, xnxq, LocalDateTime.now());
            if (count > 0) {
                return new Result(false, "当前学期还有选修课选课时间未结束，不能自动分配选修课。");
            }
            //2.查询所有报名未满的选修课(优先补满课程：即缺口人数少的优先填充)
            List<OptionalCourse> lackOptionalCourse = baseMapper.selectLaskChooseCourse(campusId, xnxq);
            if (null == lackOptionalCourse || lackOptionalCourse.size() <= 0) {
                return new Result(false, "暂无课程可分配！");
            }
            //2.1转换为选修课时间集合
            Map<String, List<WorkDay>> optionalWorkDayMap = transOptionalCourseToMap(lackOptionalCourse);

            //3.查询所有选修课的班级数据范围
            List<OptionalCourse> clazzs = baseMapper.selectChooseCourseClazz(campusId, xnxq);
            //3.1将班级按照选修课ID转换
            Map<String, List<String>> clazzMap = transChooseCourseClazzToMap(clazzs);

            //4.查询所有未选择选修课的学生信息（需要班级数据范围）
            List<Student> studentList = studentMapper.selectLaskStudent(campusId, clazzes, xnxq);
            //4.1根据班级id分类学生集合
            Map<String, List<Student>> studentMap = studentList.stream().collect(Collectors.groupingBy(Student::getClazzId));

            //5.查询所有年级-班级选修课占位信息(学生的选修课必须在占位节次内)
            List<GradeTimetable> gradeTimetableList = gradeTimetableMapper.selectChooseGradeTimetable(campusId, xnxq);
            //5.1转换为班级占位信息
            Map<String, List<String>> clazzTimetableMap = transGradeTimetableListToMap(gradeTimetableList);

            //6.查询所有学生课后一小时选课信息(避免课程时间冲突)（需要班级数据范围）
            List<OptionalTimetable> afterCourseList = optionalTimetableMapper.selectAfterCourse(campusId, clazzes, xnxq);
            //6.1转换课后一小时信息为map
            Map<String, List<WorkDay>> afterWorkDayMap = transAfterListToMap(afterCourseList);

            //7.查询学生选修课历史记录,所有学年学期(不可复选)
            List<StudentCourse> studentHistCourseList = studentCourseMapper.selectHistChooseCourse();
            //7.1转换学生历史选课信息为map
            Map<String, String> studentHistCourseMap = transStudentHistCourseListToMap(studentHistCourseList);

            //记录已经分配课程的学生id
            Map<String, String> stuOK = new HashMap<String, String>();

            int lackCount = 0; //课程未满数量（课程最大人数-已选学生数量）

            //记录学生选课信息
            StudentCourse studentCourse = null;
            List<StudentCourse> studentCourseList = new ArrayList<StudentCourse>();

            //循环报名未满的选修课
            for (OptionalCourse optionalCourse : lackOptionalCourse) {
                lackCount = optionalCourse.getLackCount();
                //根据课程id找到课程的班级数据范围
                List<String> clzs = clazzMap.get(optionalCourse.getId());
                //获取班级范围内的所有学生
                List<Student> students = getStudents(clzs, studentMap);
                //循环学生
                if (null != students && students.size() > 0) {
                    for (Student student : students) {
                        if (lackCount > 0) {
                            //验证学生是否已经选过当前的选修课，yes: continue
                            if (studentHistCourseMap.containsKey(student.getId() + ";" + optionalCourse.getCourseId())) {
                                continue;
                            }
                            //验证当前学生是否已经安排了课程
                            if (stuOK.containsKey(student.getId())) {
                                continue;
                            }
                            //验证学生选择当前选修课时，选修课时间是否满足对应的选修课占位时间
                            if (!validatePlaceholder(student, clazzTimetableMap.get(student.getClazzId()), optionalWorkDayMap.get(optionalCourse.getId()))) {
                                continue;
                            }
                            //验证学生选择当前选修课时，是否选择了其他课程（目前只获取了课后一小时）
                            if (!validateAfter(afterWorkDayMap.get(student.getId()), optionalWorkDayMap.get(optionalCourse.getId()))) {
                                continue;
                            }

                            //验证通过,写值
                            studentCourse = new StudentCourse();
                            studentCourse.setOptionalCourseId(optionalCourse.getId());
                            studentCourse.setStudentId(student.getId());
                            studentCourse.setCourseType("2");
                            studentCourse.setStatus("1");
                            studentCourse.setAutoAllot("1"); //1：表示自动分配
                            studentCourse.setGradeName(student.getGradeName());
                            studentCourseList.add(studentCourse);

                            //写入已分配课程的学生集合
                            stuOK.put(student.getId(), student.getId());

                            lackCount--; //未满数量-1
                        }
                    }
                }
            }

            //数据入库
            for (StudentCourse course : studentCourseList) {
                i += studentCourseMapper.insert(course);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new Result(true, "执行异常！请重新操作");
        } finally {
            if (null != conn) {
                conn.del(AUTO_COURSE_LOCK.getBytes());//删除值
                conn.close(); //释放锁
            }
        }
        return new Result(true, "执行成功！已分配" + i + "人", i);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void delLock() {
        stringRedisTemplate.delete(AUTO_COURSE_LOCK);
    }

    /**
     * 转换选修课时间详情
     *
     * @param list
     * @return
     */
    private Map<String, List<WorkDay>> transOptionalCourseToMap(List<OptionalCourse> list) {
        Map<String, List<WorkDay>> rtn = new HashMap<String, List<WorkDay>>();
        if (null != list) {
            List<WorkDay> tmp = null;
            for (OptionalCourse optionalCourse : list) {
                if (!rtn.containsKey(optionalCourse.getId())) { //不存在key值
                    tmp = new ArrayList<WorkDay>();
                }
                if (StringUtil.isNotEmpty(optionalCourse.getWorkDays())) {
                    tmp.addAll(optionalCourse.getWorkDays());
                }
                rtn.put(optionalCourse.getId(), tmp);
            }
        }
        return rtn;
    }

    /**
     * 转换选修课适用班级信息为Map
     * key = 选修课ID
     * value = list<String> 班级id集合
     *
     * @param list
     */
    private Map<String, List<String>> transChooseCourseClazzToMap(List<OptionalCourse> list) {
        Map<String, List<String>> rtn = new HashMap<String, List<String>>();
        if (null != list) {
            List<String> tmp = null;
            for (OptionalCourse optionalCourse : list) {
                if (!rtn.containsKey(optionalCourse.getId())) { //不存在key值
                    tmp = new ArrayList<String>();
                } else {
                    tmp = rtn.get(optionalCourse.getId());
                }
                if (StringUtil.isNotEmpty(optionalCourse.getClazzes())) {
                    for (Clazz clazz : optionalCourse.getClazzes()) {
                        tmp.add(clazz.getId());
                    }
                }
                rtn.put(optionalCourse.getId(), tmp);
            }
        }
        return rtn;
    }

    /**
     * 找出符合选修课适用班级条件的所有学生
     *
     * @param clzs       选修课适用班级ID
     * @param studentMap 学生信息，按照班级分组存放
     * @return
     */
    private List<Student> getStudents(List<String> clzs, Map<String, List<Student>> studentMap) {
        List<Student> rtn = new ArrayList<Student>();
        if (null != clzs) {
            for (String clz : clzs) {
                List<Student> tmp = studentMap.get(clz);
                if (null != tmp) {
                    rtn.addAll(tmp);
                }
            }
        }
        return rtn;
    }

    /**
     * 转换学生历史选课信息为map
     * key    学生id;课程定义表id
     * value  课程定义表id
     *
     * @return
     */
    private Map<String, String> transStudentHistCourseListToMap(List<StudentCourse> studentHistCourseList) {
        Map<String, String> rtn = new HashMap<String, String>();
        if (null != studentHistCourseList) {
            for (StudentCourse studentCourse : studentHistCourseList) {
                rtn.put(studentCourse.getStudentId() + ";" + studentCourse.getOptionalCourse().getCourseId(), studentCourse.getOptionalCourse().getCourseId());
            }
        }
        return rtn;
    }

    /**
     * 将所有年级的占位信息转换为班级map
     * key    班级id;周次;节次
     * value  班级id;周次;节次
     *
     * @return
     */
    private Map<String, List<String>> transGradeTimetableListToMap(List<GradeTimetable> gradeTimetableList) {
        Map<String, List<String>> rtn = new HashMap<String, List<String>>();
        List<String> tmp = null;
        if (null != gradeTimetableList) {
            for (GradeTimetable gradeTimetable : gradeTimetableList) {
                List<Clazz> clazzs = gradeTimetable.getClazzs();
                if (null != clazzs) {
                    for (Clazz clazz : clazzs) {
                        if (!rtn.containsKey(clazz.getId())) { //不存在key值
                            tmp = new ArrayList<String>();
                        } else {
                            tmp = rtn.get(clazz.getId());
                        }
                        tmp.add(clazz.getId() + ";" + gradeTimetable.getWorkDay().getDayOfWeek() + ";" + gradeTimetable.getWorkDay().getPeriod());
                        rtn.put(clazz.getId(), tmp);
                    }
                }
            }
        }
        return rtn;
    }

    /**
     * 转换课后一小时信息为map
     * key   学生id
     * value 日程信息
     *
     * @param afterCourseList
     * @return
     */
    private Map<String, List<WorkDay>> transAfterListToMap(List<OptionalTimetable> afterCourseList) {
        Map<String, List<WorkDay>> rtn = new HashMap<String, List<WorkDay>>();
        List<WorkDay> tmp = null;
        if (null != afterCourseList) {
            for (OptionalTimetable afterCourse : afterCourseList) {
                WorkDay workDay = afterCourse.getWorkDay();
                List<StudentCourse> studentCourses = afterCourse.getStudentCourses();
                if (null != workDay && null != studentCourses) {
                    for (StudentCourse studentCours : studentCourses) {
                        if (!rtn.containsKey(studentCours.getStudentId())) { //不存在key值
                            tmp = new ArrayList<WorkDay>();
                        } else {
                            tmp = rtn.get(studentCours.getStudentId());
                        }
                        tmp.add(workDay);
                        rtn.put(studentCours.getStudentId(), tmp);
                    }
                }
            }
        }
        return rtn;
    }

    /**
     * 验证学生的选修课占位是否符合选修课时间
     *
     * @param clazzTimetable   该学生所在班级占位数据
     * @param optionalWorkDays 该选修课的上课时间
     * @return
     */
    private boolean validatePlaceholder(Student student, List<String> clazzTimetable, List<WorkDay> optionalWorkDays) {
        if (null != clazzTimetable && clazzTimetable.size() > 0) {
            for (WorkDay workDay : optionalWorkDays) {
                //选修课的上课时间有一条不在学生的班级占位时间中，则不符合上课要求(选修课时间必须全部在占位时间中)
                if (!clazzTimetable.contains(student.getClazzId() + ";" + workDay.getDayOfWeek() + ";" + workDay.getPeriod())) {
                    return false;
                }
            }
        } else { //因为学生的选修课必须在学生占位范围内，当占位为空时，无法选中选修课
            return false;
        }
        return true;
    }

    DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 验证学生的选修课占位是否未选中课后一小时
     *
     * @param afterCourse      该学生课后一小时的上课时间数据
     * @param optionalWorkDays 该选修课的上课时间
     * @return
     */
    private boolean validateAfter(List<WorkDay> afterCourse, List<WorkDay> optionalWorkDays) {
        if (null != afterCourse) {
            for (WorkDay workDay : afterCourse) {
                for (WorkDay oWorkDay : optionalWorkDays) {
                    //选修课的上课时间存在一条数据在课后一小时的上课时间中，则不符合上课要求(选修课时间不能和课后一小时时间重合)
                    if (workDay.getWeekOfTerm().equals(oWorkDay.getWeekOfTerm())) {
                        //比较时间（选修课开始时间 <= 课后一小时的开始时间  <= 选修课截止时间）
                        if (isCompare(workDay.getStartTime(), workDay.getEndTime(), oWorkDay.getStartTime(), oWorkDay.getEndTime())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 验证： 时间不冲突比较
     * 课后一小时的截止时间  < 选修课开始时间
     * 课后一小时的开始时间  > 选修课截止时间
     *
     * @param afterStartTime    课后一小时的开始时间
     * @param afterEndTime      课后一小时的截止时间
     * @param optionalStartTime 选修课开始时间
     * @param optionalEndTime   选修课截止时间
     * @return true:时间冲突； false:时间不冲突
     */
    private boolean isCompare(LocalTime afterStartTime, LocalTime afterEndTime, LocalTime optionalStartTime, LocalTime optionalEndTime) {
        if (afterEndTime.isBefore(optionalStartTime) || afterStartTime.isAfter(optionalEndTime)) {
            return false;
        }
        return true;
    }
}
