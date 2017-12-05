package com.yongheng.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by eddy on 2015/4/25.
 */
public class Constant {

    public static final long SELF_SCORE_EXPIRE_TIME = 1 * 24 * 60 * 60 * 1000;//拉取所有成绩超时时间 1天
    public static final int MAX_SCORE = 749;//高考总分最大值
    public static final int MAX_SCORE_900 = 899;
    public static final int MAX_SCORE_480 = 479;
    public static final int MAX_SCORE_630 = 629;
    public static final int MAX_BASE_SCORE = 450;
    public static final int MAX_GENERAL_SCORE = 300;
    public static final int MAX_OPTIONAL_SCORE = 60;
    public static final int MAX_TECH_SCORE = 100;

    public static final String IPIN_AGREEMENT_URL = "http://m.wmzy.com/user_protocol";
    public static final String IPIN_ABOUT_US_URL = "http://m.wmzy.com/about_us";
    public static final String IPIN_FEELBACK_URL = "http://www.ipin.com/gaokao/feelback.html";
    public static final String IPIN_RESEARCH_TEAM_URL = "http://m.wmzy.com/man_data_team.html";


    public static final String IPIN_COLLEGE_ENTRANCE_EXAMINATION_DATE = "-06-07";

    public static final String IPIN_URL_PREFIX = "m.wmzy.com";
    public static final String TEST_URL_PREFIX = "test.gaokao.ipin.com";


    public static final int REQUEST_CODE_FOR_LOCATION = 0x0010;//选择生源地
    public static final int REQUEST_CODE_FOR_GUIDE = 0x11;//用户指引
    public static final int REQUEST_CODE_FOR_DREAM_SCH = 0X0020;//设置梦想大学
    public static final int REQUEST_CODE_FOR_ADD_SCORE = 0X0030;//添加分数
    public static final int REQUEST_CODE_FOR_INPUT_BASIC_INFO = 0x0040;//输入基本信息
    public static final int REQUEST_CODE_FOR_INPUT_SCH_INFO = 0x0050;//输入学校本科率和重本率
    public static final int REQUEST_CODE_FOR_INTENT = 0x0060;//输入意向
    public static final int REQUEST_CODE_FOR_CEPING = 0x0070;//性格测试
    public static final int REQUEST_CODE_FOR_EXP = 0x0080;//第一次启动体验

    public static final int REQUEST_CODE_FROM_CAREER = 0x0071;//性格测试-职业
    public static final int REQUEST_CODE_FROM_HOME = 0x0072;//性格测试-首页工具
    public static final int REQUEST_CODE_FROM_UTIL = 0x0073;//性格测试-我能上的专业

    public static final int REQ_CODE_ZYB_ADD_SCH = 0x0100;//志愿表添加学校
    public static final int REQ_CODE_ZYB_RENAME = 0x0101;//志愿表修改名称

    //    public static final String FIRST_VERSION_NAME = "1.0_alpha_r20150609";//第一个外发版本的versionN
    public static final String INTENT_RESPONSE_FORM_VIP_SHOP = "vip_shop_success";//成功开通VIP

    public static final String VALUE_TYPE_LI = "li";//理科
    public static final String VALUE_TYPE_WEN = "wen";//文科

    public static final String KEY_TYPE = "type";
    public static final String VALUE_SCH = "sch";
    public static final String VALUE_MAJOR = "major";
    public static final String KEY_ZY_BATCH = "zy_batch";
    public static final String KEY_WEB_TITLE_BAR_TYPE = "web_title_bar_type";//webactivity的titlebar的类型
    public static final String VALUE_WEB_TITLE_BAR_TYPE = "rank_list";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LOCATION_NAME = "loc_name";
    public static final String KEY_PROVINCE_ID = "province_id";
    public static final String KEY_WEN_LI = "wenli";
    public static final String KEY_W_L = "wl";
    public static final String KEY_JOB_CATE = "job_cate";
    public static final String KEY_SCORE_INFO = "score_info";
    public static final String KEY_SCORE = "score";
    public static final String KEY_SCORE_RANK = "score_rank";
    public static final String KEY_SCORE_TYPE = "score_type";
    public static final String KEY_INFO_TYPE = "info_type";
    public static final String KEY_RANK_GRADE = "rank_grade";
    public static final String KEY_SHOW_REPORT = "show_report";
    public static final String KEY_GRADE_NUMS = "grade_nums";
    public static final String KEY_SCH_MODEL = "school";
    public static final String KEY_FROM_FIRST = "from_first";
    public static final String KEY_HOME_OPEN_VIEW_ACTION = "add_score";
    public static final String KEY_SWITCH_FILTER_TAB_MSG = "switch_filter_tab_msg";//自动切换到挑选tab时要传递参数的key
    public static final String KEY_HIDDEN_SHARE_BTN = "hidden_share_btn";//是否隐藏右上角分享按钮
    public static final String KEY_IGNORE_URL = "isIsIgnoreUrl";//是否忽然url不进行二次处理
    public static final String KEY_HIDDEN_TOOL_BAR = "hidden_tool_bar";//是否隐藏工具栏
    public static final String KEY_COOKIES = "cookies";
    public static final String KEY_DIPLOMA = "diploma";//本专科
    public static final String KEY_BATCH = "batch";//批次
    public static final String KEY_MAJOR_ID = "major_id";//专业id
    public static final String KEY_MAJOR_NAME = "major_name";//专业名称
    public static final String KEY_MAJOR_CODE = "major_code";//专业代码
    public static final String KEY_YEAR = "year";
    public static final String KEY_YEARS = "years";
    public static final String KEY_SCH_ID = "sch_id";//学校id
    public static final String KEY_SCH_985 = "is_985";//学校是985
    public static final String KEY_SCH_211 = "is_211";//学校是211
    public static final String KEY_SCH_RANK = "sch_rank_index";//学校排名
    public static final String KEY_SCH_TYPE = "sch_type";//学校类型
    public static final String KEY_SCH_LOGO = "sch_logo";//学校logo的url
    public static final String KEY_SCH_NAME = "sch_name";//学校logo的url
    public static final String KEY_TO_DETAIL_WHICH_PAGE = "to_detail_which_page";//进入详情页的哪个碎片
    public static final String KEY_JOB_TEST = "job_test";//职业性格测试结果
    public static final String KEY_CODE = "code";
    public static final String KEY_MSG = "msg";
    public static final String KEY_DATA = "data";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_SCH_LIST = "sch_list";
    public static final String KEY_MAJOR_LIST = "major_list";
    public static final String KEY_HOME_IS_CLICK_MAJOR = "home_is_click_major";//是否点击专业
    public static final String KEY_COLLECTION = "collect_list";//收藏列表
    public static final String KEY_COLLECTION_TYPE = "collect_type";//收藏类型
    public static final String KEY_COLLECTION_COUNT = "count";//收藏数量
    public static final String KEY_START_COUNT = "start";//分配起始位置
    public static final String KEY_COLLECTED_LIST = "collected_list";//收藏列表
    public static final String KEY_AVG_SCORE_LI = "avg_score_li"; //理科平均分
    public static final String KEY_AVG_SCORE_WEN = "avg_score_wen";//文科平均分
    public static final String KEY_SAFE_RATIO = "safe_ratio";//录取概率

    public static final String FILE_NAME_DREAM_SCH = "dream_sch.ipin";
    public static final String FILE_NAME_BATCH_SCORE_LINE = "batch_line.ipin";

    public static final String ACTION_INPUT_BASIC_INFO = "input_basic_info";//输入学校基本信息
    public static final String ACTION_ADD_SCORE = "add_score";//输出成绩分数界面
    public static final String ACTION_INPUT_SCH_INFO = "input_sch_info";//输入学校本科重本率信息

    public static final String ACTION_SWITCH_FILTER_TAB = "com.gaokaozhiyuan.action.switch2filter_tab";//切换到挑选tab的广播action


    public static final String VALUE_DIPLOMA_BK = "bk";//本科
    public static final String VALUE_DIPLOMA_ZK = "zk";//专科
    public static final String VALUE_DIPLOMA_BK_STR = "本科";//本科
    public static final String VALUE_DIPLOMA_ZK_STR = "专科";//专科
    public static final String VALUE_FILTER_TYPE_NUM = "num";//num
    public static final String VALUE_FILTER_TYPE_SCH = "sch";//num
    public static final String VALUE_FILTER_TYPE_MAJOR = "major";//num
    public static final String VALUE_CP_TYPE_SIMPLE = "simple";
    public static final String VALUE_SORT_TOUCH_RATIO = "touch_ratio";//录取概率
    public static final String VALUE_SORT_TOTAL_RANK = "total_rank";//综合排名
    public static final String VALUE_SORT_SALARY_RATIO = "salary_ratio";//薪资
    public static final String VALUE_SORT_SALARY5 = "salary5";//五年薪资
    public static final String VALUE_SORT_MATCH_RATIO = "match_ratio";//专业匹配度
    public static final String VALUE_SORT_FEMALE_RATIO = "female_ratio";//男女比例
    public static final String VALUE_ORDERBY_DESC = "desc";//降序
    public static final String VALUE_ORDERBY_ASC = "asc";//升序
    public static final String VALUE_COLLECTION_SCHOOL = "sch";//学校
    public static final String VALUE_COLLECTION_MAJOR = "major";//专业
    public static final String VALUE_COLLECTION_VOLUNTEER = "schmaj";//志愿
    public static final String VALUE_COLLECTION_PROFESSION = "zhineng";//职能
    public static final String VALUE_COLLECTION_PAPER = "paper";//题库
    public static final String VALUE_COLLECTION_ZYB = "zyb";//志愿表
    public static final String VALUE_PAGE_DETAIL_TO_EMPLOYMENT = "page_detail_to_employment";//进入详情里面的就业碎片

    public static final String VALUE_HOME_V4 = "home";
    public static final String VALUE_ME_PAGE = "me";
    public static final String VALUE_HOME_V6 = "home";

    public static final String INTENT_TO = "intent_to";
    public static final String INTENT_FROM = "intent_from";
    public static final String INTENT_SHOW = "intent_show";
    public static final String INTENT_UMENG = "intent_umeng";


    public static final String BMTI_E = "e";
    public static final String BMTI_I = "i";
    public static final String BMTI_N = "n";
    public static final String BMTI_S = "s";
    public static final String BMTI_F = "f";
    public static final String BMTI_T = "t";
    public static final String BMTI_J = "j";
    public static final String BMTI_P = "p";

    //职业模块
    public static final String KEY_ZHI_NENG_ID = "zhineng_id";//职能id
    public static final String KEY_ZHI_NENG_LIST = "zhineng_list";//职能列表id

    public static final String KEY_ZHINENG_LIST_TYPE = "zhineng_list_type";//职能列表的类型
    public static final String KEY_QUARY_CAREER_TYPE = "quary_career_type";
    public static final String VALUE_QUARY_CAREER_TYPE_ALL = "all";
    public static final String VALUE_QUARY_CAREER_TYPE_MARK = "mark";
    public static final String KEY_ZHI_NENG_TYPE = "zhineng_type";
    public static final String KEY_INDUSTARY = "industry";
    public static final String KEY_REAL_INDUSTARY = "real_industry";

    //智能分析等级
    public static final String VALUE_ANALYSE_LEVEL_UNKNOW = "unknown";
    public static final String VALUE_ANALYSE_LEVEL_CAREFUL = "careful";
    public static final String VALUE_ANALYSE_LEVEL_CONSIDER = "consider";
    public static final String VALUE_ANALYSE_LEVEL_RECOMMEND = "recommend";


    //前景等级
    public static final String VALUE_PROSPECT_LEVEL_TOP = "top";
    public static final String VALUE_PROSPECT_LEVEL_HEIGHT = "height";
    public static final String VALUE_PROSPECT_LEVEL_GENERAL = "general";
    public static final String VALUE_PROSPECT_LEVEL_LOW = "low";
    public static final String VALUE_PROSPECT_LEVEL_VERY_LOW = "very_low";
    public static final String VALUE_PROSPECT_LEVEL_UNKOWN = "unknown";
    public static final String APP_ID = "wxb4ba3c02aa476ea1";

    //活动页操作类型
    public static final String ACTIVITY_OPERATE_TYPE_H5 = "h5";

    //活动展示类型
    public static final String SHOW_TYPE_ALL = "all";
    public static final String SHOW_TYPE_UNBIND = "unbind";

    //志愿表
    public static final String KEY_APPLICATION_FORM_TYPE = "application_form_type";//志愿表类型
    public static final int VALUE_APPLICATION_FORM_TYPE_EMPTY = 0;//志愿表详情页面类型--空表
    public static final int VALUE_APPLICATION_FORM_TYPE_UNEMPTY = 1;//志愿表详情页面类型--
    public static final int VALUE_APPLICATION_FORM_TYPE_INTELLIGENT = 2;//--智能生成

    public static final String KEY_APPPLICATION_FORM_ID = "application_form_id";//志愿表id
    public static final String KEY_APPLICATION = "application";//志愿
    public static final String KEY_MAJOR_COUNT = "major_count";//专业数量
    public static final String KEY_APPLICATION_FORM_NAME = "application_form_name";//志愿表名称
    public static final String KEY_APPLICATION_FORM_REMARKS = "application_form_remarks";//志愿表名称
    public static final int VALUE_TYPE_ADDMAJOR = 1;
    public static final int VALUE_TYPE_ADDSCH = 2;

    public static final String KEY_APPLICAITON_PRIORITY = "type";
    public static final String TYPE_APPLICAITON_PRIORITY_MAJ = "major";
    public static final String TYPE_APPLICAITON_PRIORITY_SCH = "sch";
    public static final String KEY_ADJUST = "adjust";
    public static final String KEY_MD5 = "a0dd6d9f531167be";//智能生成志愿表MD5的值
    public static final String KEY_IS_FIRST_GENEATE = "is_first_geneate";//第一次自动生成志愿表;
    public static final String KEY_POSITION = "position";
    public static final String KEY_LOC_PROVICE = "loc_province";//第一次自动生成志愿表;
    public static final String KEY_LOC_CITY = "loc_city";
    public static final String KEY_SHOW_TYPE = "show_type";//志愿表展示类型

    //江苏版本科目
    public static final String KEY_SUBJECT_PHYSICS = "physics";
    public static final String KEY_SUBJECT_BIOLOGY = "biology";
    public static final String KEY_SUBJECT_CHEMISTRY = "chemistry";
    public static final String KEY_SUBJECT_GEOGRAPHY = "geography";
    public static final String KEY_SUBJECT_POLITICS = "politics";
    public static final String KEY_SUBJECT_HISTORY = "history";
    public static final String KEY_OPT_SELECT_LEVEL = "opt_select_level";
    public static final String KEY_REQ_SELECT_LEVEL = "req_select_level";
    public static final String KEY_ARTICLE_ID = "article_id";

    //成绩
    public final static String KEY_YSY_SCORE = "ysy_score";//400（语数英）
    public final static String KEY_ZH_SCORE = "zh_score";//250（综合）
    public final static String KEY_ZX_SCORE = "zx_score";//55（自选模块）
    public final static String KEY_JS_SCORE = "js_score";//88（技术）
    public final static String KEY_SELECT_COURSE = "zj_opt_course";//浙江所选科目

    public static final String KEY_UID = "uid";//用户id
    public static final String KEY_USERNAME = "username";//用户名
    public static final String KEY_ARTICLE_TITLE = "article_title";

    //用户输入分数的类型
    public static final int INPUT_TYPE_SCORE = 0;//分数
    public static final int INPUT_TYPE_RANK = 1;//排名
    public static final int INPUT_TYPE_DIFF = 2;//线差
    public static final String KEY_IMAGE_URLS = "image_urls";

    public static final String VALUE_ACTIVITY_EMPLOY_ANALYSE = "value_activity_employ_analyse";


    //位置相关
    public static final String REGION_HUABEI = "华北";
    public static final String REGION_HUABEI_STR = "region_huabei";

    public static final String REGION_DONGBEI = "东北";
    public static final String REGION_DONGBEI_STR = "region_dongbei";

    public static final String REGION_HUADONG = "华东";
    public static final String REGION_HUADONG_STR = "region_huadong";

    public static final String REGION_HUAZHONG = "region_huazhong";
    public static final String REGION_HUAZHONG_STR = "华中";

    public static final String REGION_HUANAN = "region_huanan";
    public static final String REGION_HUANAN_STR = "华南";

    public static final String REGION_XINAN = "region_xinan";
    public static final String REGION_XINAN_STR = "西南";

    public static final String REGION_XIBEI = "region_xibei";
    public static final String REGION_XIBEI_STR = "西北";

    public static final String DIFFICULT_CHONG = "chong"; //冲
    public static final String DIFFICULT_WEN = "wen";//稳
    public static final String DIFFICULT_BAO = "bao";//保
    public static final String DIFFICULT_IMPOSSIBLE = "impossible";//难
    public static final String DIFFICULT_INCALCULABLE ="incalculable";//无法计算

    // 本专科数值类型
    public static final int EDU_WMZY_DIPLOMA_BK = 7;
    public static final int EDU_WMZY_DIPLOMA_ZK = 5;

    // 批次类型对应数据库值
    public static final int EDU_WMZY_BATCH_BK_1 = 1;
    public static final int EDU_WMZY_BATCH_BK_2 = 2;
    public static final int EDU_WMZY_BATCH_BK_2A = 3;
    public static final int EDU_WMZY_BATCH_BK_2B = 4;
    public static final int EDU_WMZY_BATCH_BK_3 = 5;
    public static final int EDU_WMZY_BATCH_BK_3A = 6;
    public static final int EDU_WMZY_BATCH_BK_3B = 7;
    public static final int EDU_WMZY_BATCH_ZK_1 = 8;
    public static final int EDU_WMZY_BATCH_ZK_2 = 9;
    public static final int EDU_WMZY_BATCH_PRE = 101;

    // 批次对应是字符串表示
    public static final String EDU_WMZY_BATCH_BK_1_STR = "bk1";
    public static final String EDU_WMZY_BATCH_BK_2_STR = "bk2";
    public static final String EDU_WMZY_BATCH_BK_2A_STR = "bk2a";
    public static final String EDU_WMZY_BATCH_BK_2B_STR = "bk2b";
    public static final String EDU_WMZY_BATCH_BK_3_STR = "bk3";
    public static final String EDU_WMZY_BATCH_BK_3A_STR = "bk3a";
    public static final String EDU_WMZY_BATCH_BK_3B_STR = "bk3b";
    public static final String EDU_WMZY_BATCH_ZK_1_STR = "zk1";
    public static final String EDU_WMZY_BATCH_ZK_2_STR = "zk2";
    public static final String EDU_WMZY_BATCH_PRE_STR = "";

    //没有综合排名默认显示
    public static final String EDU_WMZY_IPIN_RANK_DEFALUT = "--";
    private static final List<String> NEW_POLICY_PROVINCE_ARRAY = Arrays.asList("330000000000");
    public static final HashSet<String> NEW_POLICY_PROVINCE_SET = new HashSet<String>(NEW_POLICY_PROVINCE_ARRAY);

    //所有合法批次集合
    public static final HashSet<Integer> ALL_BATCH_SET = new HashSet<Integer>(Arrays.asList(EDU_WMZY_BATCH_BK_1,
            EDU_WMZY_BATCH_BK_2, EDU_WMZY_BATCH_BK_2A, EDU_WMZY_BATCH_BK_2B, EDU_WMZY_BATCH_BK_3,
            EDU_WMZY_BATCH_BK_3A, EDU_WMZY_BATCH_BK_3B, EDU_WMZY_BATCH_ZK_1, EDU_WMZY_BATCH_ZK_2));

    //所有本专科集合
    public static final HashSet<Integer> ALL_DIPLOMA_SET = new HashSet<>(Arrays.asList(EDU_WMZY_DIPLOMA_BK,EDU_WMZY_DIPLOMA_ZK));

    //公共全局
    public static final String NO_DATA_DEFAULT_DISPLAY = "--";
//    public static final String NO_SAFE_RATIO_DEFAULT_DISPLAY = "无法预测";

    //志愿表 KEY
    public static final String ZYB_ID = "zyb_id";
    public static final String ZYB_SHOW_TYPE = "show_type";
    public static final String ZYB_NAME = "zyb_name";
    public static final String ZYB_COUNT = "zyb_coount";

    public static final String SCORE_TYPE_RANK = "rank";
    public static final String SCORE_TYPE_SCORE = "score";
    public static final String ZY_BATCH = "zy_batch";


    public static final int ZYB_SHOW_TYPE_XZ = 1;
    public static final int ZYB_SHOW_TYPE_DEFALUT = 0;

    public static final int PAGE_DEFALUT = 1;
    public static final int PAGE_LEN = 20;

}
