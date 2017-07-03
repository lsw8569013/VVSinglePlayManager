package movie.example.ls.vvmoviemanager.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Android6.0 权限检查
 * Created By: lsw
 * Author : lsw
 * Date :  2016/5/23
 * Email : lsw
 */
public class PermissionsCheckerUtil {

    private static final String TAG = "PermissionsCheckerUtil";

    private final Context mContext;

    public PermissionsCheckerUtil(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
               return true;
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return lacksPermissionOps(permission);
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean lacksPermissionOps(String permission){

        String checkStr="";

        switch (permission){
            case Manifest.permission.CAMERA :
                checkStr= AppOpsManager.OPSTR_CAMERA;
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                checkStr= AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE;
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                checkStr= AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE;
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                checkStr= AppOpsManager.OPSTR_COARSE_LOCATION;
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                checkStr= AppOpsManager.OPSTR_FINE_LOCATION;
                break;
            case Manifest.permission.READ_PHONE_STATE:
                checkStr= AppOpsManager.OPSTR_READ_PHONE_STATE;
                break;
            case Manifest.permission.RECORD_AUDIO:
                checkStr= AppOpsManager.OPSTR_RECORD_AUDIO;
                break;
            case Manifest.permission.MODIFY_AUDIO_SETTINGS:
                checkStr= AppOpsManager.OPSTR_RECORD_AUDIO;
                break;
            case Manifest.permission.CALL_PHONE:
                checkStr= AppOpsManager.OPSTR_CALL_PHONE;
                break;
            case Manifest.permission.READ_CONTACTS:
                checkStr= AppOpsManager.OPSTR_READ_CONTACTS;
                break;
            case Manifest.permission.WRITE_CONTACTS:
                checkStr= AppOpsManager.OPSTR_WRITE_CONTACTS;
                break;
        }

        if(TextUtils.isEmpty(checkStr)){
            return false;
        }

        final int version = Build.VERSION.SDK_INT;

        if (version >= 23) {
            AppOpsManager manager = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);

            int checkResult = manager.checkOpNoThrow(
                    checkStr,
                    Binder.getCallingUid(),
                    mContext.getPackageName()
            );

            if (AppOpsManager.MODE_ALLOWED == checkResult) {
                return false;
            } else {
                return true;
            }
        }

        return false;

    }


    /**
     * 判断悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24);  // AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                return true;
            } else {
                return false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class<?> spClazz = Class.forName(manager.getClass().getName());
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op,
                        Binder.getCallingUid(), context.getPackageName());
                Log.e("399", " property: " + property);

                if (AppOpsManager.MODE_ALLOWED == property) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("399", "Below API 19 cannot invoke!");
        }
        return false;
    }

    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    public static boolean getAppOps(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            try {
                Object object = context.getSystemService("appops");
                if (object == null) {
                    return false;
                }
                Class localClass = object.getClass();
                Class[] arrayOfClass = new Class[3];
                arrayOfClass[0] = Integer.TYPE;
                arrayOfClass[1] = Integer.TYPE;
                arrayOfClass[2] = String.class;
                Method method = localClass.getMethod("checkOp", arrayOfClass);
                if (method == null) {
                    return false;
                }
                Object[] arrayOfObject1 = new Object[3];
                arrayOfObject1[0] = Integer.valueOf(24);
                arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
                arrayOfObject1[2] = context.getPackageName();
                int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
                return m == AppOpsManager.MODE_ALLOWED;
            } catch (Exception ex) {

            }
        }
        return false;
    }

}
