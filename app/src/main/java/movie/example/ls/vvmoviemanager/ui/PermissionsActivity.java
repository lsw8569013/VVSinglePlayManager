package movie.example.ls.vvmoviemanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.base.BaseActivity;
import movie.example.ls.vvmoviemanager.utils.PermissionsCheckerUtil;
import movie.example.ls.vvmoviemanager.utils.PermissionsSetUtils;


/**
 * Android 6.0-- 动态授权页面
 * WangFei 2016-3-21
 * 授权拒绝后弹出权限页面
 */
public class PermissionsActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String EXTRA_PERMISSIONS =
            "me.fly.permission.extra_permission"; // 权限参数
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    private PermissionsCheckerUtil mChecker; // 权限检测器
    private boolean isRequireCheck; // 是否需要系统权限检测
    private int codeRequest;//请求码

    private String title;
    private String[] permissions;
    private LinearLayout rootLayout;
    private TextView text;
    private TextView exit;
    private TextView setting;


    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            if (permissions == null) {
                return;
            }
            if (mChecker.lacksPermissions(permissions)) {
                if ("Xiaomi".equals(Build.MANUFACTURER)) {//小米手机
                    loge("小米手机");
                    isRequireCheck = false;
                    showMissingPermissionDialog();
                } else {
                    requestPermissions(permissions); // 请求权限
                }
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("key", codeRequest);
        outState.putStringArray("key2", permissions);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        codeRequest = savedInstanceState.getInt("key");
        permissions = savedInstanceState.getStringArray("key2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 启动当前权限页面的公开接口
    public static void startPermissionActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        intent.putExtra("codeRequest", requestCode);
        if (permissions == PermissionsSetUtils.CALL_PHONE) {
            intent.putExtra("name", "拨打电话");
        } else if (permissions == PermissionsSetUtils.AUDIO_PERMISSIONS) {
            intent.putExtra("name", "录制声音");
        } else if (permissions == PermissionsSetUtils.CAMERA_PERMISSION) {
            intent.putExtra("name", "相机");
        } else if (permissions == PermissionsSetUtils.PERMISSIONS_STORAGE) {
            intent.putExtra("name", "读写SD卡");
        } else if (permissions == PermissionsSetUtils.ADDRESS) {
            intent.putExtra("name", "定位");
        } else if (permissions == PermissionsSetUtils.PHOTO_STATES) {
            intent.putExtra("name", "手机标识");
        } else if (permissions == PermissionsSetUtils.PHONE_CONTACTS) {
            intent.putExtra("name", "联系人");
        } else if (permissions == PermissionsSetUtils.SEND_SMS) {
            intent.putExtra("name", "发送短信");
        }
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void setWindowsParams() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.permission_pop;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = getIntent().getStringExtra("name");
        codeRequest = getIntent().getIntExtra("codeRequest", -1);
        permissions = getPermissions();
    }

    @Override
    protected void initDataAndViews(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            codeRequest = savedInstanceState.getInt("key");
            permissions = savedInstanceState.getStringArray("key2");
        }
        loge("开启授权界面------------");
        mChecker = new PermissionsCheckerUtil(this);
        isRequireCheck = true;
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        text = (TextView) findViewById(R.id.text);
        text.setText("缺少" + title + "权限");
        exit = (TextView) findViewById(R.id.exit);
        setting = (TextView) findViewById(R.id.setting);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAppSettings();
            }
        });
        rootLayout.setVisibility(View.GONE);

}

    @Override
    protected void initListeners() {

    }

    @Override
    public void onClick(View view) {

    }



    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.SCALE;
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loge("授权");
                isRequireCheck = false;
                allPermissionsGranted();
            } else {
                loge("拒绝");
                isRequireCheck = false;
                showMissingPermissionDialog();
            }
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        rootLayout.setVisibility(View.VISIBLE);
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

}
