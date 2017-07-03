package movie.example.ls.vvmoviemanager.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.ButterKnife;
import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.glide.FinalBitmap;
import movie.example.ls.vvmoviemanager.utils.CommonUtils;
import movie.example.ls.vvmoviemanager.utils.DialogUtils;


/**
 * 通用基类抽取
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/11
 * Email : lsw
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Log tag
     */
    protected static String TAG = null;
    /**
     * context
     */
    protected Context mContext = null;

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected FinalBitmap finalBitmap;


    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, Gravity, FADE
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // base setup
        setWindowsParams();

        mContext = this;
        TAG = this.getClass().getSimpleName();

        BaseAppManager.getInstance().addActivity(this);

        if (finalBitmap == null) {
            finalBitmap = FinalBitmap.create(this);
        }

        setScreenInfos();

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            ButterKnife.bind(this);
            initStateBar();
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }



        initDataAndViews(savedInstanceState);
        initListeners();
    }
//  初始化状态栏
    private void initStateBar(){

    }

    /**
     * 设置屏幕的尺寸信息
     */
    private void setScreenInfos() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
    }

    //----------------点击空白地区隐藏软键盘---------------



    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，
     * 因为当用户点击EditText时则不能隐藏
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left
                    && event.getX() < right
                    && event.getY() > top
                    && event.getY() < bottom) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }


    /**
     * 关闭软键盘
     */
    public void hintKeyBorad() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.right_enter, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.former_in, R.anim.top_up);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.former_in, R.anim.bottom_down);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.former_in, R.anim.alpha_scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.former_in, R.anim.alpha_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void setWindowsParams();

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    @LayoutRes
    protected abstract int getContentViewLayoutID();

    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * init all views and add events
     */
    protected abstract void initDataAndViews(Bundle savedInstanceState);
    /**
     * init all views and add events
     */
    protected abstract void initListeners();

    /**
     * network connected
     */

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    //---------Activity跳转封装-----------------------------------

    /**
     * 带返回值请求 --无参
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        this.startActivityForResult(cls, null, requestCode);
    }

    /**
     * 带返回值启动 Activity --有参
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    /**
     * 启动 Activity--无参数
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    /**
     * 启动 Activity--有参数
     *
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    /**
     * 启动一个Activity
     * 让新的Activity从一个小的范围扩大到全屏
     * 无参数
     *
     * @param cls
     * @param v
     */
    public void startActivity(Class<?> cls, View v) {
        // 让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeScaleUpAnimation(v,
                        (int) v.getWidth() / 2, (int) v.getHeight() / 2, // 拉伸开始的坐标
                        0, 0);// 拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        Intent intent = new Intent(this, cls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            overridePendingTransition(R.anim.right_enter, R.anim.left_out);
        }
    }

    /**
     * 启动一个Activity
     * 让新的Activity从一个小的范围扩大到全屏
     * 有参数
     *
     * @param cls
     * @param v
     * @param bundle
     */
    public void startActivity(Class<?> cls, View v, Bundle bundle) {
        // 让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeScaleUpAnimation(v,
                        (int) v.getWidth() / 2, (int) v.getHeight() / 2, // 拉伸开始的坐标
                        0, 0);// 拉伸开始的区域大小，这里用（0，0）表示从无到全屏

        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            overridePendingTransition(R.anim.right_enter, R.anim.left_out);
        }
    }

    /**
     * show toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        //防止遮盖虚拟按键
        if (null != msg && !CommonUtils.isEmpty(msg)) {
            DialogUtils.showToast(this, "text", msg, 0);
        }
    }

    protected void loge(String msg){
        Log.e(TAG,msg);
    }
    protected void loge(String tag, String msg){
        Log.e(tag,msg);
    }
    protected void logi(String msg){
        Log.i(TAG,msg);
    }
    protected void logi(String tag, String msg){
        Log.i(tag,msg);
    }
    protected void logw(String msg){
        Log.w(TAG,msg);
    }
    protected void logw(String tag, String msg){
        Log.w(tag,msg);
    }

    /**
     * 打印
     * @param str
     */
    protected  void sysout(String str){
       System.out.println("LogUtils--->" + str);
    }
    /**
     * 指定名称--打印
     * @param str
     */
    protected  void sysout(String tag, String str){
       System.out.println(tag + "--->" + str);
    }

    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
