package movie.example.ls.vvmoviemanager.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import movie.example.ls.vvmoviemanager.utils.ConfigUtils;
import movie.example.ls.vvmoviemanager.utils.SharedPreferencesUtils;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Activity 管理器
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/22
 * Email : lsw
 */
public class BaseAppManager {

    private static final String TAG = BaseAppManager.class.getSimpleName();

    private static BaseAppManager instance = null;
    private static List<Activity> mActivities = new LinkedList<Activity>();

    private BaseAppManager() {

    }

    public static BaseAppManager getInstance() {
        if (null == instance) {
            synchronized (BaseAppManager.class) {
                if (null == instance) {
                    instance = new BaseAppManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    //获取当前栈顶下面那个Activity
    public synchronized Activity getNextStack() {
        Activity activity = null;
        if (mActivities.size() >1){
            activity = (Activity) mActivities.get(mActivities.size() - 2);
        }
        return activity;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clearEmpty() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }

    /**
     * 关闭所有Activity，除了参数传递的Activity
     */
    public synchronized void finishAll(Class except) {
        List<Activity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<Activity>(mActivities);
        }
        for (Activity activity : copy) {
            if (activity.getClass() != except)
                activity.finish();
        }
    }

    /**
     * 获取当前处于栈顶的activity，无论其是否处于前台
     */
    public static Activity getCurrentActivity() {
        List<Activity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<Activity>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }


    /**
     * 检测某Activity是否在当前Task的栈顶
     */
    public static boolean isTopActivy(Context mContext,String cmdName){

        ActivityManager manager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if(null != runningTaskInfos){
            cmpNameTemp=(runningTaskInfos.get(0).topActivity).getShortClassName();
        }
        if(null == cmpNameTemp){
            return false;
        }
        return cmpNameTemp.equals(cmdName);

    }

    /**
     * 检测某Activity是否在当前Task的栈顶
     */
    public static boolean isExistActivy(Context mContext,String cmdName){
        boolean result=false;
        ActivityManager manager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if(null != runningTaskInfos){

            for(ActivityManager.RunningTaskInfo info:runningTaskInfos){
                String cmpNameTemp = info.topActivity.getShortClassName();
                if (!TextUtils.isEmpty(cmpNameTemp) && cmpNameTemp.equalsIgnoreCase(cmdName)) {
                    result=true;
                }
            }
        }
        return result;
    }

    /**
     * 判断app是否正在运行
     * 5.0
     * @param ctx
     * @param packageName
     * @return
     */
    public static boolean appIsRunning(Context ctx, String packageName){

        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        if(runningAppProcesses!=null){
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if(runningAppProcessInfo.processName.startsWith(packageName)){
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * app 是否在后台运行
     * 5.0以下
     * @param ctx
     * @param packageName
     * @return
     */
    public static boolean appIsBackgroundRunning(Context ctx,String packageName){

        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        if(runningAppProcesses!=null)
        {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {

                if(runningAppProcessInfo.processName.startsWith(packageName))
                {
                    return runningAppProcessInfo.importance!= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            && runningAppProcessInfo.importance!= ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE; //排除无界面的app
                }
            }
        }

        return false;
    }

    /**
     * 检测应用是否处于前台
     * @param mContext
     * @return
     */
    public static boolean appIsTopRunning(Context mContext) {
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if(tasksInfo!=null && !tasksInfo.isEmpty()){
            ComponentName activity = tasksInfo.get(0).topActivity;
            if(activity.getPackageName().equalsIgnoreCase(mContext.getPackageName())){
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当程序是否在后台运行
     * （当前运行的程序）
     *
     * */

    public static boolean isRunBackground(Context context) {
        return !SharedPreferencesUtils.getInstance(context).getBoolean(ConfigUtils.APP_STATE_FORE);
    }


}

