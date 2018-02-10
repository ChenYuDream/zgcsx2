package org.jypj.zgcsx.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 通讯录树型结构表
 * @author jian_wu
 */
@Data
public class EimsTree extends Model<EimsTree> {

    private String id;
    private String text;//节点名称
    @TableField("PARENTID")
    private String parentId;//父节点ID
    private String state;//节点状态
    private String iconcls;//显示的节点图标CSS类ID
    private String remark;//备注
    private Date ctime;//创建时间
    @TableField("CREATUSER")
    private String creatUser;//创建人员
    private Date mtime;//修改时间
    @TableField("MODIFYUSER")
    private String modifyUser;//修改人员
    @TableField(exist = false)
    private List<EimsTree> children; //当前节点的孩子
    @TableField(exist = false)
    private int orderNum;   //当前节点在同级中的顺序


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
