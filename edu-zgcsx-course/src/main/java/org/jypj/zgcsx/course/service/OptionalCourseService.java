package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.entity.OptionalCourse;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 选修课详情表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface OptionalCourseService extends BaseService<OptionalCourse> {
    /**
     * 分页查询选修课信息
     *
     * @param page
     * @return
     */
    Page<OptionalCourse> selectAll(Page<OptionalCourse> page);

    /**
     * 查询所有选修课信息
     *
     * @param condition
     * @return
     */
    List<OptionalCourse> selectAll(Map<String, Object> condition);

    /**
     * 保存选课信息
     */
    boolean save(OptionalCourse optionalCourse, Xnxq xnxq, UserInfo userInfo);


    /**
     * 中关村三小选修课，未选课学生自动分配选修课信息
     * @author zhoushengli
     *<BR>
     *<h1>南校区</h1><BR>
     *1. 学生从三年级开始，每学期开学都要对选修课进行选课，每学期只选择1门选修课，本学期选过的选修课，学生在下学期不可再选。也就是说三年级开始，往后的8个学期每个学期学生选择的选修课不可重复。<BR>
     *2. 选课开始，为学生账号开始选课功能，学生开始远课，选课结束前，学生可以对已选的课程进行更改；选课结束后，关闭学行选课功能。<BR>
     *3. 每位学生每学期必须选择一门选修课，如果在选课期间，学生未选择任何一门选修课，则在选课结束后，由系统随机为该学生分配一门选修课（随机分配课程机制：从所有课程中选择未达到满员的课程进行随机分配）。<BR>
     *4. 南校区每个年级有相对应的选修课，例如：三年级学生只选择三年级所对应的选修课；四年级学生只选择四年级所对应的选修课；六年级学生只选择六年级所对应的选修课。不可跨年级选课。(根据班级数据范围)<BR>
     *<BR>
     *<h1>北校区</h1><BR>
     *1. 学生从三年级开始，每学期开学都要对选修课进行选课，每学期只选择1门选修课，本学期选过的选修课，学生在下学期不可再选。也就是说三年级开始，往后的8个学期每个学期学生选择的选修课不可重复。<BR>
     *2. 选课开始，为学生账号开始选课功能，学生开始远课，选课结束前，学生可以对已选的课程进行更改；选课结束后，关闭学行选课功能。<BR>
     *3. 每位学生每学期必须选择一门选修课，如果在选课期间，学生未选择任何一门选修课，则在选课结束后，由系统随机为该学生分配一门选修课（随机分配课程机制：从所有课程中选择未达到满员的课程进行随机分配）。<BR>
     *4. 北校区不同于南校区，每个年级没有相对应的选修课，三年级、四年级、五年级、六年级跨年级混合选课，每门课程都有所对应的选课年级，例如：“视觉艺术类”只能五年级和六年级的学生进行选课。(根据班级数据范围)。
     *
     */
    /**
     * 选修课自动分配
     *
     * @param campusId 校区id
     * @param clazzes  角色班级数据范围
     * @param xnxq
     * @return
     */
    Result autoSetElectiveCourse(String campusId, String[] clazzes, Xnxq xnxq);

    /**
     * 手动释放自动分配锁
     *
     * @return
     */
    void delLock();
}
