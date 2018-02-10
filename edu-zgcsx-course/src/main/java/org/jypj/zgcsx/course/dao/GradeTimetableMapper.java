package org.jypj.zgcsx.course.dao;

import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.entity.GradeTimetable;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;

/**
 * <p>
 * 年级选修课时间表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface GradeTimetableMapper extends BaseMapper<GradeTimetable> {

    /**
     * 删除之前保存的选修课占位
     *
     * @param gradeId
     * @param xnxq
     * @return
     */
    int deleteBeforeSelectivePlaceHolder(@Param("gradeId") String gradeId, @Param("xnxq") Xnxq xnxq);

    /**
     * 查询所有年级选修课占位信息
     *
     * @param campusId
     * @param xnxq
     * @return
     */
    List<GradeTimetable> selectChooseGradeTimetable(@Param("campusId") String campusId, @Param("xnxq") Xnxq xnxq);


    /**
     * 查询所有年级选修课占位信息
     *
     * @param campusId 校区ID
     * @param gradeId  年级ID
     * @param xnxq     学年学期
     * @return
     */
    List<GradeTimetable> selectChooseGradeTimetableSingleGrade(@Param("campusId") String campusId, @Param("gradeId") String gradeId, @Param("xnxq") Xnxq xnxq);

}
