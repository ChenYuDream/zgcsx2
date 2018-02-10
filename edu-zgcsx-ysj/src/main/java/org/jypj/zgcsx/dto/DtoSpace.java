package org.jypj.zgcsx.dto;

import org.jypj.zgcsx.cache.CommonCache;
import org.jypj.zgcsx.dao.CodeDataDao;
import org.jypj.zgcsx.dao.SpaceDao;
import org.jypj.zgcsx.entity.CodeData;
import org.jypj.zgcsx.entity.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jian_wu on 2017/11/13.
 */
@Component
public class DtoSpace {

    //cims_campus 校区  CAMPUS
    //cims_arch 建筑楼 ARCHITECTURE
    //cims_sptype 类型 SPTYPE
    //cims_floor  楼层  FLOORID

    //四级结构  校区--》教学楼--》(类型 没有使用）》楼层    --》场馆

    @Autowired
    private CodeDataDao codeDataDao;

    @Autowired
    private SpaceDao spaceDao;

    //写入缓存了
    public  List<DtoTree> tranfer(){
        List<CodeData> listCode = codeDataDao.selectAll();
        List<Space> spaces = spaceDao.queryAllSpace(new HashMap<>());
        //1.查询所有的space有哪些校区
        List<String> campus = new ArrayList<>();
        spaces.stream().forEach(n->{
            campus.add(n.getCampus());
        });
        List<String> campusDist = campus.stream().distinct().collect(Collectors.toList());

        //2.查询所有的space有哪些建筑楼
        List<String> archis = new ArrayList<>();
        spaces.stream().forEach(n->{
            archis.add(n.getArchitecture());
        });
        List<String> archisDist = archis.stream().distinct().collect(Collectors.toList());

        //3.查询所有的space有哪些类型
        List<String> spaceType = new ArrayList<>();
        spaces.stream().forEach(n->{
            spaceType.add(n.getSptype());
        });
        List<String> spaceTypeDist = spaceType.stream().distinct().collect(Collectors.toList());

        //4.查询所有的space有哪些楼层
        List<String> floor = new ArrayList<>();
        spaces.stream().forEach(n->{
            floor.add(n.getFloorid());
        });
        List<String> floorDist = floor.stream().distinct().collect(Collectors.toList());
        //4.1 给楼层进行排序，按照地上高层到地下的顺序排序，比如F3,F2,F1,B1,B2的顺序
        //在try  catch中执行，如果出现错误，则还原数据
        List<String> floorDistClone = new ArrayList<>();
        try {
            //地上的楼层
            List<String> upFloor = floorDist.stream().filter(n->n.substring(0,1).equals("F")).sorted((s1, s2) -> {return s2.compareTo(s1);}).collect(Collectors.toList());
            //地下
            List<String> downFloor = floorDist.stream().filter(n->n.substring(0,1).equals("B")).sorted().collect(Collectors.toList());
            floorDistClone.addAll(upFloor);
            floorDistClone.addAll(downFloor);
            if(floorDistClone.size() == floorDist.size()){
                floorDist = floorDistClone;
            }
        }catch (Exception e){
            System.out.println("楼层排序异常");
            floorDist = floor.stream().distinct().collect(Collectors.toList());
        }

        //5.循环对比space数据，处理数据，处理量貌似有点大
        Map<String,Object> resultMap = new HashMap<>();
        List<DtoTree> dtoTrees = new ArrayList<>();
        for(String campusStr:campusDist){
            DtoTree dtoTree = new DtoTree();
            dtoTree.setCode(campusStr);
            dtoTree.setName(CommonCache.getCodeName("cims_campus",campusStr));
            List<DtoTree> dtoTreesArc = new ArrayList<>();
            for(String archisStr:archisDist){
                DtoTree dtoTreeArc = new DtoTree();
                dtoTreeArc.setCode(archisStr);
                dtoTreeArc.setName(CommonCache.getCodeName("cims_arch",archisStr));
                List<DtoTree> dtoTreesfloor = new ArrayList<>();
                for(String floorStr:floorDist){  //楼层
                    DtoTree dtoTreeFloor = new DtoTree();
                    dtoTreeFloor.setCode(floorStr);
                    dtoTreeFloor.setName(CommonCache.getCodeName("cims_floor",floorStr));
                    List<DtoTree> dtoTreesSpace = new ArrayList<>();
                    spaces.stream().forEach(n->{
                        if(campusStr.equals(n.getCampus())&&archisStr.equals(n.getArchitecture())&&floorStr.equals(n.getFloorid())){
                            DtoTree dtoTreeSpace = new DtoTree();
                            dtoTreeSpace.setName(n.getMc());
                            dtoTreeSpace.setCode(n.getId());
                            dtoTreesSpace.add(dtoTreeSpace);
                        }
                    });
                    if(dtoTreesSpace.size()>0){
                        dtoTreeFloor.setSubTree(dtoTreesSpace);
                        dtoTreesfloor.add(dtoTreeFloor);
                    }
                }
                if(dtoTreesfloor.size()>0){
                    dtoTreeArc.setSubTree(dtoTreesfloor);
                    dtoTreesArc.add(dtoTreeArc);
                }
            }
            if(dtoTreesArc.size()>0){
                dtoTree.setSubTree(dtoTreesArc);
                dtoTrees.add(dtoTree);
            }
        }
        return dtoTrees;
    }


}
