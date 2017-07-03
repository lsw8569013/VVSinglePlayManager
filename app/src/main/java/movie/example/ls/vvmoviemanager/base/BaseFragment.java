package movie.example.ls.vvmoviemanager.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import movie.example.ls.vvmoviemanager.application.BaseApplication;
import movie.example.ls.vvmoviemanager.ui.PermissionsActivity;
import movie.example.ls.vvmoviemanager.utils.NetWorkUtils;
import movie.example.ls.vvmoviemanager.utils.PermissionsCheckerUtil;
import movie.example.ls.vvmoviemanager.utils.PermissionsSetUtils;


/**
 * 针对业务 直接基类封装
 * Date :  2016/10/27
 */
public abstract class BaseFragment extends BaseAppFragment  {

    public boolean mNetworkInitAvailable=false;
    public PermissionsCheckerUtil mPermissionsChecker; // 权限检测器
    public final int PERMISSIONS_GRANTED = 0; // 权限授权
    public final int PERMISSIONS_DENIED = 1; // 权限拒绝
    public final int REQUEST_CODE_PHONESTATE = 0; // 系统参数请求码
    public final int REQUEST_CODE_ADDRESS = 1; // 定位请求码
    public final int REQUEST_CODE_SDCARD = 2; // 读写SD卡请求码
    public final int REQUEST_CODE_CAMMER = 3; // 拍照
    public final int REQUEST_CODE_AUDIO = 4; // 音频
    public final int REQUEST_CALL_PHONE = 5; // 电话

    protected  String userID = BaseApplication.getUserID();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mPermissionsChecker = new PermissionsCheckerUtil(getContext());

        return super.onCreateView(inflater,container,savedInstanceState);

    }

    //    启动授权页面
    public void startPermissionsActivity(int pressionType) {
        switch (pressionType){
            case REQUEST_CODE_PHONESTATE:
                PermissionsActivity.startPermissionActivityForResult(getActivity(), REQUEST_CODE_PHONESTATE, PermissionsSetUtils.PHOTO_STATES);
                break;
            case REQUEST_CODE_ADDRESS:
                PermissionsActivity.startPermissionActivityForResult(getActivity(), REQUEST_CODE_ADDRESS, PermissionsSetUtils.ADDRESS);
                break;
            case REQUEST_CODE_SDCARD:
                PermissionsActivity.startPermissionActivityForResult(getActivity(), REQUEST_CODE_SDCARD, PermissionsSetUtils.PERMISSIONS_STORAGE);
                break;
            case REQUEST_CODE_CAMMER:
                PermissionsActivity.startPermissionActivityForResult(getActivity(), REQUEST_CODE_CAMMER, PermissionsSetUtils.CAMERA_PERMISSION);
                break;
            case REQUEST_CODE_AUDIO:
                PermissionsActivity.startPermissionActivityForResult(getActivity(), REQUEST_CODE_AUDIO, PermissionsSetUtils.AUDIO_PERMISSIONS);
                break;
            case REQUEST_CALL_PHONE:
                PermissionsActivity.startPermissionActivityForResult(getActivity(), REQUEST_CALL_PHONE, PermissionsSetUtils.CALL_PHONE);
                break;
        }
    }

    //单纯的展示结果页面如需业务处理请直接调用 toggle...


    @Override
    public void onResume() {
        super.onResume();
        mNetworkInitAvailable= NetWorkUtils.checkNetwork();
    }
}
