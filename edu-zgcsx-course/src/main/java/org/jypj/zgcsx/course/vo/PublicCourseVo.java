package org.jypj.zgcsx.course.vo;

import java.util.List;

/**
 * 公用课表 导出使用
 * 单个sheet
 */
public class PublicCourseVo implements java.io.Serializable {

    private String sheet; //sheet名称
    private List<GradeVo> gradeVoList;

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public List<GradeVo> getGradeVoList() {
        return gradeVoList;
    }

    public void setGradeVoList(List<GradeVo> gradeVoList) {
        this.gradeVoList = gradeVoList;
    }

    /**
     * 单个年级
     */
    public class GradeVo implements java.io.Serializable {
        private List<ClazzVo> clazzVoList;

        public List<ClazzVo> getClazzVoList() {
            return clazzVoList;
        }

        public void setClazzVoList(List<ClazzVo> clazzVoList) {
            this.clazzVoList = clazzVoList;
        }
    }

    /**
     * 单个班级
     */
    public class ClazzVo implements java.io.Serializable {
        private String xnxq;  //学年学期
        private String clazz; //班级
        private String grade; //年级
        private List<CourseVo> courseVoList;

        public String getXnxq() {
            return xnxq;
        }

        public void setXnxq(String xnxq) {
            this.xnxq = xnxq;
        }

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public List<CourseVo> getCourseVoList() {
            return courseVoList;
        }

        public void setCourseVoList(List<CourseVo> courseVoList) {
            this.courseVoList = courseVoList;
        }
    }

    /**
     * 单列数据
     */
    public class CourseVo implements java.io.Serializable {
        private int index; //序号
        private String[] courseName; //星期1-5的课程名称

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String[] getCourseName() {
            return courseName;
        }

        public void setCourseName(String[] courseName) {
            this.courseName = courseName;
        }
    }
}