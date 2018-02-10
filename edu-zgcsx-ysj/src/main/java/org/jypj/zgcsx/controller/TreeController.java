package org.jypj.zgcsx.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.cache.CommonCache;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.dto.DtoContactTree;
import org.jypj.zgcsx.dto.DtoStudent;
import org.jypj.zgcsx.dto.DtoTeacherForContact;
import org.jypj.zgcsx.dto.DtoTeacherForNotice;
import org.jypj.zgcsx.entity.EimsPersonTree;
import org.jypj.zgcsx.entity.EimsTree;
import org.jypj.zgcsx.entity.Student;
import org.jypj.zgcsx.entity.Teacher;
import org.jypj.zgcsx.enums.SchoolEnum;
import org.jypj.zgcsx.service.*;
import org.jypj.zgcsx.utils.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author jian_wu
 * Created by jian_wu on 2017/11/10.
 * TreeController 包含EimsTree和EimsPersonTree
 * space也是树结构，也放在这里了
 * 学校的树结构也放这里了
 *
 * +教师通讯录管理
 *
 */
@Controller
@RequestMapping("tree")
public class TreeController {

    private final String BASE_PARENT_NODETEXT = "中关村三小";

    @Autowired
    private EimsTreeService eimsTreeService;

    @Autowired
    private EimsPersonTreeService eimsPersonTreeService;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DtoTeacherForNotice dtoTeacherForNotice;

    /**
     * 查全部树结构（教师通讯录）
     * @return
     */
    @ResponseBody
    @RequestMapping("all")
    public Result getTree(){
        return new Result(Result.SUCCESS,"接口查询成功",eimsTreeService.getTreeData());
    }


    /**
     * 查教师
     */
    @ResponseBody
    @RequestMapping("teacher/search")
    public Result getTeacherByTree(Page<EimsPersonTree> page, String name, String idCard, String sex, String nodeids){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("xm",name);
        queryMap.put("sfzjh",idCard);
        queryMap.put("xbm",sex);
        if(StringUtil.isEmpty(nodeids)){
            nodeids = "All";
        }
        //根节点nodeId
        String baseNoedeId = "";
        //此处默认通讯录中只有一个跟节点，就是parentId = 0 的节点
        List<EimsTree> parentTree = eimsTreeService.selectList(new EntityWrapper<EimsTree>().eq("PARENTID","0"));
        if(parentTree!=null&&parentTree.size()>0){
            baseNoedeId = parentTree.get(0).getId();
        }
        List<String> nodeIdsList = getChildRenTreeNodeIds(baseNoedeId);
        //1.非通讯录教师
        if( "NoContact".equals(nodeids)){
            page = eimsPersonTreeService.queryPersonTreeNoContactByMap(page,queryMap);
        }
        //全部（包含通讯录和非通讯录）
        else if("All".equals(nodeids)){
            //为了去除通讯录的脏数据,所以加nodeIdlist
            queryMap.put("nodeids",nodeIdsList);
            page = eimsPersonTreeService.queryPersonTreeAllByMap(page,queryMap);
        }else{
           //通讯录
            if("Contact".equals(nodeids)){
                queryMap.put("nodeids",nodeIdsList);
            }else{
                nodeIdsList = getChildRenTreeNodeIds(nodeids);
                queryMap.put("nodeids",nodeIdsList);
            }
            page = eimsPersonTreeService.queryPersonTreeByMap(page,queryMap);
        }
        List<EimsPersonTree> personTreeList = page.getRecords();
        List<DtoTeacherForNotice> teacherList = new ArrayList<>();
        personTreeList.stream().forEach(n->{
            teacherList.add(dtoTeacherForNotice.transfer(n));
        });
        Page<DtoTeacherForNotice> resultPage = new Page<>();
        BeanUtils.copyProperties(page,resultPage,"records");
        resultPage.setRecords(teacherList);
        return  new Result(Result.SUCCESS,"接口调用成功",resultPage);
    }

    /**
     * 地点
     * @return
     */
    @ResponseBody
    @RequestMapping("space/all")
    public Result getSpace(){
            return new Result(Result.SUCCESS,"接口查询成功", CommonCache.getSpaceTree());
    }

    /**
     * 学校树结构
     * @return
     */
    @ResponseBody
    @RequestMapping("school")
    public Result getSchoolTree(){
        return new Result(Result.SUCCESS,"接口查询成功", CommonCache.getSchoolAllTree());
    }

    /**
     * 查学生（根据学校树查）
     * @return
     */
    @ResponseBody
    @RequestMapping("student/search")
    public Result getStudentByTree(Page<Student> page, String name, String idCard, String sex, String nodeids){
        //前端为了统一参数所以也用idCard，这里学生的idCard参数表示学籍号
        String xjh=null;
        if(StringUtil.isNotEmpty(idCard)){
            xjh = idCard;
        }
        EntityWrapper<Student> condition = new EntityWrapper<>();
        if(StringUtil.isNotEmpty(name)){
            condition.like("XM",name);
        }
        if(StringUtil.isNotEmpty(xjh)){
            condition.like("GJXJH",xjh);
        }
        if(StringUtil.isNotEmpty(sex)){
            condition.like("XB",sex);
        }
        condition.eq("VALID","1");
        //根据节点查的话，要先知道节点是属于哪个级别，CommonCache写了方法查询,SchoolEnum里面定义的常量
        if(StringUtil.isNotEmpty(nodeids)){
            String flag = CommonCache.getTreeCodeBelong(nodeids);
            if(flag.equals(SchoolEnum.SCHOOL_NODE.getCode())){
                /**
                 * 部分学生没有学校id，但这部分学校任然是有效数据，所以查学校时，仍然查全部
                 */
                //condition.eq("XXID",nodeids);
            }else if(flag.equals(SchoolEnum.CAMPUS_NODE.getCode())){
                condition.eq("XQID",nodeids);
            }else if (flag.equals(SchoolEnum.GRADE_NODE.getCode())){
                condition.eq("JBID",nodeids);
            }else if (flag.equals(SchoolEnum.CLASS_NODE.getCode())){
                condition.eq("BJID",nodeids);
            }
        }
        page = studentService.selectPage(page,condition);
        List<Student> studentList = page.getRecords();
        List<DtoStudent> resultList = new ArrayList<>();
        studentList.stream().forEach(n->{
            resultList.add(DtoStudent.fransfer(n));
        });
        Page<DtoStudent> resultPage = new Page<>();
        BeanUtils.copyProperties(page,resultPage,"records");
        resultPage.setRecords(resultList);
        return new Result(Result.SUCCESS,"接口查询成功",resultPage);
    }

    /**
     * 树节点查所有子节点，包含自己
     * @param nodeId
     */
    private List<String> getChildRenTreeNodeIds(String nodeId){
        List<EimsTree> eimsTrees=eimsTreeService.queryChildenTree(nodeId);
        List<String> codeids = new ArrayList<>();
        for(EimsTree eimsTree:eimsTrees){
            codeids.add(eimsTree.getId());
        }
        return codeids;
    }


    //通讯录Start

    @ResponseBody
    @RequestMapping("contact")
    public Result contactTree(){
        return new Result(getContactTree());
    }

    /**
     * 通讯录树结构
     * @return
     */
    public  List<DtoContactTree> getContactTree(){
        List<EimsTree> list = eimsTreeService.getTreeDataWithOutNoContact();
        List<EimsPersonTree> eimsPersonTrees = eimsPersonTreeService.selectList(new EntityWrapper<EimsPersonTree>());
        List<DtoContactTree> rootList = DtoContactTree.transfer(list, eimsPersonTrees);
        List<DtoContactTree> resultList = new ArrayList<>();
        list.stream().forEach(n->{
            if("0".equals(n.getParentId())){
                DtoContactTree dtoContactTree =new DtoContactTree();
                dtoContactTree.setNodeId(n.getId());
                DtoContactTree.toTree(rootList,dtoContactTree);
                resultList.addAll(dtoContactTree.getChildrenNode());
            }
        });
        return resultList;
    }

    /**
     * parentId,text
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Result addNode(EimsTree eimsTree,String nodeName,HttpServletRequest request) {
        String userId = LoginController.getUserId(request);
        if (StringUtil.isEmpty(eimsTree.getParentId())) {
            List<EimsTree> parentList = eimsTreeService.selectList(new EntityWrapper<EimsTree>().eq("TEXT",BASE_PARENT_NODETEXT));
            eimsTree.setParentId(parentList.get(0).getId());
        }
        if(StringUtil.isNotEmpty(nodeName)){
            eimsTree.setText(nodeName);
        }
        if("校区".equals(nodeName)){
            return new Result(Result.FAIL,"校区为特殊节点，暂时无法添加和删除");
        }
        eimsTree.setId(StringUtil.getLowUUID());
        eimsTree.setCreatUser(userId);
        eimsTree.setCtime(new Date());
        eimsTree.setModifyUser(userId);
        eimsTree.setMtime(new Date());
        eimsTreeService.insert(eimsTree);
        return new Result();
    }

    /**
     * parentId,text,id
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public Result update(EimsTree eimsTree, HttpServletRequest request,String nodeId,String nodeName) {
        if (StringUtil.isEmpty(nodeId)) {
            return new Result(Result.FAIL, "没有id，无法更新");
        }
        //String userId = LoginController.getUserId(request);
        if (StringUtil.isEmpty(eimsTree.getParentId())) {
            eimsTree.setParentId("0");
        }
        if (StringUtil.isEmpty(nodeId)) {
            eimsTree.setId(nodeId);
        }
        if (StringUtil.isEmpty(nodeName)) {
            eimsTree.setText(nodeName);
        }
        if("校区".equals(nodeName)){
            return new Result(Result.FAIL,"校区为特殊节点，暂时无法添加和删除");
        }
        //eimsTree.setModifyUser(userId);
        eimsTree.setMtime(new Date());
        eimsTreeService.updateNode(eimsTree);
        return new Result();
    }

    /**
     * 删除节点
     * @param id 节点id
     */
    @ResponseBody
    @RequestMapping("del/{id}")
    public Result delNode(@PathVariable("id") String id){
        //根节点不让删除，不然所有的数据全没了
        EimsTree eimsTree = eimsTreeService.selectById(id);
        if(BASE_PARENT_NODETEXT.equals(eimsTree.getText())){
            return new Result(Result.FAIL,"中关村三小节点无法删除");
        }
        if("校区".equals(eimsTree.getText())){
            return new Result(Result.FAIL,"校区为特殊节点，暂时无法添加和删除");
        }
        eimsTreeService.delNodeWithTeacher(id);
        return new Result();
    }

    /**
     * nodeId,teacherIds
     * 节点添加教师
     * @return
     */
    @ResponseBody
    @RequestMapping("add/teacher")
    public Result addTeacherToNode(String nodeId, @RequestParam(value = "teacherIds[]",required = false) String[] teacherIds, HttpServletRequest request) {
        //1.检查nodeId是否存在,并获取到nodeName
        String nodeName;
        if (StringUtil.isEmpty(nodeId)) {
            return new Result(Result.FAIL, "没有nodeId");
        } else {
            EimsTree eimsTree = eimsTreeService.selectById(nodeId);
            if (eimsTree == null) {
                return new Result(Result.FAIL, "节点已经被删除");
            }else {
                nodeName = eimsTree.getText();
            }
        }

        //2.通讯录添加教师(多教师)
        String userId = LoginController.getUserId(request);
        List<EimsPersonTree> personTreeList = new ArrayList<>();
        //2.1查此节点已经存在的教师（比较添加的教师是否重复）,添加不重复的
        List<EimsPersonTree> existTeachers = eimsPersonTreeService.selectList(new EntityWrapper<EimsPersonTree>().eq("NODEID",nodeId));
        //已经在此节点的教师ids
        List<String> existTeacherIds = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(existTeachers)){
            existTeachers.forEach(n->existTeacherIds.add(n.getTeacherId()));
        }
        //添加成功的教师名单
        List<String> successNames = new ArrayList<>();
        //添加失败的教师名单
        List<String> failNames = new ArrayList<>();
        if(teacherIds!=null &&teacherIds.length>0){
            for(String teacherId:teacherIds){
                EimsPersonTree personTree = new EimsPersonTree();
                //2.2教师查单条
                Teacher teacher = teacherService.selectById(teacherId);
                //2.3如果教师已经存在，则不添加
                if(existTeacherIds.contains(teacherId)){
                    failNames.add(teacher.getTeacherName());
                    continue;
                }
                personTree.setId(StringUtil.getLowUUID());
                personTree.setNodeName(nodeName);
                personTree.setNodeId(nodeId);
                personTree.setTeacherId(teacherId);
                personTree.setTeacherName(teacher.getTeacherName());
                personTree.setTeacherbm(teacher.getBm());
                personTree.setCreatUser(userId);
                personTree.setCtime(new Date());
                personTree.setModifyUser(userId);
                personTree.setMtime(new Date());
                personTreeList.add(personTree);
                successNames.add(teacher.getTeacherName());
            }
            if(personTreeList.size()>0){
                eimsPersonTreeService.insertBatch(personTreeList);
            }
        }
        //3.处理下添加成功和添加失败的名单
        StringBuffer sb=new StringBuffer();
        for(String failName:failNames){
            sb.append(failName+",");
        }
        if(sb.length()>0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        String msg="";
        String code;
        if(failNames.size()==0){
            msg="添加成功";
            code="0";
        }else if(successNames.size()==0){
            msg=sb.toString()+"已经添加";
            code="-1";
        }else {
            msg=sb.toString()+"已经添加,其余教师添加成功";
            code="-1";
        }
        return new Result(code,msg);
    }

    /**
     * 节点删除教师
     * @param nodeId
     * @param teacherId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("del/teacher")
    public Result delNodeTeacher(String nodeId,String teacherId, HttpServletRequest request){
        EntityWrapper<EimsPersonTree> condition = new EntityWrapper<>();
        condition.eq("NODEID",nodeId);
        condition.in("TEACHERID",teacherId);
        eimsPersonTreeService.delete(condition);
        return new Result();
    }

    /**
     * 通讯录查询所有教师
     * @param teacherName
     * @param sex
     * @param bm 别名
     * @param phoneNum
     * @param mzm 名族
     * @param gradeId 年级id
     * @return
     */
    @ResponseBody
    @RequestMapping("contact/teacher/search")
    public Result getAllTeacher(Page<Teacher> page, String teacherName, String sex, String bm, String phoneNum, String mzm, String gradeId,
    String kcmc){
        Map<String,Object> queryMap = new HashMap<>(16);
        queryMap.put("teacherName",teacherName);
        queryMap.put("sex",sex);
        queryMap.put("bm",bm);
        queryMap.put("phoneNum",phoneNum);
        queryMap.put("mzm",mzm);
        queryMap.put("gradeId",gradeId);
        queryMap.put("kcmc",kcmc);
        teacherService.selectAllTeacher(page,queryMap);
        Page<DtoTeacherForContact> resultPage = new Page<>();
        List<DtoTeacherForContact> resultList = new ArrayList<>();
        page.getRecords().stream().forEach(n->{
            DtoTeacherForContact teacherForContact = DtoTeacherForContact.transfer(n);
            resultList.add(teacherForContact);
        });
        BeanUtils.copyProperties(page,resultPage,"records");
        resultPage.setRecords(resultList);
        return new Result(resultPage);
    }
    //通讯录end

    /**
     * 查所有教师的id
     * 逗号分隔
     * @return
     */
    @ResponseBody
    @RequestMapping("all/teacherIds")
    public Result chooseAllTeacher(){
        List<Teacher> teachers = teacherService.selectAllTeacherIds();
        List<String> list = new ArrayList<>();
        teachers.forEach(n->list.add(n.getId()));
        String ids = String.join(",",list);
        return new Result(ids);
    }

}
