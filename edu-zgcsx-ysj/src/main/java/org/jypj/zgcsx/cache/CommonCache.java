package org.jypj.zgcsx.cache;

import org.apache.log4j.Logger;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.dao.CodeDataDao;
import org.jypj.zgcsx.dao.CommonTreeDao;
import org.jypj.zgcsx.dto.DtoSpace;
import org.jypj.zgcsx.dto.DtoTree;
import org.jypj.zgcsx.entity.CodeData;
import org.jypj.zgcsx.entity.CodeDataChild;
import org.jypj.zgcsx.entity.CommonTree;
import org.jypj.zgcsx.enums.SchoolEnum;
import org.jypj.zgcsx.service.CommonTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian_wu on 2017/11/10.
 */
@Component
public class CommonCache {

    private Logger log = Logger.getLogger(CommonCache.class);

    private static Map<String,Object> codeMap; //字典缓存(map结构)

    private static List<CodeData> codeDatalist;  //字典(list结构)

    private static List<DtoTree> spaceTree;  //地点缓存(树结构)

    private static List<CommonTree> schoolAllTree; //学校树结构缓存(学校-校区-年级-班级)

    private static List<CommonTree> schoolInfoTree; //学校树结构

    private static List<CommonTree> campusInfoTree; //校区树结构

    private static List<CommonTree> gradeInfoTree; //年级树结构

    private static List<CommonTree> classInfoTree; //年级树结构

    @Autowired
    public CodeDataDao codeDataDao;

    @Autowired
    public DtoSpace dtoSpace;

    @Autowired
    public CommonTreeService commonTreeService;

    @Autowired
    public CommonTreeDao commonTreeDao;

    /**
     * 服务器加载的时候运行，初始化缓存
     */
    @PostConstruct
    public void init(){
        try {
            codeMap = new HashMap<>();
            List<CodeData> list = codeDataDao.selectAll();
            codeDatalist = list;
            //将list集合转化成以code_data为key的map
            list.stream().forEach(n->{
                codeMap.put(n.getCode(),n.getCodeDataChildren()==null?new Object():n.getCodeDataChildren());
            });
            log.info("字典缓存写入成功");

            //2.地点
            spaceTree = dtoSpace.tranfer();
            log.info("地点树结构缓存写入成功");

            //3.学校树结构（学校>校区>年级>班级）
            schoolAllTree = commonTreeService.getSchoolTree();
            log.info("学校树结构缓存写入成功");

            //4.缓存学校、校区、年级、班级单独的树结构，在学生根据树nodeId查询时，可以判断是要查哪个节点
            schoolInfoTree = commonTreeDao.getSchoolInfoTree();
            campusInfoTree = commonTreeDao.getCampusAllInfoTree();
            gradeInfoTree = commonTreeDao.getGradeAllInfoTree();
            classInfoTree = commonTreeDao.getClassAllInfoTree();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取字典值
     * @param parentCode
     * @param childCode
     * @return
     */
    public static String getCodeName(String parentCode,String childCode){
        if(StringUtil.isEmpty(parentCode)){
            return null;
        }
        if(StringUtil.isEmpty(childCode)){
            return null;
        }
        for (CodeData n:codeDatalist) {
            if (n.getCode().equals(parentCode)) {
                List<CodeDataChild> list = n.getCodeDataChildren();
                for (CodeDataChild codeDataChild : list) {
                    if (codeDataChild.getValue().equals(childCode)) {
                        return codeDataChild.getText();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 查学校树结构，获得所属的级别
     * @param codeId
     * @return
     */
    public static String getTreeCodeBelong(String codeId){
        String code = SchoolEnum.SCHOOL_NODE.getCode();
        for(CommonTree commonTree:schoolInfoTree){
            if(commonTree.getId().equals(codeId)){
                return SchoolEnum.SCHOOL_NODE.getCode();
            }
        }
        for(CommonTree commonTree:campusInfoTree){
            if(commonTree.getId().equals(codeId)){
                return SchoolEnum.CAMPUS_NODE.getCode();
            }
        }
        for(CommonTree commonTree:gradeInfoTree){
            if(commonTree.getId().equals(codeId)){
                return SchoolEnum.GRADE_NODE.getCode();
            }
        }
        for(CommonTree commonTree:classInfoTree){
            if(commonTree.getId().equals(codeId)){
                return SchoolEnum.CLASS_NODE.getCode();
            }
        }
        return code;
    }

    public static Map<String, Object> getCodeMap() {
        return codeMap;
    }

    public static List<CodeData> getCodeDatalist() {
        return codeDatalist;
    }

    public static List<DtoTree> getSpaceTree() {
        return spaceTree;
    }

    public static List<CommonTree> getSchoolAllTree() {
        return schoolAllTree;
    }

    public static List<CommonTree> getSchoolInfoTree() {
        return schoolInfoTree;
    }

    public static List<CommonTree> getCampusInfoTree() {
        return campusInfoTree;
    }

    public static List<CommonTree> getGradeInfoTree() {
        return gradeInfoTree;
    }

    public static List<CommonTree> getClassInfoTree() {
        return classInfoTree;
    }
}
