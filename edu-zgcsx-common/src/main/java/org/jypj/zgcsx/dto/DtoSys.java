package org.jypj.zgcsx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian_wu on 2017/11/28.
 *
 * @author jian_wu
 * 系统导航实体
 */
@Data
@Component
public class DtoSys {

    private String sysName;
    private String sysUrl;
    private String sysIcon;
    private String sysNum;
    @JsonIgnore
    @Value("${YSJ_URL}")
    private String YSJ_URL;

    @JsonIgnore
    @Value("${DKC_URL}")
    private String DKC_URL;

    @JsonIgnore
    @Value("${SXJP_URL}")
    private String SXJP_URL;

    @JsonIgnore
    @Value("${WYXS_URL}")
    private String WYXS_URL;

    @JsonIgnore
    @Value("${ZCPS_URL}")
    private String ZCPS_URL;

    @JsonIgnore
    @Value("${XSXK_URL}")
    private String XSXK_URL;

    @JsonIgnore
    @Value("${CJLR_URL}")
    private String CJLR_URL;

    public DtoSys() {
    }

    public DtoSys(String sysNum, String sysName, String sysUrl, String sysIcon) {
        this.sysNum = sysNum;
        this.sysName = sysName;
        this.sysUrl = sysUrl;
        this.sysIcon = sysIcon;
    }

    /**
     * 初始化
     *
     * @return
     */
    public List<DtoSys> init() {
        List<DtoSys> list = new ArrayList<>();
        list.add(new DtoSys("7", "云事件", YSJ_URL + "?systemNum=7", "icon-fenlei"));
        list.add(new DtoSys("8", "大课程", DKC_URL, "icon-xinzeng"));
        list.add(new DtoSys("9", "双向竞聘", SXJP_URL, "icon-huanfu"));
        list.add(new DtoSys("10", "物业修缮", WYXS_URL, "icon-huanfu"));
        list.add(new DtoSys("11", "职称评审", ZCPS_URL, "icon-huanfu"));
        list.add(new DtoSys("12", "成绩录入", CJLR_URL, "icon-huanfu"));
        list.add(new DtoSys("13", "学生选课", XSXK_URL, "icon-huanfu"));
        return list;
    }

}
