package movie.example.ls.vvmoviemanager.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import movie.example.ls.vvmoviemanager.application.BaseApplication;


/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/4/9
 * Email : lsw
 */
public class NetWorkUtils {
    /**
     * 判断网络连接是否打开
     */
    public static boolean checkNetwork() {
        boolean flag = false;
        ConnectivityManager netManager = (ConnectivityManager) BaseApplication.getmContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManager.getActiveNetworkInfo() != null) {
            flag = netManager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }


    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(){

        ConnectivityManager cm = (ConnectivityManager)  BaseApplication.getmContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetInfo = cm.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 是否是流量
     * @return
     */
    public static boolean isFlux(){

        ConnectivityManager cm = (ConnectivityManager)  BaseApplication.getmContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetInfo = cm.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean gPSIsOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
}
