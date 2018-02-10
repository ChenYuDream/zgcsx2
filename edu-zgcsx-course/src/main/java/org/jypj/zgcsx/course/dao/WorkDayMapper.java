package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.vo.ClazzTimetableVo;
import org.jypj.zgcsx.course.vo.SpaceTimetableVo;
import org.jypj.zgcsx.course.vo.TeacherTimetableVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日程表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface WorkDayMapper extends BaseMapper<WorkDay> {

    /**
     * 根据可循环的workDayId和周次查询详细的WorkDay
     *
     * @param workDayId
     * @param weekOfTerm
     * @return
     */
    WorkDay selectDetailByNonIdAndWeekOfTerm(@Param("workDayId") String workDayId, @Param("weekOfTerm") Integer weekOfTerm);

    /**
     * 根据选修课ID查询时间表
     *
     * @param id
     * @return
     */
    List<WorkDay> selectWorkDaysByOptionalCourseId(String id);

    /**
     * 根据教师ID，学年学期查询教师所有上课时间表
     *
     * @param teacherIds 教师ID集合
     * @param xnxq       学年学期
     * @return
     */
    List<TeacherTimetableVo> selectTeacherTimetablesByTeacher(@Param("teacherIds") List<String> teacherIds, @Param("xnxq") Xnxq xnxq);

    /**
     * 根据空间ID，学年学期查询空间所有上课时间表
     *
     * @param spaceId 空间ID
     * @param xnxq    学年学期
     * @return
     */
    List<SpaceTimetableVo> selectSpaceTimetablesBySpaceId(@Param("id") String spaceId, @Param("xnxq") Xnxq xnxq);

    /**
     * 根据条件查询
     *
     * @param workDay
     * @return
     */
    List<WorkDay> selectDetailByWorkDay(WorkDay workDay);

    /**
     * 新增
     *
     * @param workDay
     * @return
     */
    int insertWorkDay(WorkDay workDay);

    /**
     * 根据空间ID和学年学期查询所有选修课时间
     *
     * @param spaceId
     * @param xnxq
     * @return
     */
    List<WorkDay> selectBySpaceId(@Param("spaceId") String spaceId, @Param("xnxq") Xnxq xnxq);

    int insertAfter(WorkDay workDay);

    int insertOptional(WorkDay workDay);

    List<WorkDay> selectAfter(WorkDay workDay);

    List<WorkDay> selectOptional(WorkDay workDay);


    /**
     * 查询教师当前日程开始时间-结束时间内是否存在开放或拓展课程
     *
     * @return
     */
    int selectCount(WorkDay workDay);

    /**
     * 根据id查询不带周次的记录
     *
     * @param id
     * @return
     */
    WorkDay selectNotWeekById(String id);

    /**
     * 查询所有班级课程表
     */
    List<ClazzTimetableVo> selectAllClazzTimetable(Map<String, Object> condition);
}
