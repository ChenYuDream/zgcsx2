package org.jypj.zgcsx.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.log4j.Logger;
import org.jypj.zgcsx.common.dto.DtoFile;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.FileUtil;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.dto.*;
import org.jypj.zgcsx.entity.*;
import org.jypj.zgcsx.enums.Notice;
import org.jypj.zgcsx.intercept.AuthIntercept;
import org.jypj.zgcsx.service.CourseNoticeAttachmentService;
import org.jypj.zgcsx.service.CourseNoticeEvaluateService;
import org.jypj.zgcsx.service.CourseNoticePersonService;
import org.jypj.zgcsx.service.CourseNoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 * 云事件 前端控制器
 * </p>
 *
 * @author jian_wu
 * @since 2017-11-06
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    private Logger log = Logger.getLogger(NoticeController.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private CourseNoticeService courseNoticeService;

    @Autowired
    private CourseNoticeAttachmentService attachmentService;

    @Autowired
    private DtoNoticeDetail dtoNoticeDetail;

    @Autowired
    private CourseNoticePersonService personService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private CourseNoticeEvaluateService noticeEvaluateService;

    @Value("${APACHE.SERVER}")
    private String APACHE_IP;

    @Value("${attachment.maxNum}")
    private String attachment_maxNum;

    //地址弹窗
    @RequestMapping("layer/address")
    public String toAddressDiv(){
        return "layer-tpl/addTemplate-address";
    }
    //选人弹窗
    @RequestMapping("layer/people")
    public String toPeopleDiv(){
        return "layer-tpl/addTemplate-people";
    }
    //日历主页
    @RequestMapping("calendar")
    public String toCalendar(HttpServletRequest request){
        request.setAttribute("model","calendar");
        return "main";
    }
    //反馈弹窗
    @RequestMapping("layer/feedback/{noticeId}")
    public String toFeedbackLayer(@PathVariable("noticeId")String noticeId,HttpServletRequest request){
        request.setAttribute("noticeId",noticeId);
        return "layer-tpl/feedback-tpl";
    }
    //添加事件主页
    @RequestMapping("to/add")
    public String toAdd(HttpServletRequest request){
        request.setAttribute("model","event_add");
        return "main";
    }
    //事件列表
    @RequestMapping("to/list")
    public String toListPage(HttpServletRequest request){
        request.setAttribute("model","event_list");
        return "main";
    }
    //全部事件
    @RequestMapping("to/list/all")
    public String toListAllPage(HttpServletRequest request){
        request.setAttribute("model","event_list_all");
        return "main";
    }

    //事件详情
    @RequestMapping("detail/{noticeId}")
    public String toDetail(@PathVariable("noticeId") String noticeId, HttpServletRequest request){
        User user = LoginController.getUser(request);
        String userId = LoginController.getUserId(request);
        try {
            if(StringUtil.isNotEmpty(noticeId)){
                //验证notice是否存在
                CourseNotice courseNotice = courseNoticeService.selectById(noticeId);
                if(courseNotice != null) {
                    request.setAttribute("id",noticeId);
                    //是否可以反馈   非自己的活动事件可以反馈
                    if(Notice.ACTIVITY_TYPE.getCode().equals(courseNotice.getEvenStyle()) && !userId.equals(courseNotice.getCreater())){
                        request.setAttribute("ifShowFeedback","1");
                        CourseNoticePerson noticePerson = personService.selectOne(new EntityWrapper<CourseNoticePerson>().
                                eq("NOTICE_ID",noticeId).eq("PERSON_ID",userId));
                        if(noticePerson == null){
                            request.setAttribute("ifShowFeedback","0");
                        }
                        //是否已经反馈
                        if(StringUtil.isNotEmpty(noticePerson.getOper())){
                            request.setAttribute("alreadyFeedback","1");
                            request.setAttribute("noticePerson",noticePerson);
                            request.setAttribute("timeOut","0");
                        }else{
                            request.setAttribute("alreadyFeedback","0");
                            //判断时间是否可以反馈    事件开始时间不能大于当前时间
                            if(new Date().after(courseNotice.getNoticeStart())){
                                request.setAttribute("timeOut","1");
                            }else{
                                request.setAttribute("timeOut","0");
                            }
                        }
                    }else{
                        request.setAttribute("ifShowFeedback","0");
                        request.setAttribute("alreadyFeedback","0");
                        request.setAttribute("timeOut","0");
                    }
                    //可以评论的情况 活动类型事件  参与者都可以评论    评论时间是结束活动后一周内
                    if(Notice.ACTIVITY_TYPE.getCode().equals(courseNotice.getEvenStyle())){
                        CourseNoticePerson noticePerson = personService.selectOne(new EntityWrapper<CourseNoticePerson>().
                                eq("NOTICE_ID",noticeId).eq("PERSON_ID",userId));
                        Calendar c = new GregorianCalendar();
                        c.setTime(courseNotice.getNoticeEnd());
                        c.add(Calendar.DATE,7);
                        Date endCommentDate = c.getTime();
                        CourseNoticeEvaluate evaluate = noticeEvaluateService.selectOne(new EntityWrapper<CourseNoticeEvaluate>().eq("NOTICE_ID",noticeId)
                                .eq("NOTICE_PERSON_ID",userId));
                        request.setAttribute("evaluate",evaluate);
                        if(new Date().after(courseNotice.getNoticeEnd()) && new Date().before(endCommentDate) && noticePerson!=null){
                            request.setAttribute("ifShowCommentDiv","1");
                        }else{
                            request.setAttribute("ifShowCommentDiv","0");
                        }
                        if(new Date().after(endCommentDate)&& noticePerson!=null && evaluate==null){
                            request.setAttribute("commentTimeOutDiv","1");
                        }else{
                            request.setAttribute("commentTimeOutDiv","0");
                        }
                    }
                    //事件结束七天后 不能再上传附件
                    Calendar c = new GregorianCalendar();
                    c.setTime(courseNotice.getNoticeEnd());
                    c.add(Calendar.DATE,7);
                    Date endFileUploadDate = c.getTime();
                    if(new Date().after(endFileUploadDate)){
                        request.setAttribute("endFileUpload","1");
                    }else{
                        request.setAttribute("endFileUpload","0");
                    }
                }else{
                    return "redirect:"+StringUtil.getLocalUrl(request) + "/login";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(request.getAttribute("ifShowCommentDiv") == null){
            request.setAttribute("ifShowCommentDiv","0");
        }
        if(request.getAttribute("ifShowFeedback") == null){
            request.setAttribute("ifShowFeedback","0");
        }
        if(request.getAttribute("timeOut") == null){
            request.setAttribute("timeOut","0");
        }
        if(request.getAttribute("endFileUpload") == null){
            request.setAttribute("endFileUpload","0");
        }
        //如果有管理菜单，就可以删除附件
        if((AuthIntercept.checkMenu(user.getMenuList(),"notice/to/list/all"))){
            request.setAttribute("ifCanDelFile","1");
        }else{
            request.setAttribute("ifCanDelFile","0");
        }
        request.setAttribute("model","event_detail");
        return "main";
    }

    //修改
    @RequestMapping("update/page/{noticeId}")
    public String toUpdatePage(@PathVariable("noticeId")String noticeId, HttpServletRequest request){
        String userId = LoginController.getUserId(request);
        try {
            if(StringUtil.isNotEmpty(noticeId)){
                //验证notice是否存在
                CourseNotice courseNotice = courseNoticeService.selectById(noticeId);
                if(courseNotice != null) {
                    request.setAttribute("courseNotice",courseNotice);
                }else{
                    return "redirect:"+StringUtil.getLocalUrl(request) + "/login";
                }
                //权限判断  只有自己和拥有事件管理菜单的用户才能进入修改页面
                String uri = request.getRequestURI();
                User user = LoginController.getUser(request);
                boolean flag = false;
                if((AuthIntercept.checkMenu(user.getMenuList(),"notice/to/list/all")&&uri.contains("update/page")) ||courseNotice.getCreater().equals(userId)){
                    flag=true;
                }
                if(!flag){
                    return "redirect:"+StringUtil.getLocalUrl(request) + "/login";
                }
                List<CourseNoticePerson> personList = personService.selectAllPerson(noticeId);
                List<String> teacherIdList = new ArrayList<>();
                List<String> studentIdList = new ArrayList<>();
                personList.stream().forEach(n->{
                    if("1".equals(n.getNoticePersonType())){
                        teacherIdList.add(n.getPersonId());
                    }else if("2".equals(n.getNoticePersonType())){
                        studentIdList.add(n.getPersonId());
                    }
                });
                String teacherIds = String.join(",", teacherIdList);
                String studentIds = String.join(",", studentIdList);
                request.setAttribute("teacherIds",teacherIds);
                request.setAttribute("studentIds",studentIds);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        request.setAttribute("model","event_update");
        return "main";
    }

    //时间轴
    @RequestMapping("to/time/shaft")
    public String toDetail(HttpServletRequest request){
        request.setAttribute("model","time_shaft");
        return "main";
    }

    //通讯录添加教师弹窗
    @RequestMapping("txl/add/teacher/layer")
    public String toTXLAddTeacherLayer(){
        return "layer-tpl/addTeacher-tpl";
    }

    //通讯录
    @RequestMapping("to/txl")
    public String toTxlPage(HttpServletRequest request){
        request.setAttribute("model","txl");
        return "main";
    }

    //代发页面
    @RequestMapping("add/notice/other")
    public String toAddNoticeForOther(HttpServletRequest request){
        request.setAttribute("model","addNoticeForOther");
        request.setAttribute("ifForOther","1");
        return "main";
    }

    /**
     * 判断事件是否存在
     * @return
     */
    @ResponseBody
    @RequestMapping("detail/exist/{noticeId}")
    public Result detailExist(@PathVariable("noticeId")String noticeId){
        Map<String,Object> resultMap = new HashMap<>(16);
        if(StringUtil.isNotEmpty(noticeId)){
            CourseNotice courseNotice =courseNoticeService.selectById(noticeId);
            if(courseNotice == null){
                return new Result(Result.FAIL,"事件不存在");
            }
            //判断下事件是否已经开始 0 未开始 1一开始 2已结束
            resultMap.put("ifBegin","0");
            if(new Date().after(courseNotice.getNoticeStart())){
                resultMap.put("ifBegin","1");
            }
            if(new Date().after(courseNotice.getNoticeEnd())){
                resultMap.put("ifBegin","2");
            }
        }
        return new Result(resultMap);
    }

    //日历数据
    @ResponseBody
    @RequestMapping("calendar/data")
    public Result getTestData(String startTime, String endTime,HttpServletRequest request) throws Exception{
        Map<String,Object> queryMap = new HashMap<>();
        if(StringUtil.isNotEmpty(startTime)&&startTime.length()>10){
            queryMap.put("NOTICE_START",format.parse(startTime));
        }else if(StringUtil.isNotEmpty(startTime)&&startTime.length()<=10){
            queryMap.put("NOTICE_START",format2.parse(startTime));
        }
        if(StringUtil.isNotEmpty(endTime)&&endTime.length()>10){
            queryMap.put("NOTICE_END",format.parse(endTime));
        }else if(StringUtil.isNotEmpty(endTime)&&endTime.length()<=10){
            queryMap.put("NOTICE_END",format2.parse(endTime));
        }
        queryMap.put("personId", LoginController.getUserId(request));
        List<CourseNotice> list = courseNoticeService.selectCalByMap(queryMap);
        List<DtoNotice> resultList = new ArrayList<>();
        list.stream().forEach(n->{
            resultList.add(DtoNotice.transfer(n));
        });
        return new Result(Result.SUCCESS,"接口查询成功",resultList);
    }


    /**
     * 分页查
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Result noticeList(Page<CourseNotice> page, String noticeLevel, String evenLevel, String evenTime, String start, String end,
                             HttpServletRequest request,String isAll,String noticeTitle) throws ParseException{
        Map<String,Object> queryMap = new HashMap<>(16);
        if(StringUtil.isNotEmpty(evenTime)){
            String timeStr[] = evenTime.split("-");
            start = timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
            end = timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
        }
        if(StringUtil.isNotEmpty(noticeLevel)){
            queryMap.put("noticeLevel",noticeLevel);
        }
        if(StringUtil.isNotEmpty(evenLevel)){
            queryMap.put("evenLevel",evenLevel);
        }
        if(StringUtil.isNotEmpty(noticeTitle)){
            queryMap.put("noticeTitle",noticeTitle);
        }
        if(StringUtil.isNotEmpty(start)){
            Date noticeStart = format2.parse(start);
            queryMap.put("NOTICE_START",noticeStart);
        }
        if(StringUtil.isNotEmpty(end)){
            Date noticeEnd = format2.parse(end);
            queryMap.put("NOTICE_END",noticeEnd);
        }
        if(StringUtil.isEmpty(isAll)){
            queryMap.put("personId",LoginController.getUserId(request));
        }
        page = courseNoticeService.selectListByPage(page,queryMap);
        Page<DtoNoticeList> resultPage = new Page<>();
        BeanUtils.copyProperties(page,resultPage,"records");
        List<DtoNoticeList> resultList = new ArrayList<>();
        page.getRecords().stream().forEach(n-> resultList.add(DtoNoticeList.transfer(n)));
        resultPage.setRecords(resultList);
        return new Result(resultPage);
    }

    /**
     * 添加事件
     * @param courseNotice
     * @param teachers
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Result addNewNotice(CourseNotice courseNotice, @RequestParam(value = "teachers[]",required = false) String[] teachers,
                               @RequestParam(value = "evenTypes[]",required = false) String[] evenTypes,
                               String initiator,
                               @RequestParam(value = "students[]",required = false) String[] students, String evenTime,
                               HttpServletRequest request){
        try {
            String error= convertData(courseNotice,evenTime,evenTypes,initiator);
            if(courseNotice.getNoticeStart()==null||courseNotice.getNoticeEnd()==null){
                return new Result(Result.FAIL,error);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Result.FAIL,"数据转换错误");
        }
        User user = LoginController.getUser(request);
        //1.添加学年学期，校区，学校字段
        courseNotice.setXxid(user.getXxid());
        courseNotice.setXqid(user.getXqid());
        courseNotice.setCimsXn(user.getXn());
        courseNotice.setCimsXq(user.getXq());
        //2.以下字段没有用，但为了和以前的数据保持一致，还是给默认值
        courseNotice.setNoticeRemind(1);
        courseNotice.setNoticeStick(0);
        courseNotice.setNoticeStickEnddays(0);
        courseNotice.setNoticepubstate("1");
        courseNotice.setFbtype("1");
        //3.如果参与教师没有发起人自己，则在参与教师中加入发起人
        if(teachers!=null){
            int flag = 0;
            for(String teacherId:teachers){
                if(teacherId.equals(user.getUserid())){
                    flag = 1;
                }
            }
            if(flag == 0){
                String[] newTeachers = new String[teachers.length+1];
                for(int i=0;i<teachers.length;i++){
                    newTeachers[i]=teachers[i];
                }
                newTeachers[teachers.length]=user.getUserid();
                //数组加元素真麻烦
                teachers=newTeachers;
            }
        }else{
            //教师数组为空的情况
            teachers = new String[1];
            teachers[0] = user.getUserid();
        }
        CourseNotice tempNotice = courseNoticeService.selectById(courseNotice.getId());
        if(tempNotice == null){
            courseNoticeService.saveNotice(courseNotice,teachers,students);
        }else{
            courseNoticeService.updateNotice(courseNotice,teachers,students);
        }
        //4.自动刷新教师日历
        /*if(teachers!=null){
            for(String userId:teachers){
                WebsocketController.refreshCanlendar(userId);
            }
        }*/
        if(tempNotice == null){
            return new Result(Result.SUCCESS,"添加成功");
        }else{
            return new Result(Result.SUCCESS,"更新成功");
        }
    }

    /**
     * 查附件
     * @return
     */
    @ResponseBody
    @RequestMapping("files/{noticeId}")
    public Result getAttachements(@PathVariable("noticeId")String noticeId){
        EntityWrapper<CourseNoticeAttachment> condition = new EntityWrapper<CourseNoticeAttachment>();
        condition.eq("COURSE_NOTICE_ID",noticeId);
        List<CourseNoticeAttachment> list = attachmentService.selectList(condition);
        List<DtoAttachment> resultList = new ArrayList<>();
        list.stream().forEach(n->
            resultList.add(DtoAttachment.transfer(n))
        );
        return new Result(Result.SUCCESS,"接口调用成功",resultList);
    }

    /**
     * 查单条
     * @return
     */
    @ResponseBody
    @RequestMapping("one/{noticeId}")
    public Result getOne(@PathVariable("noticeId") String noticeId){
        CourseNotice courseNotice =courseNoticeService.selectById(noticeId);
        if(courseNotice == null){
           return new Result(Result.FAIL,"未查到事件");
        }
        DtoNoticeDetail dtoNoticeDetailResult =  dtoNoticeDetail.transfer(courseNotice);
        return new Result(Result.SUCCESS,"接口查询成功",dtoNoticeDetailResult);
    }


    /**
     * 更新
     * @param courseNotice
     * @param teachers
     * @param evenTypes
     * @param initiator
     * @param students
     * @param evenTime
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public Result update(CourseNotice courseNotice, @RequestParam(value = "teachers[]",required = false) String[] teachers,
                         @RequestParam(value = "evenTypes[]",required = false) String[] evenTypes,
                         String initiator,
                         @RequestParam(value = "students[]",required = false) String[] students, String evenTime){
        CourseNotice oldCourseNotice = courseNoticeService.selectById(courseNotice.getId());
        if(oldCourseNotice == null){
            return new Result(Result.FAIL,"事件不存在");
        }
        try {
            convertData(courseNotice,evenTime,evenTypes,initiator);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Result.FAIL,"数据转换错误");
        }
        courseNoticeService.updateNotice(courseNotice,teachers,students);
        return new Result("更新成功");

    }

    /**
     * 时间轴
     */
    @ResponseBody
    @RequestMapping("timeLine")
    public Result showTimeLine( Page<CourseNotice> page, String noticeLevel, String evenLevel, String evenTime, String start, String end,String noticeTitle)throws ParseException{
        Map<String,Object> queryMap = new HashMap<>(16);
        if(StringUtil.isNotEmpty(evenTime)){
            String timeStr[] = evenTime.split("-");
            start = timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
            end = timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
        }
        if(StringUtil.isNotEmpty(noticeLevel)){
            queryMap.put("noticeLevel",noticeLevel);
        }
        if(StringUtil.isNotEmpty(evenLevel)){
            queryMap.put("evenLevel",evenLevel);
        }
        if(StringUtil.isNotEmpty(noticeTitle)){
            queryMap.put("noticeTitle",noticeTitle);
        }
        if(StringUtil.isNotEmpty(start)){
            Date noticeStart = format2.parse(start);
            queryMap.put("NOTICE_START",noticeStart);
        }
        if(StringUtil.isNotEmpty(end)){
            Date noticeEnd = format2.parse(end);
            queryMap.put("NOTICE_END",noticeEnd);
        }
        queryMap.put("isTimeLine","isTimeLine");
        page = courseNoticeService.selectListByPage(page,queryMap);
        List<CourseNotice> list = page.getRecords();
        Object object = DtoNoticeTimeLine.transToTimeLine(list);
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("record",object);
        resultMap.put("current",page.getCurrent());
        resultMap.put("total",page.getTotal());
        return new Result(resultMap);
    }

    /**
     * 删除事件
     * @param noticeId
     * @return
     */
    @ResponseBody
    @RequestMapping("delete/{noticeId}")
    public Result delete(@PathVariable("noticeId") String noticeId){
        courseNoticeService.delNotice(noticeId);
        return new Result();
    }

    /**
     * 上传文件
     * @return
     */
    @ResponseBody
    @RequestMapping("upload")
    public Result uploadFiles(HttpServletResponse response,
                              HttpServletRequest request,String id) throws IOException{
        // 先实例化一个文件解析器
        CommonsMultipartResolver coMultiResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());

        // 判断request请求中是否有文件上传
        if (coMultiResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获得文件，input标签name属性值相同都为"file"
            List<MultipartFile> files = multiRequest.getFiles("file");
            Result fileResult = fileUtil.uploadFile(files);
            //上传成功
            if(fileResult.getCode().equals(Result.SUCCESS)){
                @SuppressWarnings("unchecked")
                List<DtoFile> dtoFiles = (List<DtoFile>)fileResult.getResult();
                //处理上传完的数据
                if(dtoFiles!=null&&dtoFiles.size()>0){
                    for(DtoFile file:dtoFiles){
                        //如果传入了事件id，则将文件信息写入数据库
                        if(StringUtil.isNotEmpty(id)){
                            CourseNoticeAttachment attachment = new CourseNoticeAttachment();
                            attachment.setId(StringUtil.getUpUUID());
                            attachment.setCourseNoticeId(id);
                            attachment.setFileName(file.getFileName());
                            attachment.setPath(file.getFilePath());
                            attachment.setSuffix(file.getFileType());
                            //创建人姓名，时间
                            User user = LoginController.getUser(request);
                            attachment.setCreateUser(user.getUsername());
                            attachment.setCreateUserId(user.getUserid());
                            attachment.setModifyUser(user.getUsername());
                            attachmentService.insert(attachment);
                        }
                    }
                }
            }

        }
        return new Result(Result.SUCCESS,"上传成功");
    }

    /**
     * 下载
     * @return
     */
    @ResponseBody
    @RequestMapping("downLoad")
    public Result downLoadFile(String fileId,HttpServletRequest request,HttpServletResponse response){

        try {
            //1.查数据库获取文件名和地址，前台只传文件id
            CourseNoticeAttachment attachment = attachmentService.selectById(fileId);
            if(attachment == null){
                response.setStatus(409);
                return new Result(Result.FAIL,"文件记录不存在");
            }
            String filePath = attachment.getPath();
            if(filePath.contains("^")){
                filePath = filePath.substring(0,filePath.indexOf("^"));
            }
            String fileName = attachment.getFileName();
            //2.调用FileUtil的方法
            fileUtil.downLoadFile(filePath,fileName,request,response);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(Result.FAIL,"下载错误",e.getMessage());
        }
        return new Result(Result.FAIL,"下载错误");
    }

    /**
     * 删除附件
     * @return
     */
    @ResponseBody
    @RequestMapping("file/del/{id}")
    public Result delFile(@PathVariable("id")String fileId){
        if(StringUtil.isEmpty(fileId)){
            return new Result(Result.FAIL,"没有id");
        }
        //1.删除数据库
        attachmentService.deleteById(fileId);
        //2.删文件

        return new Result();
    }

    /**
     * 检查附件是否最大个数
     * 如果出错则不限制数量
     * @return
     */
    @ResponseBody
    @RequestMapping("check/attachment")
    public Result checkFileNum(String noticeId){
        List<CourseNoticeAttachment> list = attachmentService.selectList(new EntityWrapper<CourseNoticeAttachment>()
                .eq("COURSE_NOTICE_ID",noticeId));
        if(StringUtil.isNumeric(attachment_maxNum)){
            int max = Integer.parseInt(attachment_maxNum);
            if(list!=null&&list.size() >= max){
                return new Result(Result.FAIL,"附件最大上传个数为"+max+"个");
            }
        }
        return new Result();
    }

    /**
     * 反馈事件
     * @return
     */
    @ResponseBody
    @RequestMapping("feedback")
    public Result noticeFeedback(String noticeId, String feedback, String reason, HttpServletRequest request){
        String userId = LoginController.getUserId(request);
        CourseNoticePerson noticePerson = personService.selectOne(new EntityWrapper<CourseNoticePerson>().
                eq("NOTICE_ID",noticeId).eq("PERSON_ID",userId));
        noticePerson.setReason(reason);
        noticePerson.setMtime(new Date());
        noticePerson.setOper(feedback);
        personService.updateById(noticePerson);
        return new Result();
    }

    /**
     * 反馈列表
     * @return
     */
    @ResponseBody
    @RequestMapping("feedback/list")
    public Result feedbackList(String noticeId,String type,String name,Page<CourseNoticePerson> page){
        Map<String,Object> queryMap = new HashMap<>(16);
        queryMap.put("noticeId",noticeId);
        queryMap.put("type",type);
        queryMap.put("name",name);
        //不看发起人的反馈 因为发起人不能反馈
        CourseNotice notice = courseNoticeService.selectById(noticeId);
        queryMap.put("creater",notice.getCreater());
        personService.selectListByPage(page,queryMap);
        return new Result(page);
    }

    /**
     * 评价
     * @return
     */
    @ResponseBody
    @RequestMapping("comment")
    public Result commentCommit(CourseNoticeEvaluate evaluate,HttpServletRequest request){
        String userId = LoginController.getUserId(request);
        CourseNotice notice = courseNoticeService.selectById(evaluate.getNoticeId());
        if(notice== null){
            return new Result(Result.FAIL,"事件不存在");
        }
        CourseNoticeEvaluate evaluateTemp = noticeEvaluateService.selectOne(new EntityWrapper<CourseNoticeEvaluate>().eq("NOTICE_ID",evaluate.getNoticeId())
                .eq("NOTICE_PERSON_ID",userId));
        if(evaluateTemp != null){
            return new Result(Result.FAIL,"您已经评论过了");
        }
        evaluate.setId(StringUtil.getLowUUID());
        evaluate.setCtime(new Date());
        evaluate.setNoticePersonId(userId);
        evaluate.setCreateuser(userId);
        noticeEvaluateService.insert(evaluate);
        return new Result();
    }

    /**
     * 评价列表
     * @return
     */
    @ResponseBody
    @RequestMapping("comment/list")
    public Result commentList(Page<CourseNoticeEvaluate> page,String name,String evaluate,String noticeId){
        Map<String,Object> queryMap = new HashMap<>(16);
        queryMap.put("noticeId",noticeId);
        queryMap.put("name",name);
        queryMap.put("evaluate",evaluate);
        noticeEvaluateService.selectListByPage(page,queryMap);
        return new Result(page);
    }

    /**
     * 获取当前时间
     * yyyy-MM-dd
     * @return
     */
    @ResponseBody
    @RequestMapping("nowDate")
    public Result getNowDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        return new Result(date);
    }

    /**
     * 封装数据转化的方法（事件添加和修改的时候使用）
     */
    private String convertData(CourseNotice courseNotice, String evenTime, String[] evenTypes, String initiator) throws ParseException{
        //1.前台给的格式是 xxxx-xx-xx xx:xx:xx - xxxx-xx-xx xx:xx:xx  拆分第三个'-'
        String timeStr[] = evenTime.split("-");
        String firstPart = timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
        String secondPart = timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
        courseNotice.setNoticeStart(format.parse(firstPart.trim()));
        courseNotice.setNoticeEnd(format.parse(secondPart.trim()));
        //开始时间不能晚于结束时间
        if(format.parse(firstPart.trim()).after(format.parse(secondPart.trim()))){
            courseNotice.setNoticeStart(null);
            courseNotice.setNoticeEnd(null);
            return "开始时间不能晚于结束时间";
        }
        //开始时间不能早于系统时间
        if(format.parse(firstPart.trim()).before(new Date())){
            courseNotice.setNoticeStart(null);
            courseNotice.setNoticeEnd(null);
            return "开始时间不能早于系统时间";
        }
        String evenType="";
        if(evenTypes!=null){
            for(String str:evenTypes){
                evenType += str+",";
            }
            evenType = evenType.substring(0,evenType.length()-1);
            courseNotice.setEvenType(evenType);
        }
        if(initiator != null){
            courseNotice.setCreater(initiator);
        }
        return null;
    }

    /**
     * 封装传参的方法
     * 分页查询和时间轴使用
     * @return
     */
    public EntityWrapper<CourseNotice> getCondition(String noticeLevel, String evenLevel, String evenTime){
        try {
            EntityWrapper<CourseNotice> condition = new EntityWrapper<>();
            String start="";
            String end="";
            if(StringUtil.isNotEmpty(evenTime)){
                String timeStr[] = evenTime.split("-");
                start = timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
                end = timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
            }
            condition.orderBy("CTIME",false);
            if(StringUtil.isNotEmpty(noticeLevel)){
                condition.eq("NOTICE_LEVEL",noticeLevel);
            }
            if(StringUtil.isNotEmpty(evenLevel)){
                condition.eq("EVEN_LEVEL",evenLevel);
            }
            if(StringUtil.isNotEmpty(start)){
                Date noticeStart = format2.parse(start);
                condition.ge("NOTICE_START",noticeStart);
            }
            if(StringUtil.isNotEmpty(end)){
                Date noticeEnd = format2.parse(end);
                condition.le("NOTICE_END",noticeEnd);
            }
            return condition;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new EntityWrapper<CourseNotice>();
    }

    /**
     * 天数加一
     * @return
     */
    public Date datePlusOne(String end) throws ParseException{
        Date noticeEnd = format2.parse(end);
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(noticeEnd);
        calendar.add(Calendar.DATE,1);
        return calendar.getTime();
    }

}
