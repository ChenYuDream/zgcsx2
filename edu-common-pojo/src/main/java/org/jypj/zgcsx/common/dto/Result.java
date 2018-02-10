package org.jypj.zgcsx.common.dto;

import java.io.Serializable;

/**
 * <p>
 * description:
 * </p>
 *
 * @author guanfeng_yang
 * @date 2017/7/24 10:32
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String FAIL = "-1";
    public static final String SUCCESS = "0";
    /**
     * 结果码
     */
    private String code;
    /**
     * 结果信息
     */
    private String msg;
    /**
     * 结果集
     */
    private Object result;

    public Result() {
        this.code = SUCCESS;
        this.msg = "接口调用成功";
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Object object) {
        this.code = SUCCESS;
        this.msg = "接口调用成功";
        this.result = object;
    }


    public Result(String code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
