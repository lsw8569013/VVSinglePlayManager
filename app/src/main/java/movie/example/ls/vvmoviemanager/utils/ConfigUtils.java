package movie.example.ls.vvmoviemanager.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;



import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import movie.example.ls.vvmoviemanager.application.BaseApplication;


/**
 * 全局网络常量中心
 */
public class ConfigUtils {

    private static final String TAG = "ConfigUtils";

    public static final String DB_NAME = "FlyDB.db";

    public static final String APP_STATE_FORE = "app_state_fore";

    public static final String BASE_URL = "https://api2.tumbo.com.cn/v1/";
    public static final String HOST_NAME = "im";
    public static final String SERVICE_NAME = "im";
    public static final String HOST = "im.tumbo.com.cn";

    public static final int PORT = 5222;
    public static final String PHONE_TYPE = "ANDROID";

    public static final String CER_FLY = "-----BEGIN CERTIFICATE-----\n" +
            "MIIFfDCCBGSgAwIBAgIQdZ6uya2U6W21q5LUqY1sxTANBgkqhkiG9w0BAQsFADCB\n" +
            "lDELMAkGA1UEBhMCVVMxHTAbBgNVBAoTFFN5bWFudGVjIENvcnBvcmF0aW9uMR8w\n" +
            "HQYDVQQLExZTeW1hbnRlYyBUcnVzdCBOZXR3b3JrMR0wGwYDVQQLExREb21haW4g\n" +
            "VmFsaWRhdGVkIFNTTDEmMCQGA1UEAxMdU3ltYW50ZWMgQmFzaWMgRFYgU1NMIENB\n" +
            "IC0gRzEwHhcNMTcwMjA3MDAwMDAwWhcNMTgwMjA3MjM1OTU5WjAcMRowGAYDVQQD\n" +
            "DBFhcGkyLnR1bWJvLmNvbS5jbjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoC\n" +
            "ggEBAKSbD7lv2mYTDRt0GiHOo1WzcTHtuerx6BN1AYD7B6uFLfkgfrK7dLE3C1gD\n" +
            "cZkUDy298KSQrZmnBuJVkcUn8Cb16AjNadq8vn30pitXTqcrps72PZ+b/fe89kIr\n" +
            "KcQZ7sIU/gveOfHWqlWjVT8+EQ+XJOLK4q6Y0IFITxhX7RxRks+4jZR0RUkc/CCB\n" +
            "28UQ57octAmvyQ1Q1kJGfmt5KtXWmeiLHxNpXB+HBjFGt244KSrfY79PyFTkJ+zZ\n" +
            "40225ptnfske/M1nfe6x/NZw+wX4amJ9cGyjF4cwUXZC8oPxXcwvNpweluliM9UL\n" +
            "z0TMpYMNR3g1VzB9q7KXAs63lnECAwEAAaOCAj8wggI7MBwGA1UdEQQVMBOCEWFw\n" +
            "aTIudHVtYm8uY29tLmNuMAkGA1UdEwQCMAAwYQYDVR0gBFowWDBWBgZngQwBAgEw\n" +
            "TDAjBggrBgEFBQcCARYXaHR0cHM6Ly9kLnN5bWNiLmNvbS9jcHMwJQYIKwYBBQUH\n" +
            "AgIwGQwXaHR0cHM6Ly9kLnN5bWNiLmNvbS9ycGEwHwYDVR0jBBgwFoAUXGGesHZB\n" +
            "qWqqQwvhx24wKW6xzTYwDgYDVR0PAQH/BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUF\n" +
            "BwMBBggrBgEFBQcDAjBXBggrBgEFBQcBAQRLMEkwHwYIKwYBBQUHMAGGE2h0dHA6\n" +
            "Ly9oYy5zeW1jZC5jb20wJgYIKwYBBQUHMAKGGmh0dHA6Ly9oYy5zeW1jYi5jb20v\n" +
            "aGMuY3J0MIIBAgYKKwYBBAHWeQIEAgSB8wSB8ADuAHUA3esdK3oNT6Ygi4GtgWhw\n" +
            "fi6OnQHVXIiNPRHEzbbsvswAAAFaGCqYzAAABAMARjBEAiBso8O/SVerJPQcFsVv\n" +
            "n2aX7B+049HU2P0UPNeb3rulNgIgQe5Yh1vcgvDYzkET2xhQU7Uq0YU1g5JPM7FS\n" +
            "TA1sXwQAdQCkuQmQtBhYFIe7E6LMZ3AKPDWYBPkb37jjd80OyA3cEAAAAVoYKpkK\n" +
            "AAAEAwBGMEQCIF4h9EfPGOv9eCluBWlPcYOs3vVYoE7Y/nuolm3UbarTAiAaUDxZ\n" +
            "DAZqCdzMEokSM6Pli/pAOF64FaTw0AdjZGrNOzANBgkqhkiG9w0BAQsFAAOCAQEA\n" +
            "eVRBzl1+QOPqJN6myC+gQD/tgpzBkat0x6C6nYByNgDR8WaUYrvIIJVoXRcWb6Nw\n" +
            "B4nae0l2oz32tACtB1w1P1Iko6qYeKSxqfCArn4QwYvZvluYm/Gnbuq8ZY8BuaiZ\n" +
            "oAsaRzgfuN60vlbbx2zPeT/X/sgYnig3fGiQWn5jy4pcs7SQ6zqws4QK0wvtSDjt\n" +
            "j1xn3jY8Gbty/K8rHHD2Hzi4/MeNlR0qC4XIH+Nm0OTWhWzOcvDE5qe/r+bf7tM1\n" +
            "VarcJf/qkHyQjHu1EXQoAHRVGiC8Xk5vTFGXWlY0HdcsM971UmOqV5H2NA6KVMla\n" +
            "Wr2UMu+QMK0pwNl1DT92Ew==\n" +
            "-----END CERTIFICATE-----";

    //广播action定义
    public static final String ERROR_LOGIN_ACTION = "com.flyscreen.smack.error.login";
    public static final String RECONN_IM_ACTION = "com.flyscreen.smack.reconn.IM";
    public static final String NETWORK_ERROR_ACTION = "com.flyscreen.network.";
    public static final String SMACK_SERVICE_ACTION = "com.flyscreen.smack.service";
    public static final String PROCESS_SERVICE_ACTION = "com.flyscreen.process.service";
    public static final String RECEIVER_MESSAGE_ACTION = "com.flyscreen.process.service.message.receiver";
    public static final String USER_SCREEN_UNLOCK = "com.flyscreen.process.service.message.screen_tranon";
    public static final String SMACK_DELMYSELF_ACTION = "com.flyscreen.process.service.del_myself";

    public static final String APP_BACKGROUND_ACTION = "com.flyscreen.app.background";
    public static final String APP_FOREGROUND_ACTION = "com.flyscreen.app.foreground";

    public static final String IM_CONNECT_ACTION = "com.flyscreen.app.im.success.connect";

    //    类型区分
    public static final int SINGLE_CHAT = 1;
    public static final int GROUP_CHAT = 2;
    public static final int SYSTEM_CHAT = 3;
    public static final String RED_RANDOM = "RANDOM";
    public static final String RED_FIXED = "FIXED";

    // 表情类型区分
    public static final int ICON_ADD = 0;
    public static final int ICON_GAME = 1;
    public static final int ICON_LOVES = 2;
    public static final int ICON_SMALL = 3;
    public static final int ICON_LARAGE = 4;

    public static final int ICON_PARENT = 5;
    //消息的状态
    public static final int MESSAGE_FAILURE = 1;
    public static final int MESSAGE_SENDING = 2;
    public static final int MESSAGE_COMPLETE = 3;
    public static final int MESSAGE_UNREAD = 4;
    public static final int MESSAGE_READED = 5;

    // SharedPreference存储key
    public static final String MINE_USER_INFO = "mine_user_info" + BaseApplication.getUserID();
    // 配置cache文件名称
    public static final String CACHE_NAME = "flyclip_cache";

    public static final String LOVE_TYPE_MOMENT = "MOMENT";
    public static final String LOVE_TYPE_IMAGE = "IMAGE";
    public static final String LOVE_TYPE_VIDEO = "VIDEO";
    public static final String LOVE_TYPE_FACE = "FACE";

    public static final String GROUP_OPERATE_ADD = "INVITE_MEMBER";
    public static final String GROUP_OPERATE_DELETE = "KICK_MEMBER";
    public static final String C_DATACACHE = "C_DATACACHE";
    public static final String C_DATACACHE_HEAD = "C_DATACACHE_HEAD";
    public static final String C_DATACACHE_HEAD_IMAGE = "C_DATACACHE_HEAD_IMAGE";
    public static final String HOT_CACHE = "HOT_CACHE";
    public static final String NEW_CACHE = "NEW_CACHE";


    /**
     * 生成时间格式
     *
     * @return
     */
    public static SimpleDateFormat getDataFormat() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        return DATE_FORMAT;
    }

    /**
     * 解析获取时间格式
     *
     * @return
     */
    public static SimpleDateFormat getReceiverDataFormat() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
        );
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        return DATE_FORMAT;
    }

    /**
     * 用于获取软件内声音key
     */
    public static final String SETTING_SOUND = "setting_sound";
    /**
     * 用于获取软件内单人或全部人聊天背景key
     */
    public static final String ALLBACKGROUND = "allBackground";
    //七牛文件下载基路径
    public static final String QINIU_DOWNLOADD = "http://ofpdqsygx.bkt.clouddn.com/";
    public static final String QINIU_THUMBURL_IMAGE = "?imageView2/2/w/200/h/200/interlace/1";
    public static final String QINIU_THUMBURL_IMAGE_SMALL = "?imageView2/2/w/100/h/100/interlace/1";
    public static final String QINIU_IMAGE_INFO = "?imageInfo";
    public static final String QINIU_THUMBURL_VIDEO = "?vframe/jpg/offset/0/w/380/h/260";
    public static final String HEADER_PROTRAIT = "?imageView2/2/w/200";
    public static final String HEADER_IMAGESLIM = "?imageslim";
    //对方没有注册飞屏账号，可以发送邀请
    public static final String INVITE = "INVITE";
    // 对方有飞屏账号,可直接发送消息
    public static final String ALREADY_FRIEND = "FRIEND";


    public static final String CHAT_CLASS_SHAOR_NAME = ".activity.chat.ChatRoomActivity";
    public static final String CHAT_SETTING_CLASS_SHAOR_NAME = ".activity.chat.ChatSettingActivity";
    public static final String CHAT_CREATE_CLASS_SHAOR_NAME = ".activity.chat.ChatGroupCreateActivity";
    public static final String VIDEO_LIST_CLASS_SHAOR_NAME = ".activity.search.VideoListAcy";
    public static final String VIDEO_ONE_CLASS_SHAOR_NAME = ".activity.search.MomentListAcy";
    public static final String MAIN_CLASS_SHAOR_NAME = ".activity.MainActivity";

    public static final String NOTFRIEND = "NOTFRIEND";
    public static final String NORMAL = "NORMAL";
    public static final String BLOCK = "BLOCK";
    public static final String MYSELF = "MYSELF";


    public static final String ACCEPT = "ACCEPT";
    public static final String REQUEST = "REQUEST";
    public static final String REJECT = "REJECT";

    public static final String GAME_SYSTEM = "GAME_SYSTEM";
    public static final String GAME_INVITE = "GAME_INVITE";

    public static final int REQUEST_CODE = 100;
    public static final int ACCEPT_AND_REJECT_CODE = 101;

    public static final String FOLLOWS = "FOLLOWS";
    public static final String HAD_FOLLOWS = "HAD_FOLLOWS";

    public static final int GROUPHOLDER = 1;//群主
    public static final int GROUP_USER = 2;//群成员

    public static final int NEW_MESSAGE_TRANSFORM = 1;
    public static final int NEW_MESSAGE_SYSTEM = 2;
    public static final int NEW_MESSAGE_FRIEND = 3;

    public static final String MEMBER = "MEMBER";
    public static final String ADMIN = "ADMIN";
    public static final String OWNER = "OWNER";

    public static final int PAY_RESULT_SUCCESS = 0;
    public static final int PAY_RESULT_CONFIRM = 1;
    public static final int PAY_RESULT_FAILED = 2;

    //通知类型（FOLLOW 关注通知, LIKE 赞通知, COMMENT 评论通知, REDPACKET 红包通知, FRIEND 添加好友消息）
    public static final String NOTICE_TYPE_FOLLOW = "FOLLOW";
    public static final String NOTICE_TYPE_LIKE = "LIKE";
    public static final String NOTICE_TYPE_COMMENT = "COMMENT";
    public static final String NOTICE_TYPE_FRIEND = "FRIEND";

    //评论类型 (LIKE, TEXT_COMMENT, REDPACKET_COMMENT, MEDIA_COMMENT, EMOTICON_COMMENT)
    public static final String COMMENT_TYPE_LIKE = "LIKE";
    public static final String COMMENT_TYPE_TEXT_COMMENT = "TEXT_COMMENT";
    public static final String COMMENT_TYPE_REDPACKET_COMMENT = "REDPACKET_COMMENT";
    public static final String COMMENT_TYPE_MEDIA_COMMENT = "MEDIA_COMMENT";
    public static final String COMMENT_TYPE_EMOTICON_COMMENT = "EMOTICON_COMMENT";

    // FRIEND_REQUEST:添加好友请求
    public static final String PUSH_FRIEND_REQUEST = "FRIEND_REQUEST";
    // FRIEND_REQUEST_ACCEPT:接受好友请求
    public static final String PUSH_FRIEND_REQUEST_ACCEPT = "FRIEND_REQUEST_ACCEPT";
    // ADD_COMMENT:新的点赞或评论
    public static final String PUSH_ADD_COMMENT = "ADD_COMMENT";
    // DELETE_COMMENT:删除点赞或评论
    public static final String PUSH_DELETE_COMMENT = "DELETE_COMMENT";
    // OFFLINE_IM:聊天消息推送
    public static final String PUSH_OFFLINE_IM = "OFFLINE_IM";
    // FOLLOW:有新的关注消息
    public static final String PUSH_FOLLOW_COMMENT = "FOLLOW";
    // UNFOLLOW:取消关注
    public static final String PUSH_UNFOLLOW_COMMENT = "UNFOLLOW";

    // 个人中心瞬间跳转类型,
    public static final int TYPE_MIMEFRAGMENT = 112;
    // 个人中心点赞和消息跳转查看
    public static final int TYPE_MESSAGELIST_AND_PRAISELIST = 113;

    public static final String IMAGESLIM = "?imageMogr2/thumbnail/!60p";// "imageslim"

    public static final String[] DESTROY_TIME = {
            "OFF",
            "3S",
            "4S",
            "5S",
            "6S",
            "7S",
            "8S",
            "9S",
            "10S",
            "15S",
            "30S",
            "1M",
            "2M",
            "3M",
            "5M"
    };

    public static int getEntryPos(String str){
        if(TextUtils.isEmpty(str)){
            return 0;
        }
        List<String> stringList = Arrays.asList(ConfigUtils.DESTROY_TIME);
        if(stringList.contains(str)){
            return stringList.indexOf(str);
        }else{
            return 0;
        }
    }

    // 邀请好友入群
    public static final int GROUP_CHAT_INVITE_FRIEND = 10;
    // 移除好友出群
    public static final int GROUP_CHAT_REMOVE_FRIEND = 11;
    // 群全部好友
    public static final int GROUP_CHAT_ALL_FRIEND = 12;

    // 用于判断是否同步过通讯录
    public static final String IS_SYNCHRONIZATION_MOBILE = "IS_SYNCHRONIZATION_MOBILE";

    /**
     * 返回毫秒
     *
     * @param s
     * @return
     */
    public static long getDestroyTime(String s) {

        if (TextUtils.isEmpty(s)) {
            return 0;
        }

        long time = 0;

        switch (s) {
            case "OFF":
                break;
            case "3S":
                time = 3 * 1000;
                break;
            case "4S":
                time = 4 * 1000;
                break;
            case "5S":
                time = 5 * 1000;
                break;
            case "6S":
                time = 6 * 1000;
                break;
            case "7S":
                time = 7 * 1000;
                break;
            case "8S":
                time = 8 * 1000;
                break;
            case "9S":
                time = 9 * 1000;
                break;
            case "10S":
                time = 10 * 1000;
                break;
            case "15S":
                time = 15 * 1000;
                break;
            case "30S":
                time = 30 * 1000;
                break;
            case "1M":
                time = 60 * 1000;
                break;
            case "2M":
                time = 2 * 60 * 1000;
                break;
            case "3M":
                time = 3 * 60 * 1000;
                break;
            case "5M":
                time = 5 * 60 * 1000;
                break;
//            case "1H":
//                time = 60 * 60 * 1000;
//                break;
//            case "1D":
//                time = 24 * 60 * 60 * 1000;
//                break;
//            case "1W":
//                time = 7 * 24 * 60 * 60 * 1000;
//                break;
        }

        return time;

    }

    public static void checkErrorLogin(Context context) {
        if (TextUtils.isEmpty(BaseApplication.getUserID()) ||
                TextUtils.isEmpty(BaseApplication.getToken())) {
            LocalBroadcastUtils.send(context, new Intent(ConfigUtils.ERROR_LOGIN_ACTION));
        }
    }

    /**
     * 返回销毁时间字符串
     *
     * @param time
     * @return
     */
    public static String getDestroyTimeShow(long time) {

        StringBuilder stringBuilder = new StringBuilder();

        if (time > 0) {

            int weekSecond = 7 * 24 * 60 * 60 * 1000;
            int daySecond = 24 * 60 * 60 * 1000;
            int hourSecond = 60 * 60 * 1000;
            int minuteSecond = 60 * 1000;

//            int weeks = (int) (time / weekSecond);
//            int days = (int) ((time % (weekSecond)) / (daySecond));
//            int hours = (int) (((time % weekSecond % daySecond) / (hourSecond)));
            int minutes = (int) (((time % weekSecond % daySecond % hourSecond) / (minuteSecond)));
            int seconds = (int) ((time % weekSecond % daySecond % hourSecond % minuteSecond) / 1000);

//            if (weeks > 0) {
//                stringBuilder.append(weeks + "W");
//            }
//            if (days > 0) {
//                stringBuilder.append(days + "D");
//            }
//            if (hours > 0) {
//                stringBuilder.append(hours + "H");
//            }
            if (minutes > 0) {
                stringBuilder.append(minutes + "M");
            }
            if (seconds > 0) {
                stringBuilder.append(seconds + "S");
            }

        }

        return stringBuilder.toString();
    }








    /**
     * 输入框 emoji 表情过滤
     */
    public static void ignoreEmojiInput(EditText et) {
        et.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (StringUtils.isEmoji(source + "")) {
                            return "";
                        }
                        return null;
                    }
                }
        });
    }

    public static String getMapUrl(double lat, double lng, int w, int h) {
        String url = "http://restapi.amap.com/v3/staticmap?location=" +
                lng + "," + lat +
                "&zoom=17&size=" +
                w + "*" + h +
                "&markers=mid,,A:" +
                lng + "," + lat +
                "&key=63b387c3cf5ddd7f15abf1cd011b02ea";
        return url;
    }


}
