package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 通讯录树状表
 * </p>
 *
 * @author qi_ma
 * @since 2017-12-13
 */
@TableName("EIMS_TREE")
public class Tree implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 名称
     */
    @TableField("TEXT")
    private String name;
    /**
     * 父ID（顶级为0）
     */
    @TableField("PARENTID")
    private String parentId;
    /**
     * 排序
     */
    @TableField("ORDERNUM")
    private Integer orderNum;

    /**
     * 父节点集
     */
    @TableField(exist = false)
    private List<Tree> parents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<Tree> getParents() {
        return parents;
    }

    public void setParents(List<Tree> parents) {
        this.parents = parents;
    }
}
