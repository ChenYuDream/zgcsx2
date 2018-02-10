package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.entity.CourseNotice;
import org.jypj.zgcsx.entity.EimsPersonTree;
import org.jypj.zgcsx.entity.Student;
import org.jypj.zgcsx.service.EimsPersonTreeService;
import org.jypj.zgcsx.service.StudentService;
import org.jypj.zgcsx.utils.CourseNoticeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by jian_wu on 2017/11/14.
 */
@Data
@Component
public class DtoNoticeDetail {

    private String id;
    private String noticeTitle;
    private String evenProperty;
    private String noticeLevel;
    private String evenStyle;
    private String evenLevel;
    private String noticeContent;
    private String place;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date start;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date end;
    private List<Object> teachers;
    private List<Object> students;
    private List<Object> initiator;  //发起人，前端传进来数组，我回传也传数组
    private List<String> evenTypes;  //多选，数据库存放时，以逗号分隔，此处转成数组
    private String evenType;

    @JsonIgnore
    @Autowired
    private EimsPersonTreeService eimsPersonTreeService;

    @JsonIgnore
    @Autowired
    private StudentService studentService;

    public DtoNoticeDetail transfer(CourseNotice courseNotice){
        DtoNoticeDetail dtoNoticeDetail = new DtoNoticeDetail();
        CourseNoticeUtil.translation(courseNotice);
        BeanUtils.copyProperties(courseNotice,dtoNoticeDetail);
        if(StringUtil.isEmpty(dtoNoticeDetail.getNoticeContent())){
            dtoNoticeDetail.setNoticeContent("");
        }
        dtoNoticeDetail.setId(courseNotice.getId());
        dtoNoticeDetail.setStart(courseNotice.getNoticeStart());
        dtoNoticeDetail.setEnd(courseNotice.getNoticeEnd());
        List<EimsPersonTree> teacherList = eimsPersonTreeService.queryNoticeTeacher(courseNotice.getId());
        List<Student> studentList = studentService.queryNoticeStudent(courseNotice.getId());
        //参与教师
        List<Object> teachers = new ArrayList<>();
        Map<String,Object> teacherMap;
        for(EimsPersonTree teacher:teacherList){
            teacherMap = new HashMap<>();
            teacherMap.put("teacherId",teacher.getTeacherId());
            teacherMap.put("teacherName",teacher.getTeacherName());
            teacherMap.put("teacherBm",teacher.getTeacherbm());
            teachers.add(teacherMap);
        }
        dtoNoticeDetail.setTeachers(teachers);

        //学生
        List<Object> students = new ArrayList<>();
        Map<String,Object> studentMap;
        for(Student student:studentList){
            studentMap = new HashMap<>();
            studentMap.put("studentId",student.getId());
            studentMap.put("studentName",student.getXm());
            students.add(studentMap);
        }
        dtoNoticeDetail.setStudents(students);

        //发起人
        EimsPersonTree creater = eimsPersonTreeService.queryTeacher(courseNotice.getCreater());
        teachers = new ArrayList<>();
        teacherMap = new HashMap<>();
        teacherMap.put("teacherId",creater.getTeacherId());
        teacherMap.put("teacherName",creater.getTeacherName());
        teachers.add(teacherMap);
        dtoNoticeDetail.setInitiator(teachers);

        //evenTypes
        List<String> evenTypesList= new ArrayList<>();
        String evenTypes=courseNotice.getEvenType();
        if(StringUtil.isNotEmpty(evenTypes) && evenTypes.contains(",")){
            String types[] = evenTypes.split(",");
            for(String str:types){
                evenTypesList.add(str);
            }
        }else if(StringUtil.isNotEmpty(evenTypes)&&!evenTypes.contains(",")){
            evenTypesList.add(evenTypes);
        }
        dtoNoticeDetail.setEvenTypes(evenTypesList);

        return dtoNoticeDetail;
    }

}
