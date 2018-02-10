package org.jypj.zgcsx.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.google.gson.Gson;
import lombok.Data;

/**
* 组织机构权限表
* @author jian_wu
* @create 2017-11-28 14:23
**/
@Data
@TableName("EIMS_ORG_PURVIEW_T")
public class Purview implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

    /**记录ID*/
    private String id;
    /**用户ID*/
    private String userId;
    /**用户角色ID*/
    private String roleId;
    /**机构ID*/
    private String orgId;
    /**类型,01-自动产生,02-手动添加*/
    private String category;
    /**是否删除,0-删除,01-未删除 */
    private String isDel;
    /**ID类别，01-学校ID,02-校区ID,03-年级ID,04-班级ID */
    private String sort;

}