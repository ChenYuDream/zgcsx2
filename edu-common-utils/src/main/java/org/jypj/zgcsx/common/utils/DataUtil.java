package org.jypj.zgcsx.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;


/**
 * common项目api接口辅助类
 *
 * @author qi_ma
 * @since 2017-12-8
 */

public class DataUtil {

    /**
     * 公用模块项目地址
     */
    private String commonProjectUrl;

    public DataUtil(String commonProjectUrl) {
        this.commonProjectUrl = commonProjectUrl;
    }

    public DataUtil() {
    }

    public void setCommonProjectUrl(String commonProjectUrl) {
        this.commonProjectUrl = commonProjectUrl;
    }

    public String getCommonProjectUrl() {
        return commonProjectUrl;
    }

    /**
     * 获取角色数据权限范围
     *
     * @param roleId
     * @return null:全部数据范围，emptyArray:无数据范围，其他：班级数组
     */
    public String[] getUserData(String loginName, String roleId) {
        JSONObject jsonObject = HttpUtil.getJson(commonProjectUrl + "/api/user/data?loginName=" + loginName + "&roleId=" + roleId);
        String[] result = null;
        if (jsonObject != null) {
            jsonObject = jsonObject.getJSONObject("result");
            String isAll = jsonObject.getString("isAll");
            if (Objects.equals(isAll, "0")) {
                return null;
            }
            JSONArray array = jsonObject.getJSONArray("classes");
            if (array == null) {
                return new String[0];
            }
            result = new String[array.size()];
            array.toArray(result);
        }
        return result;
    }

}
