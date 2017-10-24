package com.example.wanjian.creditrent.base;

/**
 * Created by hugo on 2015/12/24.
 * 常量类
 */
public class C {


    public static final String SERVICE_ALL = "service_choose_tutor_all";
    public static final String SERVICE_TECHNOLOGY = "service_choose_tutor_technology";
    public static final String SERVICE_NO_TECHNOLOGY = "service_choose_tutor_no_technology";
    /*---------------------------------------User----------------------------------------------*/
//    public static final String USER_NAME = "user_tel_number";
    public static final String BASIC_MESSAGE = "basic_message";

    public static final String USER_ID = "userId";

    // 其他信息
    public static final String LOG_DATE = "log_date";  // 日志文件的日期信息
    public static final String TOPIC_Field = "topic_category";  // 话题社记录用户上次选择的领域信息 一级领域


    public static final String USER_NAME = "username"; //用户账户
    public static final String TRUENAME = "truename"; //真实姓名
    public static final String PASSWORD = "password";
    public static final String VERSION = "version";

    public static final String CITY_ID = "city_id";
    public static final String CAMPUS_ID = "campus_id";
    public static final String TAKING_TYPE_ID = "taking_type_id";
    public static final String TALKING_TYPE_ID = "talking_type_id";
    public static final String SCHOOL_ID = "school_id";

    //创建订单
    public static final String ORDERNAME = "order_name";
    public static final String ORDERNUMBER = "order_number";
    public static final String IS_AUTO_DOWNLOAD = "is_auto_download";

    public static final String IS_FIRST_NO_PASS = "if_tips_no_pass";


    /**
     * 主界面 + 校招咨询详情页
     */
    public static final String XZDS_XZInformation = "xzds/XzInformation";
    public static final String XZDS_XZInformationDetail = "xzds/XzInformation/detail";

   //user
   public static final String XZDS_USER_Collect = "xzds/user/collect";



    /**
     * 个人资料设置
     */
    public static final int EDUCATION = 0;
    public static final int AWARD = 1;
    public static final int WOEK = 2;
    public static final int EXPERIENCE = 3;

    public static final int UPDATE_DO_WILL = 4;
    public static final int UPDATE_CARE_TOPIC = 5;

    /**
     * 分享功能的类别
     */
    public static final int SHARE_QQ = 1;
    public static final int SHARE_WEIBO = 2;
    public static final int SHARE_WECHAT_CIRCLE = 3;
    public static final int SHARE_WECHAT_QZONE = 4;
    public static final int SHARE_WECHAT = 5;

    /**
     * 我的订单状态
     * 未付款的对于（1—下单未付款）；
     * 进行中(2-已经付款，交流还未开始)
     * 待确定（3—交流已结束，，但未确认）；
     * 待评价（4-交流完成，已确认，未评价）
     * 已经完成（5-已评价,状态为完成）
     */
    public static final int UNPAYMENT = 1;
    public static final int UNDERWAY = 2;//
    public static final int UNCONFIRM = 3;
    public static final int UNEVALUATE = 4;
    public static final int FINISH = 5;
    public static final int All = 0;
    public static final int UNPAY = 10;

    /**
     * 普通订单状态
     */
    public static final int NORMAL_ORDER = 101;



    /**
     * 1v1订单状态
     * 0表示听友未支付(下单未支付)，1表示等待说友协商时间（即听友已支付），
     * 2表示说友修改预约信息（听友待确认时间地点），
     * 3表示等待听友确认，4表示等待开始，5表示交流进行中，
     * 6表示待确认时长，7表示待确认付款，8表示待补交超时金额，
     * 9表示已结束，10表示订单取消（作废）
     */

    public static final int S_PAYING = 0;
    public static final int S_PAYED = 1;
    public static final int S_MODIFY_SPEAK = 2;
    public static final int S_MODIFY_LISTENER = 3;
    public static final int S_WAIT_START = 4;
    public static final int S_STARTING = 5;
    public static final int S_CHECK_TIME = 6;
    public static final int S_CONFIRM_PAY = 7;
    public static final int S_PAY_EXTRA = 8;
    public static final int S_END = 9;
    public static final int S_CANCELED = 10;
    public static final int S_ALL = -2;

    /**
     * RxUtils里的常量
     */
    public static final String UNLOGIN = "请登录后重试!";

    /**
     * startActivityForResult 常量
     */
    public static final int BASIC_MSG_COMPLETED = 0;
    public static final int PICK_FROM_CAMERA = 1;
    public static final int CROP_FROM_CAMERA = 2;
    public static final int CROP_FROM_FILE = 3;
    public static final int PICK_FROM_FILE = 4;
    public static final int CHOOSE_CROPED_PICTURE = 5;
    public static final int UPLOAD_PICTURE = 6;
    public static final int UNLOGIN_COMPLETED = 7;
    public static final int PUBLISH_TALKING = 100;


    /**
     * RxBus RxManage 相关
     */
    public static final String LOGOUT = "注销";
    public static final String REFRESH = "刷新";


    /*话题社常量*/
    public static final int TYPE_WITH_PIC = 0;
    public static final int TYPE_NO_PIC = 1;

    public static final int TYPE_COMMENT_REPLY=1;
    public static final int TYPE_COMMENT_COMMENT = 0;
    public static final int TYPE_COMMENT_ANSWER = 2;

    public static final int FROM_TOPIC_QUESTION = 10;
    public static final int FROM_TALKING_PUBLISH = 11;

    public static final int REQUEST_ADD_PIC = 1002;
    /**
     * 选择学校或者学位的领域
     */

    public static final int CHOOSE_SCHOOL = 0X00001;
    public static final int CHOOSE_DEGREE = 0X00002;
    public static final int CHOOSE_RANK = 0X00003;

    /**
     * 判断跳转是修改资料还是增加资料
     */
    public static final int MODIFY_INFO = 0X00003;
    public static final int ADD_INFO = 0X00004;

    /**
     * 是学院还是专业
     */
    public static final int CHOOSE_COLLEGE = 0X00005;
    public static final int CHOOSE_MAJOR = 0X00006;

    /**
     * 监听是否完成输入
     */
    public static final int IS_COMPLETE = 0X00007;

    public static final int PRACTISE_TYPE = 0X00003;
    public static final int WORK_TYPE = 0X00004;

    //消息系统全部清空时间
    public static final String DELETE_TIME = "delete_time";

}
