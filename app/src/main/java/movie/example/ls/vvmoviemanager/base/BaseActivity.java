package movie.example.ls.vvmoviemanager.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import movie.example.ls.vvmoviemanager.ui.PermissionsActivity;
import movie.example.ls.vvmoviemanager.utils.NetWorkUtils;
import movie.example.ls.vvmoviemanager.utils.PermissionsCheckerUtil;
import movie.example.ls.vvmoviemanager.utils.PermissionsSetUtils;
import movie.example.ls.vvmoviemanager.views.CustomProgressDialog;


/**
 * 针对业务 直接基类封装
 * Created By: lsw
 * Author : lsw
 * Date :  2016/10/26
 * Email : lsw
 */
public abstract class BaseActivity extends BaseAppCompatActivity  {

    public boolean mNetworkInitAvailable = false;
    public PermissionsCheckerUtil mPermissionsChecker; // 权限检测器
    public final int PERMISSIONS_GRANTED = 0; // 权限授权
    public final int PERMISSIONS_DENIED = 1; // 权限拒绝
    public final int REQUEST_CODE_PHONESTATE = 0; // 系统参数请求码
    public final int REQUEST_CODE_ADDRESS = 1; // 定位请求码
    public final int REQUEST_CODE_SDCARD = 2; // 读写SD卡请求码
    public final int REQUEST_CODE_CAMMER = 3; // 拍照
    public final int REQUEST_CODE_AUDIO = 4; // 音频
    public final int REQUEST_CALL_PHONE = 5; // 电话
    public final int REQUEST_PHONE_CONTACTS = 6; // 联系人
    public final int REQUEST_SEND_SMS = 7; // 短信




    protected CustomProgressDialog mLoadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {



        mPermissionsChecker = new PermissionsCheckerUtil(this);
        mNetworkInitAvailable = NetWorkUtils.checkNetwork();

        mLoadingDialog=new CustomProgressDialog(this).createDialog();
        mLoadingDialog.dismiss();
        super.onCreate(savedInstanceState);




    }

    public CustomProgressDialog getmLoadingDialog() {
        return mLoadingDialog;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //    启动授权页面
    public void startPermissionsActivity(int pressionType) {
        switch (pressionType) {
            case REQUEST_CODE_PHONESTATE:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_CODE_PHONESTATE, PermissionsSetUtils.PHOTO_STATES);
                break;
            case REQUEST_CODE_ADDRESS:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_CODE_ADDRESS, PermissionsSetUtils.ADDRESS);
                break;
            case REQUEST_CODE_SDCARD:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_CODE_SDCARD, PermissionsSetUtils.PERMISSIONS_STORAGE);
                break;
            case REQUEST_CODE_CAMMER:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_CODE_CAMMER, PermissionsSetUtils.CAMERA_PERMISSION);
                break;
            case REQUEST_CODE_AUDIO:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_CODE_AUDIO, PermissionsSetUtils.AUDIO_PERMISSIONS);
                break;
            case REQUEST_CALL_PHONE:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_CALL_PHONE, PermissionsSetUtils.CALL_PHONE);
                break;
            case REQUEST_PHONE_CONTACTS:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_PHONE_CONTACTS, PermissionsSetUtils.PHONE_CONTACTS);
                break;
            case REQUEST_SEND_SMS:
                PermissionsActivity.startPermissionActivityForResult(this, REQUEST_SEND_SMS, PermissionsSetUtils.SEND_SMS);
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
