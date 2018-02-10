package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.CampusTimetable;

import java.util.List;

/**
 * 校区时间表服务类
 *
 * @author qi_ma
 * @create 2017-12-15 11:08
 **/
public interface CampusTimetableService extends BaseService<CampusTimetable> {
    List<CampusTimetable> selectByCampusId(String campusId);
}
