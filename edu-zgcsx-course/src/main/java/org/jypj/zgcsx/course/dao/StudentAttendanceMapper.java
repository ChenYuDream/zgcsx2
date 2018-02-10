package org.jypj.zgcsx.course.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.jypj.zgcsx.course.entity.StudentAttendance;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.Xnxq;

import java.util.List;

/**
 * <p>
 * 考勤表 Mapper 接口
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
public interface StudentAttendanceMapper extends BaseMapper<StudentAttendance> {

    /**
     * 获取基础缺勤记录
     * @param page
     * @param studentAttendance
     * @param xnxq
     * @return
     */
    List<StudentAttendance> selectBasicStudentAttendance(Pagination page, @Param("studentAttendance") StudentAttendance studentAttendance, @Param("xnxq") Xnxq xnxq);

    /**
     * 获取选修缺勤记录
     * @param page
     * @param studentAttendance
     * @param xnxq
     * @return
     */
    List<StudentAttendance> selectChooseStudentAttendance(Pagination page, @Param("studentAttendance") StudentAttendance studentAttendance, @Param("xnxq") Xnxq xnxq);
}
