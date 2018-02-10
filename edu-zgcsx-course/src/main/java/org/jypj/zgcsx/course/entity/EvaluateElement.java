package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.List;

/**
 * 选修课评价要素
 *
 * @author qi_ma
 * @since 2018-01-02 9:43
 **/
@TableName("KC_EVALUATE_ELEMENT")
public class EvaluateElement extends BaseEntity<EvaluateElement> {
    private static final long serialVersionUID = 2572810190539786303L;
    /**
     * 评价要素
     */
    private String element;
    /**
     * 选修课ID
     */
    private String optionalCourseId;
    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 评价类型(1:分级，2不分级)
     */
    private String type;

    /**
     * 评价要素
     */
    @TableField(exist = false)
    private List<EvaluateElementValue> evaluateElementValues;

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getOptionalCourseId() {
        return optionalCourseId;
    }

    public void setOptionalCourseId(String optionalCourseId) {
        this.optionalCourseId = optionalCourseId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EvaluateElementValue> getEvaluateElementValues() {
        return evaluateElementValues;
    }

    public void setEvaluateElementValues(List<EvaluateElementValue> evaluateElementValues) {
        this.evaluateElementValues = evaluateElementValues;
    }
}
