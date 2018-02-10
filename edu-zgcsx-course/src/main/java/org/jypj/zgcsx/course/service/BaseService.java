package org.jypj.zgcsx.course.service;

import com.baomidou.mybatisplus.service.IService;
import org.jypj.zgcsx.course.entity.WorkDay;

import java.util.List;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 19:46
 */
public interface BaseService<T> extends IService<T> {
    WorkDay saveOrSelectWorkDay(String workDayId, Integer weekOfTerm);

    List<T> selectAll();

    List<T> selectAll(T t);
}
