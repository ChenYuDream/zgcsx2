package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.vo.ClazzTimetableVo;
import org.jypj.zgcsx.course.vo.SpaceTimetableVo;
import org.jypj.zgcsx.course.vo.TeacherTimetableVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日程表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface WorkDayService extends BaseService<WorkDay> {
    /**
     * 根据教师ID，学年学期查询教师所有上课时间表
     *
     * @param teacherIds 教师ID集合
     * @param xnxq       学年学期
     * @return
     */
    List<TeacherTimetableVo> selectTeacherTimetablesByTeacher(List<String> teacherIds, Xnxq xnxq);

    /**
     * 根据空间ID，学年学期查询空间所有上课时间表
     *
     * @param spaceId 空间ID
     * @param xnxq    学年学期
     * @return
     */
    List<SpaceTimetableVo> selectSpaceTimetablesBySpaceId(String spaceId, Xnxq xnxq);

    /**
     * 根据条件查询
     *
     * @param workDay
     * @return
     */
    List<WorkDay> selectDetailByWorkDay(WorkDay workDay);

    /**
     * 批量新增
     *
     * @param insertList
     * @return
     */
    int insertWorkDays(List<WorkDay> insertList);

    /**
     * 保存选修课的课程时间信息
     *
     * @param workDays
     * @param xnxq
     * @param campusId
     * @param type
     * @return
     */
    List<WorkDay> insertWorkDays(List<WorkDay> workDays, Xnxq xnxq, String campusId, String type);

    /**
     * 根据空间ID和学年学期查询所有选修课时间
     *
     * @param spaceId
     * @param xnxq
     * @return
     */
    List<WorkDay> selectBySpaceId(String spaceId, Xnxq xnxq);

    /**
     * 根据学年学期和日期计算第几周
     *
     * @param xnxq 学年学期
     * @param date 日期
     * @return 第几周
     */
    Integer getWeekOfTerm(Xnxq xnxq, LocalDate date);

    /**
     * 根据学年学期和第几周以及周几计算日期
     *
     * @param xnxq      学年学期
     * @param dayOfWeek 日期
     * @return 日期
     */
    LocalDate getDate(Xnxq xnxq, int weekOfTerm, int dayOfWeek);

    WorkDay selectDetailByNonIdAndWeekOfTerm(String workDayId, Integer weekOfTerm);


    /**
     * 查询当前学年学期的日程列表
     * @param xnxq 学年学期
     * @return
     */
    List<WorkDay> selectCurrentXnxqWorkDayList(Xnxq xnxq);

    /**
     * 查询所有班级课程表
     */
    List<ClazzTimetableVo> selectAllClazzTimetable(Map<String, Object> condition);
}
