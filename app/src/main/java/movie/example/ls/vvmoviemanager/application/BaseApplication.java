package movie.example.ls.vvmoviemanager.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.text.TextUtils;


import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

import movie.example.ls.vvmoviemanager.BuildConfig;

import static movie.example.ls.vvmoviemanager.utils.CenterUtil.initPath;


/**
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private volatile static String token;
    private volatile static String userID = "0";
    private volatile static Context mContext;

    private volatile String nickName;
    private volatile static boolean homeClick;

    public BaseApplication() {
    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        BaseApplication app = (BaseApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private synchronized HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(new File(this.getExternalCacheDir(), "video-cache"))
                .build();
    }

    public static void setHomeClick(boolean homeClick) {
        BaseApplication.homeClick = homeClick;
    }

    public static boolean isHomeClick() {
        return homeClick;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initActivityCheck();
        initPath();
        initToken();
        initNetState();
        initException();
        initDBHelper();
        initCamera();
        initVoice();
        initCheack();
        initQb();
    }

    private void initQb() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。


    }

    private void initActivityCheck() {

    }

    private void initVoice() {
    }

    private void initCheack() {
        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork()   // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
//                    .penaltyLog()   //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
//                    .build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects() //探测SQLite数据库操作
//                    .penaltyLog()  //打印logcat
//                    .penaltyDeath()
//                    .build());
        }else{
            StrictMode.setVmPolicy (new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private void initCamera() {
//        VCamera.setDebugMode(BuildConfig.DEBUG);// 开启log输出,ffmpeg输出到logcat
//        VCamera.initialize(this);
    }

    public static  String getToken() {
        if (TextUtils.isEmpty(token)) {
            initToken();
        }
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public static String getUserID() {
        if (TextUtils.isEmpty(userID)) {
            initToken();
        }
        return userID;
    }

    public static Context getmContext() {
        return  mContext;
    }

    private static void initToken() {
    }

    public static BaseApplication getInstance() {
        return SingleHolderApplication.sInstance;
    }

    private static class SingleHolderApplication {
        private static final BaseApplication sInstance = new BaseApplication();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    //  注册网络状态检测器
    private void initNetState() {
    }

    //  异常捕捉
    public void initException() {
        if (!BuildConfig.DEBUG) {
//            CrashHandler.getInstance().init(this);
        }
    }

    //初始化数据库
    private void initDBHelper() {
    }

    /**
     * 杀死进程
     * 结束APP
     */
    public void exitAPP() {
        try {
            System.gc();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getNickName() {
        if (TextUtils.isEmpty(nickName)) {
        }
        return nickName;
    }


}
