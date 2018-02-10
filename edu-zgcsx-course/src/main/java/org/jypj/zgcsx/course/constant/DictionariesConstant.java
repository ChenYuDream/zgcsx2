package org.jypj.zgcsx.course.constant;

/**
 * 字典常量
 *
 * @author qi_ma
 * @version 1.0 2017/11/21 14:43
 */
public class DictionariesConstant {
    //00-其他，01-基础层，02-拓展层，03-开放层
    public static final String COURSE_DEFINITION = "cims_kcly_sc";
    //1-国家，2-地方，3-校本，4-自主
    public static final String COURSE_LEVEL = "cims_kc_cc";
    //00-其他,01-语言类,02-历史与社会科学类,03-数学、科学、工程、技术类,04-视觉艺术类,05-表演艺术类,06-积极身体活动的健康生活方式
    public static final String COURSE_CATEGORY = "cims_kcly_ll";
    //1-必修，2-选修，3-社团,4-社团活动,5-课后一小时,6-管理班,7-主题活动
    public static final String COURSE_TYPE = "cims_kc_xxlb";
    //空间校区：C01-北校区,C02-南校区,C03-红山校区
    public static final String SPACE_CAMPUS = "cims_campus";
    //空间建筑楼：A01-建筑楼
    public static final String SPACE_ARCH = "cims_arch";
    //空间类型：//O:办公室,G:体育场馆,S:服务设备用房,R:教室,P:公共空间,D:厨房餐厅,
    public static final String SPACE_TYPE = "cims_sptype";
    //空间楼层：//B1:地下一层,B2:地下二层,F1:地上一层,F2:地上二层,F3:地上三层,F4:地上四层,F5:地上五层,F6:地上六层
    public static final String SPACE_FLOOR = "cims_floor";
    //评价标准：//1：优秀，2：达标，3：待达标
    public static final String EVA_LEVEL = "kc_eva_level";
    //默认评价要素：//01：作品完成质量，02：考勤情况，03：课堂表现
    public static final String EVA_ELEMENTS = "kc_eva_element";
}

