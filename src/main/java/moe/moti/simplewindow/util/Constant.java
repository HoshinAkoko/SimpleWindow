package moe.moti.simplewindow.util;

public class Constant {
    /** 菜单和按钮 */
    public static final String GUIDANCE = "点击任意按钮显示简介，再次点击开启对应功能库啵。";
    public static final String BUTTON_1 = "群峦打造";
    public static final String FUNCTION_1 = "用于精确计算群峦在铁砧上完美打造任意产物的步骤。";
    public static final String TITLE_1 = "群峦打铁计算器";
    public static final String BUTTON_2 = "数据库模拟";
    public static final String FUNCTION_2 = "用于在测试数据库中生成模拟数据。";
    public static final String TITLE_2 = "模拟数据小工具";
    public static final String BUTTON_3 = "发票工具";
    public static final String FUNCTION_3 = "用于查询和补开诺诺全电数票。";
    public static final String TITLE_3 = "查询补开全电数票";

    /**************************************** 群峦计算器 ****************************************/

    /** 性能限制 BFS广搜最大节点数 */
    public static final int MEMORY_LIMIT = 100000000;

    /** 群峦计算器，按钮文本 */
    public static final String[] TERRA_CAL_BUTTON = {"牵拉", "重击", "击打", "轻击", "冲压", "弯曲", "镦锻", "收缩"};
    public static final int[] TERRA_CAL_VALUE = {-15, -9, -6, -3, 2, 7, 13, 16};
    public static final int[] TERRA_CAL_SORT = {3, 2, 4, 5, 1, 0, 6, 7};
    /** 群峦计算器，剪枝条件 */
    public static final int TERRA_CAL_CUT_MAX_OFFSET = 100; //偏离最大值

    /**************************************** 数据库数据模拟工具 ****************************************/

    /** 数据库测试数据生成，按钮文本 */
    public static final String[] SQL_DATA_CREATOR_BUTTON = {"测试车组转端", "魔盒车组转端", "待定占位按钮", "生成临停数据", "生成内停数据", "生成车位记录"};
    public static final String[] SQL_DATA_OPTION_RADIO = {"AIKK客户端", "EC20客户端"};
    public static final String[] SQL_DATA_OPTION_CHECKBOX = {"随机进出时间", "多选按钮"};

    /** 生成测试数据常量 */
    public static final String SQL_DATA_AIKK_ID_PLG = "A3473C94258A4773BBE89A52456247BE";
    public static final String SQL_DATA_AIKK_ID_PL = "12F593583FFB43598948377ACA03AD3B";
    public static final String SQL_DATA_AIKK_ID_PLGA = "A384B76BBE55443788035B1874F31C72";
    public static final String SQL_DATA_AIKK_ID_LA_IN = "4AFEF7EEF42345FAB7CE776660132CFA";
    public static final String SQL_DATA_AIKK_ID_LA_OUT = "A807D40A863E42029342D1E493F4ABF0";
    public static final String SQL_DATA_AIKK_DEVICE_IN = "94:33:00:2D:00";
    public static final String SQL_DATA_AIKK_DEVICE_OUT = "93:72:00:B9:00";
    public static final String SQL_DATA_AIKK_TABLE_TAG = "_05";

    public static final String SQL_DATA_EC20_ID_PLG = "D7CDD74D37B942548AB608B8FB600EF7";
    public static final String SQL_DATA_EC20_ID_PL = "A9F276BC04E640058BADA451B0DC40EB";
    public static final String SQL_DATA_EC20_ID_PLGA = "2C648B974BF1499DBD5F2B5E617C57D7";
    public static final String SQL_DATA_EC20_ID_LA_IN = "3F0F4EE374824C569B074704019F7003";
    public static final String SQL_DATA_EC20_ID_LA_OUT = "B4181691BB3F4BF68949E9AB0FC370CB";
    public static final String SQL_DATA_EC20_DEVICE_IN = "97:24:00:02:02";
    public static final String SQL_DATA_EC20_DEVICE_OUT = "95:24:00:04:00";
    public static final String SQL_DATA_EC20_TABLE_TAG = "_03";

    public static final int SQL_DATA_CREATED_CHANNEL_INT_AIKK = 0;
    public static final int SQL_DATA_CREATED_CHANNEL_INT_EC20 = 1;
    public static final String SQL_DATA_LP_AREA_DEFAULT_VALUE = "请输入车牌";
    public static final String SQL_DATA_LP_LABEL = "车牌：";
    public static final String SQL_DATA_NUMBER_LABEL = "生成数量：";

    /**************************************** 查票补票工具 ****************************************/

    public static final int INVO_HELPER_TEXT_AREA_DEFAULT_WIDTH = 100;
    public static final int INVO_HELPER_TEXT_AREA_DEFAULT_HEIGHT = 24;

    /** 发票工具，按钮文本 */
    public static final String[] INVO_HELPER_BUTTON = {"查询", "查补", "补开", "重开", "删除", "待定"};
//    public static final String INVO_HELPER_PRESET_LABEL = "数票参数预设";
//    public static final String[] INVO_HELPER_PARAM_PRESET = {"稻谷系", "测试用"};
//    public static final Map<String, String[]> INVO_HELPER_PARAM_PRESET_MAP;
//    static {
//        INVO_HELPER_PARAM_PRESET_MAP = new HashMap<>();
//        INVO_HELPER_PARAM_PRESET_MAP.put("稻谷系", new String[]{"tax", "APP_KEY", "APP_SECRET", "S_token"});
//        INVO_HELPER_PARAM_PRESET_MAP.put("测试用", new String[]{"tax", "APP_KEY", "APP_SECRET", "S_token"});
//    }
    public static final String[] INVO_HELPER_PARAM_CONF_KEY = {"mail", "price", "pId", "plateNum", "title", "tNo", "category", "categoryInt", "personal", "remark"};
    public static final String[] INVO_HELPER_PARAM_CONF_STR = {"mail", "price", "pId", "plateNum", "title", "tNo", "category", "categoryInt", "personal", "remark"};
    public static final String[] INVO_HELPER_PARAM_CONF_DEFAULT = {""};
    public static final String[] INVO_HELPER_QUERY_PARAM_KEY = {"remark", "mail", "pId", "title", "tNo", "personal", "categoryInt", "category", "plateNum", "price"};
    public static final String[] INVO_HELPER_QUERY_PARAM_STR = {"remark", "mail", "pId", "title", "tNo", "personal", "categoryInt", "category", "plateNum", "price"};
    public static final String[] INVO_HELPER_QUERY_PARAM_DEFAULT = {};
    public static final int INVO_HELPER_QUERY_SPLIT = 1;

    public static final String INVO_HELPER_ORDER_NO_LABEL = "查询订单号";
    public static final String INVO_HELPER_ORDER_NO_DEFAULT = "";
    public static final String INVO_HELPER_TABLE_NAME_LABEL = "订单表名";
    public static final String INVO_HELPER_TABLE_NAME_DEFAULT = "pay_order";
    public static final String INVO_HELPER_SUB_TAG_LABEL = "补开子标签";
    public static final String INVO_HELPER_SUB_TAG_DEFAULT = "SUB";
    public static final String INVO_HELPER_RAW_PARAM_LABEL = "原始参数";
    public static final String INVO_HELPER_RAW_PARAM_DEFAULT = "";



}
