package movie.example.ls.vvmoviemanager.utils;

import android.Manifest;

/**
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/5/23
 * Email : lsw
 */
public class PermissionsSetUtils {
    //    读写SD卡
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //   相机
    public static String[] CAMERA_PERMISSION = {
            Manifest.permission.CAMERA
    };
    //    定位
    public static final String[] ADDRESS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    //    获取手机设备参数
    public static final String[] PHOTO_STATES = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    //   录音
    public static final String[] AUDIO_PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };
    //   拨打电话
    public static final String[] CALL_PHONE = new String[]{
            Manifest.permission.CALL_PHONE
    };

    // 读取联系人
    public static final String[] PHONE_CONTACTS = new String []{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    // 发送短信 23  22
    public static final String[] SEND_SMS = new String []{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
//            Manifest.permission.WRITE_SMS,
            Manifest.permission.RECEIVE_SMS
    };

}
