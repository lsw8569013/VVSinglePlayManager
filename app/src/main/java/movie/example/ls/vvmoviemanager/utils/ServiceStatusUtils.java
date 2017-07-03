package movie.example.ls.vvmoviemanager.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/16
 * Email : lsw
 */
public class ServiceStatusUtils {

    /**
     * 检测服务是否正在运行
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {

        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        // 获取系统所有正在运行的服务,最多返回66个
        List<RunningServiceInfo> runningServices = am.getRunningServices(66);

        for (RunningServiceInfo runningServiceInfo : runningServices) {
            // 获取服务的名称
            String className = runningServiceInfo.service.getClassName();
            if (className.equals(serviceName)) {
                // 服务存在
                return true;
            }
        }
        return false;
    }

    /**
     * 检测应用是否在前台运行
     * @param mContext
     * @return
     */
    public static  boolean isTopActivity(Context mContext) {
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if(tasksInfo!=null && !tasksInfo.isEmpty()){
            ComponentName activity = tasksInfo.get(0).topActivity;
            if(activity.getPackageName().equals(mContext.getPackageName())){
                return true;
            }
        }
        return false;
    }

}
