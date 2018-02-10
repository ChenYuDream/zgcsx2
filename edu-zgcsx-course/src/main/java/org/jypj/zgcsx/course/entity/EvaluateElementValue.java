package org.jypj.zgcsx.course.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 选修课评价要素值表
 *
 * @author qi_ma
 * @since 2018-01-02 9:43
 **/
@TableName("KC_EVALUATE_ELEMENT_VALUE")
public class EvaluateElementValue extends BaseEntity<EvaluateElementValue> {
    private static final long serialVersionUID = 983720266062342263L;
    /**
     * 评价要素
     */
    private String elementId;
    /**
     * 评价标准（字典）,为空表示不区分等级
     */
    private String evaluateLevel;
    /**
     * 评价要素详情
     */
    private String content;

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getEvaluateLevel() {
        return evaluateLevel;
    }

    public void setEvaluateLevel(String evaluateLevel) {
        this.evaluateLevel = evaluateLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
