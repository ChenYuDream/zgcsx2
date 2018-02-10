package org.jypj.zgcsx.course.service;

import org.jypj.zgcsx.course.entity.SchoolXq;

import java.util.List;

/**
 * 校区
 *
 * @author
 * @create 2017-11-22 13:51
 **/
public interface SchoolXqService extends BaseService<SchoolXq> {
    List<SchoolXq> selectAllCampuses();
}
