package movie.example.ls.vvmoviemanager.library.imageshow;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.bean.CommontBack4C;
import movie.example.ls.vvmoviemanager.glide.FinalBitmap;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;

public class DragPhotoActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<String> mList;
    private DragPhotoView[] mPhotoViews;

    int mOriginLeft;
    int mOriginTop;
    int mOriginHeight;
    int mOriginWidth;
    int mOriginCenterX;
    int mOriginCenterY;
    private float mTargetHeight;
    private float mTargetWidth;
    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;
    private Handler handler = new Handler();
    private PopupWindow pop;

    private int time = 0;
    private final int MSGTIME = 0x22;
    private Timer timer;
    private TimerTask timerTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSGTIME:
                    if (time < 20) {
                        time++;
                    } else {
                        cancelTimer();
                        if (ConfigUtils.TYPE_MIMEFRAGMENT == type) {

                        }
                        finish();
                    }
                    break;
            }
        }
    };
    private int type;
    private ArrayList<CommontBack4C> list;
    private int position;
    private FinalBitmap finalBitmap;

    @Override
    protected void onStart() {
        super.onStart();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showPop(finalBitmap);
//            }
//        }, 250);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drag_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        int screenWidth = ScreenUtils.getScreenWidth(this);

//        final int smallW = getIntent().getIntExtra("smallW", 50);
//        final int smallH = getIntent().getIntExtra("smallH", 50);

        mList = getIntent().getStringArrayListExtra("imgurls");
        type = getIntent().getIntExtra("type", ConfigUtils.TYPE_MESSAGELIST_AND_PRAISELIST);
        list = (ArrayList<CommontBack4C>) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        mPhotoViews = new DragPhotoView[mList.size()];

       finalBitmap = FinalBitmap.create(this);


        for (int i = 0; i < mPhotoViews.length; i++) {
            DragPhotoView view = (DragPhotoView) View.inflate(this, R.layout.item_viewpager, null);
            mPhotoViews[i] = view;
            //---test
           /* View item = View.inflate(this, R.layout.item_viewpager, null);
            ImageView iv_local = (ImageView) item.findViewById(R.id.iv_local);
            final DragPhotoView iv_drayView = (DragPhotoView) item.findViewById(R.id.iv_drayView);
            mPhotoViews[i] = iv_drayView;
            iv_drayView.setVisibility(View.GONE);
            finalBitmap.displayC(iv_local,getIntent().getStringExtra("localUrl"));*/
            //---test

//            Logger.e(getIntent().getStringExtra("localUrl")+"小图加载地址");
//            Logger.e(mList.get(0)+"大图地址");
            Glide.with(this)
                    .load(mList.get(i))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new GlideDrawableImageViewTarget(mPhotoViews[i]) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            findViewById(R.id.pb_dragPhoto).setVisibility(View.GONE);
                            if (pop != null ) {
                                if(pop.isShowing()){
                                    pop.dismiss();
                                    pop = null;
                                }else{
                                    dismissPop(100);
                                }
                            } else {
                                dismissPop(100);
                            }
                        }
                    });
            /*if(getIntent().getStringExtra("localUrl").equals("")){
                with.load(mList.get(i))
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(new GlideDrawableImageViewTarget(mPhotoViews[i]){
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                findViewById(R.id.pb_dragPhoto).setVisibility(View.GONE);
                                view.setVisibility(View.VISIBLE);
//                                view.setBackgroundColor(Color.BLACK);
                            }
                        });
            }*/

            mPhotoViews[i].setOnTapListener(new DragPhotoView.OnTapListener() {
                @Override
                public void onTap(DragPhotoView view) {
//                    Toast.makeText(DragPhotoActivity.this   ,"----",Toast.LENGTH_SHORT).show();
                    finishWithAnimation();
                }
            });

            mPhotoViews[i].setOnExitListener(new DragPhotoView.OnExitListener() {
                @Override
                public void onExit(DragPhotoView view, float x, float y, float w, float h) {
                    performExitAnimation(view, x, y, w, h);
                }
            });

            mPhotoViews[i].setOnViewTouchListener(new DragPhotoView.OnViewTouchListener() {
                @Override
                public void onViewTouch() {
                    time = 0;
                }
            });

            if (ConfigUtils.TYPE_MIMEFRAGMENT == type) {
                if (timer == null) {
                    timer = new Timer();
                }
                if (timerTask == null) {
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mHandler.sendEmptyMessage(MSGTIME);
                        }
                    };
                }
                timer.schedule(timerTask, 0, 100);
            }
        }

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPhotoViews[position]);
                return mPhotoViews[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPhotoViews[position]);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        mViewPager.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        mOriginLeft = getIntent().getIntExtra("left", 0);
                        mOriginTop = getIntent().getIntExtra("top", 0);
                        mOriginHeight = getIntent().getIntExtra("height", 0);
                        mOriginWidth = getIntent().getIntExtra("width", 0);
                        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
                        mOriginCenterY = mOriginTop + mOriginHeight / 2;

                        int[] location = new int[2];

                        final DragPhotoView photoView = mPhotoViews[0];
                        photoView.getLocationOnScreen(location);

                        mTargetHeight = (float) photoView.getHeight();
                        mTargetWidth = (float) photoView.getWidth();
                        mScaleX = (float) mOriginWidth / mTargetWidth;
                        mScaleY = (float) mOriginHeight / mTargetHeight;

                        float targetCenterX = location[0] + mTargetWidth / 2;
                        float targetCenterY = location[1] + mTargetHeight / 2;

                        mTranslationX = mOriginCenterX - targetCenterX;
                        mTranslationY = mOriginCenterY - targetCenterY;
                        photoView.setTranslationX(mTranslationX);
                        photoView.setTranslationY(mTranslationY);

                        photoView.setScaleX(mScaleX);
                        photoView.setScaleY(mScaleY);

                        performEnterAnimation();

                        for (int i = 0; i < mPhotoViews.length; i++) {
                            mPhotoViews[i].setMinScale(mScaleX);
                        }
                    }
                });
    }

    private void dismissPop(int i) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pop != null) {
                    pop.dismiss();
                    pop = null;
                }else{
                    dismissPop(100);
                }
            }
        }, i);
    }

    private void showPop(FinalBitmap finalBitmap) {

//        ImageView iv = new ImageView(this);
//        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
//        finalBitmap.displayC(iv,getIntent().getStringExtra("localUrl"));
        View view = View.inflate(this, R.layout.item_drag_popview, null);
        ImageView iv_local = (ImageView) view.findViewById(R.id.iv_local);
        String localUrl = getIntent().getStringExtra("localUrl");
        if(localUrl.contains("?")){
            finalBitmap.displayC(iv_local, localUrl, localUrl);
        }else{
            Glide.with(this).load(localUrl+ConfigUtils.QINIU_THUMBURL_IMAGE_SMALL)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv_local);

        }

        pop = new PopupWindow(view, 480, 480);
        pop.setFocusable(false);
        pop.setOutsideTouchable(false);
        pop.showAtLocation(mViewPager, Gravity.CENTER, 0, 0);
    }

    /**
     * ===================================================================================
     * <p>
     * 底下是低版本"共享元素"实现   不需要过分关心  如有需要 可作为参考.
     * <p>
     * Code  under is shared transitions in all android versions implementation
     */
    private void performExitAnimation(final DragPhotoView view, float x, float y, float w, float h) {
        view.finishAnimationCallBack();
        float viewX = mTargetWidth / 2 + x - mTargetWidth * mScaleX / 2;
        float viewY = mTargetHeight / 2 + y - mTargetHeight * mScaleY / 2;
        view.setX(viewX);
        view.setY(viewY);
        float centerY;

        float centerX = view.getX() + mOriginWidth / 2;

        if (mOriginWidth > mOriginHeight) {
            centerY = view.getY() ;
        } else {
            centerY = view.getY() + mOriginHeight / 2;
        }

        float translateX = mOriginCenterX - centerX;
        float translateY = mOriginCenterY - centerY;


        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(view.getX(), mOriginLeft);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(250);
        translateXAnimator.start();
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(view.getY(), mOriginTop - 85);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        translateYAnimator.setDuration(250);
        translateYAnimator.start();
    }

    private void finishWithAnimation() {

        final DragPhotoView photoView = mPhotoViews[0];
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(0, mTranslationX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(250);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(0, mTranslationY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(250);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(1, mScaleY);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(250);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(1, mScaleX);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleX((Float) valueAnimator.getAnimatedValue());

            }
        });

        scaleXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        scaleXAnimator.setDuration(250);
        scaleXAnimator.start();
    }

    private void performEnterAnimation() {
        final DragPhotoView photoView = mPhotoViews[0];
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(photoView.getX(), 0);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(250);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(photoView.getY(), 0);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(250);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(mScaleY, 1);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(250);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(mScaleX, 1);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleXAnimator.setDuration(250);
        scaleXAnimator.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishWithAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
