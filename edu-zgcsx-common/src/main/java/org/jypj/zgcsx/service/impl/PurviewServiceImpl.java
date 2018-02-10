package org.jypj.zgcsx.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.dao.ClazzDao;
import org.jypj.zgcsx.dao.PurviewDao;
import org.jypj.zgcsx.entity.Clazz;
import org.jypj.zgcsx.entity.Purview;
import org.jypj.zgcsx.service.PurviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by jian_wu on 2017/11/28.
 */
@Service
public class PurviewServiceImpl implements PurviewService{

    @Autowired
    private PurviewDao purviewDao;

    @Autowired
    private ClazzDao clazzDao;

    @Override
    public Result getUserData(String loginName, String roleId) {
        Map<String,Object> resultMap = new HashMap<>(16);
        if(StringUtil.isEmpty(loginName)||StringUtil.isEmpty(roleId)){
            return new Result(Result.FAIL,"参数错误");
        }
        //1.查总记录数
        Map<String,Object> queryMap = new HashMap<>(16);
        queryMap.put("loginName",loginName);
        queryMap.put("roleId",roleId);
        List<Purview> list = purviewDao.getListByLoginNameRoleId(queryMap);
        //2.根据SORT分别查数据范围
        //  01-学校ID,02-校区ID,03-年级ID,04-校中校ID,05-班组群ID,06-班级ID
        List<String> classes = new ArrayList<>();
        for(Purview p:list){
            //3.有学校，则表示返回全部数据标识
            if(p.getSort().equals("01")){
                resultMap.put("isAll","0");
                resultMap.put("classes",null);
                return new Result(resultMap);
            }
            // 02-校区ID
            if(p.getSort().equals("02")){
                List<Clazz> clazzList = clazzDao.selectList(new EntityWrapper<Clazz>().eq("XQID",p.getOrgId()).eq("VALID","1"));
                clazzList.forEach(n->classes.add(n.getId()));
            }
            // 03-年级ID
            if(p.getSort().equals("03")){
                List<Clazz> clazzList = clazzDao.selectList(new EntityWrapper<Clazz>().eq("JBID",p.getOrgId()).eq("VALID","1"));
                clazzList.forEach(n->{
                    //排除校中校和班组群
                    if(n.getBzqid()==null||n.getXzxid()==null){
                        classes.add(n.getId());
                    }
                });
            }
            // 04-校中校ID
            if(p.getSort().equals("04")){
                List<Clazz> clazzList = clazzDao.selectList(new EntityWrapper<Clazz>().eq("XZXID",p.getOrgId()).eq("VALID","1"));
                clazzList.forEach(n->classes.add(n.getId()));
            }
            // 05-班组群ID
            if(p.getSort().equals("05")){
                List<Clazz> clazzList = clazzDao.selectList(new EntityWrapper<Clazz>().eq("BZQID",p.getOrgId()).eq("VALID","1"));
                clazzList.forEach(n->classes.add(n.getId()));
            }
            // 06-班级id
            if(p.getSort().equals("06")){
                classes.add(p.getOrgId());
            }
        }
        //4.去重复的班级id
        HashSet<String> uniqueClass = new HashSet<>(classes);
        resultMap.put("classes",uniqueClass);
        resultMap.put("isAll","-1");
        return new Result(resultMap);
    }
}
