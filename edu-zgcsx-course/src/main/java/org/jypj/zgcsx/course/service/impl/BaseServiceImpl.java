package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.jypj.zgcsx.course.config.CourseProperties;
import org.jypj.zgcsx.course.config.mybatis.BaseMapper;
import org.jypj.zgcsx.course.entity.WorkDay;
import org.jypj.zgcsx.course.error.CourseException;
import org.jypj.zgcsx.course.service.BaseService;
import org.jypj.zgcsx.course.service.WorkDayService;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author qi_ma
 * @version 1.0 2017/11/21 19:47
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    @Resource
    WorkDayService workDayService;
    @Resource
    CourseProperties properties;

    @Override
    public WorkDay saveOrSelectWorkDay(String workDayId, Integer weekOfTerm) {
        WorkDay workDay = workDayService.selectById(workDayId);
        if (workDay == null) {
            throw new CourseException("course.data-error");
        }
        if (workDay.getWeekOfTerm() != null && workDay.getWeekOfTerm().compareTo(weekOfTerm) == 0) {
            return workDay;
        }
        workDay = workDayService.selectDetailByNonIdAndWeekOfTerm(workDayId, weekOfTerm);
        if (workDay != null) {
            return workDay;
        }
        workDay = workDayService.selectById(workDayId);
        if (workDay == null) {
            throw new CourseException("course.data-error");
        }
        workDay.setWeekOfTerm(weekOfTerm);
        workDay.clearBaseEntity();
        workDayService.insert(workDay);
        return workDay;
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectList(new EntityWrapper<>());
    }

    @Override
    public List<T> selectAll(T t) {
        Wrapper<T> wrapper = new EntityWrapper<>();
        Class<T> entity = (Class<T>) t.getClass();
        Field[] fields = entity.getDeclaredFields();
        for (Field f : fields) {
            TableField tableField = f.getAnnotation(TableField.class);
            if (tableField != null && tableField.exist()) {
                Object value = null;
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(f.getName(), entity);
                    Method method = pd.getReadMethod();
                    value = method.invoke(t);
                } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (value == null) {
                    continue;
                }
                String column = tableField.value();
                wrapper.eq(column, value);
            }
        }
        return baseMapper.selectList(wrapper);
    }
}
