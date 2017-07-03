package movie.example.ls.vvmoviemanager.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.glide.FinalBitmap;

/**
 * 通用基类抽取
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/11
 * Email : lsw
 */
public abstract class BaseAppFragment extends Fragment implements View.OnClickListener{
    /**
     * Log tag
     */
    protected static String TAG = null;

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;

    protected int mScreenHeight = 0;

    protected float mScreenDensity = 0.0f;

    /**
     * context
     */
    protected Context mContext = null;

    /**
     * avoid crash
     */
    protected Activity mActivity = null;


    protected FinalBitmap finalBitmap;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();

        if (finalBitmap == null) {
            finalBitmap = FinalBitmap.create(BaseAppFragment.this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (getContentViewLayoutID() != 0) {
                View view=inflater.inflate(getContentViewLayoutID(), null);
                ButterKnife.bind(this,view);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                if (getActivity() != null) {
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                    mScreenDensity = displayMetrics.density;
                    mScreenHeight = displayMetrics.heightPixels;
                    mScreenWidth = displayMetrics.widthPixels;

                }

                Bundle extras = getArguments();
                if (null != extras) {
                    getBundleExtras(extras);
                }

                initDataAndViews(view,savedInstanceState);

                initListeners();



                return view;

            }

        return super.onCreateView(inflater, container, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null){
            Glide.get(mContext).clearMemory();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
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
    protected abstract void initDataAndViews(View view, Bundle savedInstanceState);
    /**
     * init all views and add events
     */
    protected abstract void initListeners();

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();



    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    //---------Activity跳转封装---------------------------------------------

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
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    /**
     * 启动 Activity--无参数
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    public void startActivityImage(Intent intent) {
        startActivity(intent);
        mActivity.overridePendingTransition( 0 , 0 );
    }

    /**
     * 启动 Activity--有参数
     *
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.right_enter, R.anim.left_out);
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
        Intent intent = new Intent(mContext, cls);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            mActivity.overridePendingTransition(R.anim.right_enter, R.anim.left_out);
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

        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            mActivity.overridePendingTransition(R.anim.right_enter, R.anim.left_out);
        }
    }

    /**
     * show toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        //防止遮盖虚拟按键
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
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
    protected  void sysoutByTag(String tag, String str){
        System.out.println(tag + "--->" + str);
    }

}

