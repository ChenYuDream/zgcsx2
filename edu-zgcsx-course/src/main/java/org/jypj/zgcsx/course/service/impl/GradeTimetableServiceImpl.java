package org.jypj.zgcsx.course.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jypj.zgcsx.course.dao.ClazzTimetableMapper;
import org.jypj.zgcsx.course.dao.GradeTimetableMapper;
import org.jypj.zgcsx.course.entity.GradeTimetable;
import org.jypj.zgcsx.course.entity.Xnxq;
import org.jypj.zgcsx.course.service.GradeTimetableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 年级选修课时间表 服务实现类
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Service
public class GradeTimetableServiceImpl extends BaseServiceImpl<GradeTimetableMapper, GradeTimetable> implements GradeTimetableService {

    @Resource
    private GradeTimetableService gradeTimetableService;

    @Resource
    private GradeTimetableMapper gradeTimetableMapper;

    @Override
    public List<GradeTimetable> selectAllByMap(String campusId, String gradeId, Xnxq xnxq) {

        return baseMapper.selectChooseGradeTimetableSingleGrade(campusId, gradeId, xnxq);
    }

    @Override
    public boolean saveAllGradeTimetables(String workDayIdStr, String gradeId, Xnxq xnxq) {
        try {
            //进行保存
            List<String> workIdList = Arrays.asList(workDayIdStr.replace("'", "").split(","));
            List<GradeTimetable> gradeTimetables = new ArrayList<>();
            workIdList.forEach(s -> {
                GradeTimetable gradeTimetable = new GradeTimetable(s, gradeId);
                gradeTimetables.add(gradeTimetable);
            });
            //删除之前保存的年级日程
            gradeTimetableMapper.deleteBeforeSelectivePlaceHolder(gradeId, xnxq);
            //在保存现在的
            gradeTimetableService.insertBatch(gradeTimetables);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return true;

    }
}
