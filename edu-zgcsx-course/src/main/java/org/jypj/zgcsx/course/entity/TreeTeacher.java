package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 通讯录教师
 * </p>
 *
 * @author qi_ma
 * @since 2017-12-13
 */
@TableName("EIMS_PERSON_TREE")
public class TreeTeacher implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;
    @TableField("NODEID")
    private String treeId;
    @TableField("TEACHERID")
    private String teacherId;
    @TableField("TEACHERBM")
    private String aliasName;
    @TableField(exist = false)
    private Teacher teacher;
    @TableField(exist = false)
    private Tree tree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }
}
