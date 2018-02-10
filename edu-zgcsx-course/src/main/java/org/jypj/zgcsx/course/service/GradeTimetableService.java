package org.jypj.zgcsx.course.service;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.entity.GradeTimetable;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 年级选修课时间表 服务类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface GradeTimetableService extends BaseService<GradeTimetable> {


    /**
     * 按年级查询所有的选修课占位
     *
     * @param campusId 校区ID
     * @param gradeId  年级ID
     * @param xnxq     学年学期
     * @return
     */
    List<GradeTimetable> selectAllByMap(@Param("campusId") String campusId, @Param("gradeId") String gradeId, @Param("xnxq") Xnxq xnxq);


    /**
     * 保存所有的占位
     *
     * @param workDayIdStr
     * @param gradeId
     * @return
     */
    boolean saveAllGradeTimetables(String workDayIdStr, String gradeId, Xnxq xnxq);
}
