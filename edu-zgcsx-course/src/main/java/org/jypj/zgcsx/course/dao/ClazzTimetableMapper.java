package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.ClazzTimetable;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班级课程表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface ClazzTimetableMapper extends BaseMapper<ClazzTimetable> {
    /**
     * 查询教这个班级课同一时间点的教师名称
     *
     * @param clazzId
     * @param workDayId
     * @return
     */
    String selectTeacherName(@Param("clazzId") String clazzId, @Param("workDayId") String workDayId);


    /**
     * 查询详细的列表数据
     *
     * @param queryMap
     * @return
     */
    List<ClazzTimetable> selectListDetail(Map<String, String> queryMap);

    /**
     * 查询必修课冲突的教师名称并且不允许保存
     *
     * @param clazzTimetable
     * @return
     */
    String selectRequiredCourseConflictUnSaveTeacherName(@Param("clazzTimetable") ClazzTimetable clazzTimetable);

    /**
     * 查询必修课冲突的无教师名称并且不允许保存
     *
     * @param clazzTimetable
     * @return
     */
    String selectRequiredCourseConflictUnSaveCourseName(@Param("clazzTimetable") ClazzTimetable clazzTimetable);

    /**
     * 查询必修课冲突的教师名称并且允许保存
     *
     * @param clazzTimetable
     * @return
     */
    String selectRequiredCourseConflictSaveTeacherName(@Param("clazzTimetable") ClazzTimetable clazzTimetable);


    /**
     * 查询选修课占位冲突的年级名称并且不允许保存
     *
     * @param clazzTimetable
     * @return
     */
    String selectSelectiveCoursePlaceholderConflictUnSaveGradeName(@Param("clazzTimetable") ClazzTimetable clazzTimetable);

    /**
     * 查询选修课冲突的课程名称并且不允许保存
     *
     * @param clazzTimetable
     * @return
     */
    String selectSelectiveCourseConflictUnSaveCourseName(@Param("clazzTimetable") ClazzTimetable clazzTimetable);

    /**
     * 查询有冲突的教师
     *
     * @param workDayIds 日程IDs 逗号分隔
     * @param gradeId    级部ID
     * @return
     */
    String selectConflictTeacherName(@Param("workDayIds") String workDayIds, @Param("gradeId") String gradeId);


    /**
     * 删除冲突教师的课程
     *
     * @param workDayIds
     * @param gradeId
     * @return
     */
    int deleteConflictTeacherCourse(@Param("workDayIds") String workDayIds, @Param("gradeId") String gradeId);

    /**
     * 删除之前保存的教师课程
     *
     * @param xnxq
     * @param teacherId
     * @return
     */
    int deleteBeforeSaveTeacherCourse(@Param("xnxq") Xnxq xnxq, @Param("teacherId") String teacherId);


    /******************************* 分割线 避免冲突***************************************************/

    /**
     * 基础课程【课程管理】【我的课表】
     * <p>
     * 查询单个教师
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> selectCourseTimetable(@Param("clazzTimetable") ClazzTimetable clazzTimetable, @Param("xnxq") Xnxq xnxq);

    /**
     * 基础课程【课程管理】【我的课表】
     * <p>
     * 查询多个教师
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> selectCourseTimetableTeachers(@Param("clazzTimetable") ClazzTimetable clazzTimetable, @Param("xnxq") Xnxq xnxq);

    /**
     * 基础课程【课程管理】【我的工作】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     * @throws IOException
     */
    List<ClazzTimetable> selectBasicWork(@Param("clazzTimetable") ClazzTimetable clazzTimetable, @Param("xnxq") Xnxq xnxq);

    /**
     * 【课程管理】【公共课表】/【课表信息查询】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     */
    List<ClazzTimetable> selectPublicTimetable(@Param("clazzTimetable") ClazzTimetable clazzTimetable, @Param("xnxq") Xnxq xnxq);

    /**
     * 【课程管理】【教师任课列表】
     *
     * @param clazzTimetable
     * @param xnxq
     * @return
     */
    List<ClazzTimetable> selectTeacherTeach(Pagination page, @Param("clazzTimetable") ClazzTimetable clazzTimetable, @Param("xnxq") Xnxq xnxq, @Param("clazzes") String[] clazzes);

    /**
     * 根据教师id获取教师 默认 校区，年级， 班级
     *
     * @param clazzTimetable
     * @return
     */
    List<ClazzTimetable> selectTeacherOwn(@Param("clazzTimetable") ClazzTimetable clazzTimetable, @Param("xnxq") Xnxq xnxq);

    /**
     * 根据班级id何日程id查询课程信息
     * <p>
     * 查询单个教师
     *
     * @param clazzTimetable
     * @return
     */
    ClazzTimetable selectTimetableOwn(ClazzTimetable clazzTimetable);

    /**
     * 根据班级id何日程id查询课程信息
     * <p>
     * 查询多个教师
     *
     * @param clazzTimetable
     * @return
     */
    ClazzTimetable selectTimetableTeacherOwn(ClazzTimetable clazzTimetable);


    /**
     * 查询ClazzTimetable
     * 当教师条件为空则查询条件为 is null
     *
     * @param clazzTimetable
     * @return
     */
    ClazzTimetable selectTimetable(ClazzTimetable clazzTimetable);
}
