package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 字典表
 *
 * @author yu_xiao
 * @create 2017-12-05
 **/
@TableName("EIMS_CODEMAP_DATA")
public class CodeMapData implements Serializable {
    @TableId
    private String id;

    /**
     * 编码
     */
    @TableField("ITEM_VALUE")
    private String itemValue;

    /**
     * 描述
     */
    @TableField("ITEM_TEXT")
    private String itemText;

    /**
     * 查询code
     */
    @TableField(exist = false)
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
