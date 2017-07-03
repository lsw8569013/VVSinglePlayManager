package movie.example.ls.vvmoviemanager.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import movie.example.ls.vvmoviemanager.application.BaseApplication;


/**
 */
public class ScreenUtils {

    private ScreenUtils() {

    }

    /**
     * pix转成dip 像素转成dp
     */
    public static int px2dp(float pixValue) {
        final float scale = BaseApplication.getmContext().getResources().getDisplayMetrics().density;
        return (int) (pixValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为pix值
     */
    public  static  int dp2px(int dipValue) {
        final float scale = BaseApplication.getmContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 获得状态栏的高度
     *
     * @return
     */
    public static int getStatusHeight(){

        int statusHeight = -1;
        try{
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = BaseApplication.getmContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e){
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity){

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity){

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }


    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    
    /** 获取手机的密度*/
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int screenWidth() {
        WindowManager wm = (WindowManager) BaseApplication.getmContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**
     * 获取屏幕高度
     * @return
     */
    public static int screenHeight() {
        WindowManager wm = (WindowManager) BaseApplication.getmContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 回到home，后台运行
     *
     * @param context
     */
    public static void goHome(Context context) {

        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);

    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取状态栏高度＋标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 计算缩放
     * @param layoutParams
     * @param width
     * @param height
     * @param mContext
     * @return
     */
    public static ViewGroup.LayoutParams getScalWidthHeight(ViewGroup.LayoutParams layoutParams, int width, int height, Context mContext) {
        int screenWidth = getScreenWidth(mContext);

        int limitHeight = screenWidth * 12 /10;

        if(width>height){
            //按宽进行缩放
            layoutParams.width = screenWidth;
            layoutParams.height = height * screenWidth/width;
        }else{
            if(height <= limitHeight){
                layoutParams.width = screenWidth;
                int scalHeight = height * screenWidth / width;
                if(scalHeight>limitHeight){
                    layoutParams.height = limitHeight;
                }else{
                    layoutParams.height =  scalHeight;
                }
            }else{
                layoutParams.height = limitHeight;
//                layoutParams.width = width * height /limitHeight;
                layoutParams.width = screenWidth;
            }
        }
        return layoutParams;
    }

    public static ViewGroup.LayoutParams getScalWidthHeightVideoList(ViewGroup.LayoutParams layoutParams, int width, int height, Context mContext) {
        int screenWidth = getScreenWidth(mContext);

        int limitHeight = getScreenHeight(mContext);

        if(width>height){
            //按宽进行缩放
            layoutParams.width = screenWidth;
            layoutParams.height = height * screenWidth/width;
        }else{
            if(height <= limitHeight){
                layoutParams.width = screenWidth;
                layoutParams.height = height * screenWidth/width;
            }else{
                layoutParams.height = limitHeight;
                layoutParams.width = width * height /limitHeight;
            }
        }
        return layoutParams;
    }

    public static int getScalHeightVideoList(int width, int height, Context mContext) {
        int scalHeight = 0;

        int screenWidth = getScreenWidth(mContext);

        int limitHeight = getScreenHeight(mContext);

        if(width>height){
            //按宽进行缩放

            scalHeight = height * screenWidth/width;
        }else{
            if(height <= limitHeight){
                scalHeight = height * screenWidth/width;
            }else{
                scalHeight = width * height /limitHeight;
            }
        }
        return scalHeight;
    }

    public static ViewGroup.LayoutParams getScalWidthHeightComment(ViewGroup.LayoutParams layoutParams, int width, int height, Context mContext) {

        if(width <= 200) {
            layoutParams.width = width;
            layoutParams.height = height ;
        }else {
            layoutParams.width = 200;
            layoutParams.height = height * 200 /width  ;
        }
        return layoutParams;
    }

    /**
     * 返回当前屏幕是否为竖屏。
     * @param context
     * @return 当且仅当当前屏幕为竖屏时返回true,否则返回false。
     */
    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


}

