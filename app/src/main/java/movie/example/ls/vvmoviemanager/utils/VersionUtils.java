package movie.example.ls.vvmoviemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/16
 * Email : lsw
 */
public class VersionUtils {
    /**
     * 判断是否需要更新
     * @param context
     * @param versionName
     */
    public static boolean checkVersion(Context context, String versionName) {
        String current_Version = getVersionName(context);
        if (current_Version == null) {
            return false;
        }
        return !versionName.equals(current_Version);
    }

    /*
     * 获取当前程序的版本号
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo != null ? packInfo.versionName : null;
    }

    // 获取版本号
    public static int getVersionCode(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 安装apk
    public static void installApk(Context context, File file) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 是否为debug模式
     * @param context
     * @return
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {

        }
        return false;
    }

}