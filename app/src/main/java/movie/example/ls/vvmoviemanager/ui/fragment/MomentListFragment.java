package movie.example.ls.vvmoviemanager.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.utils.Logger;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.items.ListItem;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.adapter.items.VisibilityItem;
import movie.example.ls.vvmoviemanager.application.BaseApplication;
import movie.example.ls.vvmoviemanager.base.BaseFragment;
import movie.example.ls.vvmoviemanager.bean.CommontBack4C;
import movie.example.ls.vvmoviemanager.bean.FromUser;
import movie.example.ls.vvmoviemanager.bean.MediaBean;
import movie.example.ls.vvmoviemanager.bean.ReleaseCommontBack;
import movie.example.ls.vvmoviemanager.bean.SmailBean4C;
import movie.example.ls.vvmoviemanager.bean.SmailSmallBean;
import movie.example.ls.vvmoviemanager.keyboard.KeyAdapter;
import movie.example.ls.vvmoviemanager.ui.MomentListAcy;
import movie.example.ls.vvmoviemanager.utils.ClickUtils;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;
import movie.example.ls.vvmoviemanager.utils.DataUtil;
import movie.example.ls.vvmoviemanager.utils.ImageUtils;
import movie.example.ls.vvmoviemanager.utils.KeyboardUtils;
import movie.example.ls.vvmoviemanager.utils.LocalBroadcastUtils;
import movie.example.ls.vvmoviemanager.utils.NetWorkUtils;
import movie.example.ls.vvmoviemanager.utils.SharedPreferencesUtils;
import movie.example.ls.vvmoviemanager.views.CustomProgressDialog;
import movie.example.ls.vvmoviemanager.views.DetailCHeadView;


/**
 * momentList
 */
public class MomentListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, VisibilityItem.ItemCallback {

    MomentListAcy parentAcy;


    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.icon_img)
    ImageView iconImg;


    @BindView(R.id.ll_sendRedPack)
    View ll_sendRedPack;
    @BindView(R.id.input_re_layout)
    RelativeLayout inputReLayout;
    @BindView(R.id.viewpager)
    ViewPager iconViewpager;
    @BindView(R.id.iv_sendMsg)
    ImageView iv_sendMsg;
    @BindView(R.id.icon_big)
    ImageView iconBig;
    @BindView(R.id.icon_love)
    ImageView iconLove;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.emojion_root_layout)
    RelativeLayout emojionRootLayout;
    @BindView(R.id.extend_layout)
    FrameLayout extendLayout;
    @BindView(R.id.ll_CKeyBoard)
    LinearLayout llCKeyBoard;

    @BindView(R.id.iv_c_lock)
    ImageView IV_lock;

    @BindView(R.id.iv_keyShade)
    ImageView iv_keyShade;

    
    @BindView(R.id.lv_discovery)
    RecyclerView lvDiscovery;
    
    @BindView(R.id.view_line)
    View viewLine;

    private int commentPostion;
    private MomentListAdapter.ContentHolder holder;
    private FromUser commentReplyUser;
    private HttpProxyCacheServer proxy;

    private int contentType; //0 图片 1 视频
    public int statusBarHeight;
    private boolean isMeizu = false;
    private int viewHight = 0;
    private boolean keyFirst = true;
    private int keyboardHeight = -1;
    private EditText chatInputEt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentAcy = (MomentListAcy) getActivity();
    }

    public RecyclerView mRecyclerView;
    private BGARefreshLayout refreshSearch;
    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {
        }
    });
    private LinearLayoutManager mLayoutManager;

    private ArrayList<VisibilityItem> mList = new ArrayList<VisibilityItem>();

    private final ListItemsVisibilityCalculator mListItemVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback() {
                @Override
                public void deactivateCurrentItem(ListItem listItemToDeactivate, View view, int position) {
                    Logger.e("销毁 view " + position);
                    super.deactivateCurrentItem(listItemToDeactivate, view, position);
                }

                @Override
                public void activateNewCurrentItem(ListItem newListItem, View newView, int newViewPosition) {
                    super.activateNewCurrentItem(newListItem, newView, newViewPosition);
//                    Logger.e("激活 view "+ newViewPosition);
                }
            }, mList);

    private ItemsPositionGetter mItemsPositionGetter;

    private int mScrollState;

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mScrollState == RecyclerView.SCROLL_STATE_IDLE) {

                        mListItemVisibilityCalculator.onScrollStateIdle(
                                mItemsPositionGetter,
                                mLayoutManager.findFirstVisibleItemPosition(),
                                mLayoutManager.findLastVisibleItemPosition());
                    }
                    break;
                case 2:
                    refreshSearch.endLoadingMore();
                    break;
            }
        }
    };
    private MomentListAdapter adapter;

    private int timeToWin = 0;

    public MomentListFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRecyclerView != null && adapter != null) {

            MomentListAdapter.ContentHolder mHolder = (MomentListAdapter.ContentHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
            if (mHolder != null) {
                if (mList.get(0).getCommontBack4C().getMoment().getMediaList().get(0).getMediaType().equals("VIDEO")) {

                    mHolder.iv_playVideo.performClick();
                }
            } else {
//                Logger.e("没找到第一个");
            }
        }

        List<CommontBack4C> list = new ArrayList();
        if (parentAcy.tempMoments.size() > 0) {
            list.add(parentAcy.tempMoments.get(0));
        }

    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_search;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
//        CommontBack4CList = (ArrayList<CommontBack4C>) extras.getSerializable("moments");
    }

    private AppStateBroadCast appStateBroadCast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }



    private class AppStateBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (contentType == 0) {
                return;
            }

            if (BaseApplication.isHomeClick()) {
                return;
            }

            PowerManager powerManager = (PowerManager) getActivity()
                    .getSystemService(Context.POWER_SERVICE);
            if (!powerManager.isScreenOn()) {
                return;
            }

            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equalsIgnoreCase(ConfigUtils.APP_BACKGROUND_ACTION)) {

                finish();
            }
        }
    }


    @Override
    protected void initDataAndViews(View mView, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        proxy = BaseApplication.getProxy(getActivity());

        chatInputEt = (EditText)mView.findViewById(R.id.chat_input_et);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.lv_discovery);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        refreshSearch = (BGARefreshLayout) mView.findViewById(R.id.refreshSearch);
        refreshSearch.setDelegate(this);


        if (parentAcy.type == ConfigUtils.TYPE_MESSAGELIST_AND_PRAISELIST) {
            refreshSearch.setRefreshViewHolder(new DetailCHeadView(mContext, false));
        } else {
            refreshSearch.setRefreshViewHolder(new DetailCHeadView(mContext, true));
        }

        adapter = new MomentListAdapter(getActivity(), mList, finalBitmap, this, getChildFragmentManager(), parentAcy.videoMoments, parentAcy.currentTime);
        mRecyclerView.setAdapter(adapter);
        setDataFirst(parentAcy.tempMoments);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                //不滑动的时候，进行判断 500毫秒后，如果还不滑动，就播放
                if (mScrollState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()) {
                    mHandler.sendEmptyMessageDelayed(1, 800);
                }
                if (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (llCKeyBoard.isShown()) {
                        hideKeyBoard();
                    }

                }
                switch (scrollState) {
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        finalBitmap.pauseRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //SCROLL_STATE_IDLE
                        finalBitmap.resumeRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(!mList.isEmpty()){
//                    mListItemVisibilityCalculator.onScroll(
//                            mItemsPositionGetter,
//                            mLayoutManager.findFirstVisibleItemPosition(),
//                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition()+1,
//                            mScrollState);
//                }
            }
        });
        //-----------------------
        initIconLayout();
        try {
        } catch (Exception e) {
        }
        showHeadView();

        String mediaType = parentAcy.tempMoments.get(0).getMoment().getMediaList().get(0).getMediaType();
        if (!TextUtils.isEmpty(mediaType) && mediaType.equalsIgnoreCase("VIDEO")) {
            contentType = 1;
        } else {
            contentType = 0;
        }

        if (NetWorkUtils.isFlux()) {
            showToast("您正在使用流量……");
        }

        appStateBroadCast = new AppStateBroadCast();
        LocalBroadcastUtils.register(getActivity(), appStateBroadCast, new IntentFilter(ConfigUtils.APP_BACKGROUND_ACTION));
    }

    @Override
    protected void initListeners() {

        chatInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = chatInputEt.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    iv_sendMsg.setImageResource(R.mipmap.send_black);
                } else {
                    iv_sendMsg.setImageResource(R.mipmap.send_red);
                }
            }
        });

        chatInputEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //监听键盘输入删除功能
                    return ImageUtils.deleteSmallText(chatInputEt);
                }
                return false;
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return new ProgressBar(mContext);
    }


    @Override
    protected void onNetworkDisConnected() {
    }

    private ArrayList<CommontBack4C> results;


    /**
     * 适配数据
     *
     * @param CommontBack4CList
     */
    private void setDataFirst(ArrayList<CommontBack4C> CommontBack4CList) {
        if (CommontBack4CList.size() > 0) {

            int numList = mList.size();

            for (CommontBack4C cb4 : CommontBack4CList) {
                VisibilityItem item = new VisibilityItem(this, false, cb4);
                mList.add(item);
            }
            mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);
//            adapter.notifyDataSetChanged();
            adapter.notifyItemChanged(numList);
        }
    }

    private void addDataMore(ArrayList<CommontBack4C> CommontBack4CList) {
        ArrayList<VisibilityItem> cb4s = new ArrayList<VisibilityItem>();
        for (CommontBack4C cb4 : CommontBack4CList) {
            VisibilityItem item = new VisibilityItem(this, false, cb4);
            cb4s.add(item);
        }
        mList.addAll(cb4s);
        adapter.notifyDataSetChanged();
        cb4s = null;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getActivity().finish();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(final BGARefreshLayout refreshLayout) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.endLoadingMore();
            }
        },2000);
        return true;
        /*//TODO 下拉刷新

        return true;*/
    }


    @Override
    public void onPause() {
//        Logger.e("C --- onPause");
        super.onPause();
        finalBitmap.clearMemory();

        MomentListAdapter.ContentHolder viewHolderForAdapterPosition = (MomentListAdapter.ContentHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        if (viewHolderForAdapterPosition != null) {
            timeToWin = viewHolderForAdapterPosition.video_player_1.getCurrentPositon();
            viewHolderForAdapterPosition.setLastTime(timeToWin);
        } else {
//            Logger.e("没有获取到 postion VIew");
        }

        mVideoPlayerManager.stopAnyPlayback();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        finalBitmap = null;
        mHandler.removeCallbacksAndMessages(null);
        mVideoPlayerManager.resetMediaPlayer();
    }

    public void scrollToPosition(int position) {
        mLayoutManager.scrollToPositionWithOffset(position, 0);
    }

    /**
     * 播放视频
     */
    @Override
    public void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition, CommontBack4C cb4) {
        final MediaBean mediaBean = cb4.getMoment().getMediaList().get(0);
        MomentListAdapter.ContentHolder holder = (MomentListAdapter.ContentHolder) newActiveView.getTag();
        if (mediaBean.getMediaType().equals("VIDEO")) {
            if (holder.video_player_1.isPlaying())
                return;

//            if(!NetWorkUtils.isFlux()){
//            }
            playVideo(holder, mediaBean.getMediaUrl());


        } else {
            mVideoPlayerManager.stopAnyPlayback();
            holder.pb_video.setVisibility(View.GONE);
        }
    }

    //点击播放的操作
    public void clickPlayVideo(MomentListAdapter.ContentHolder holder, MediaBean mediaBean) {

        playVideo(holder, mediaBean.getMediaUrl());

    }

    ;

    protected void playVideo(MomentListAdapter.ContentHolder holder, String videPath) {

        if (!NetWorkUtils.checkNetwork() && !proxy.isCached(videPath)) {
            return;
        }

        String proxyUrl = proxy.getProxyUrl(videPath);

        holder.pb_video.setVisibility(View.VISIBLE);
        holder.iv_playVideo.setVisibility(View.GONE);
        mVideoPlayerManager.playNewVideo(null, holder.video_player_1, proxyUrl, 1.00f);
    }

    public void backgroundAlpha(float bgAlpha) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        window.setAttributes(lp);
    }

    public void showLoadingC(String msg) {
        CustomProgressDialog dialog = parentAcy.getmLoadingDialog();
        dialog.setMessage(dialog, msg);
        dialog.show();
    }

    public void hideLoadingC() {
        parentAcy.getmLoadingDialog().dismiss();
    }


    public void showKeyBoard(MomentListAdapter.ContentHolder holder, FromUser fromUser) {
        showHeadView();
        commentReplyUser = fromUser;
        this.commentPostion = (int) holder.iv_more.getTag();
        this.holder = holder;
        llCKeyBoard.setVisibility(View.VISIBLE);
        closeAllKeyboardLayout();
        KeyboardUtils.showSoftInput(mContext, llCKeyBoard);
        chatInputEt.requestFocus();
        if (commentReplyUser != null) {
            chatInputEt.setHint("@" + commentReplyUser.getNickname());
        }
    }

    public void hideKeyBoard() {
        keyFirst = false;
//        Logger.e("隐藏键盘");
        KeyboardUtils.hideSoftInput(mContext, llCKeyBoard);
        llCKeyBoard.setVisibility(View.GONE);
    }

    //-----------------

    /**
     * 初始化表情布局
     */
    private void initIconLayout() {

        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Integer> mNageList = new ArrayList<>();
        mNageList.add(ConfigUtils.ICON_SMALL);
        mNageList.add(ConfigUtils.ICON_PARENT);
        iconViewpager.setAdapter(new KeyAdapter(parentAcy.getSupportFragmentManager(), mNageList));
        chatInputEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    sendTextMessage();
                    return true;
                }
                return false;
            }
        });

        SharedPreferencesUtils.getInstance(mContext).setBoolean(addImageC, true);
        SharedPreferencesUtils.getInstance(mContext).setBoolean(emojinoImageC, true);


        iconViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        iconBig.performClick();
                        break;
                    case 1:
                        iconLove.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        final View decorView = getActivity().getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
                if (isMeizu) {
                    //meizu-
                    if (viewHight == 0) {
                        viewHight = displayHight;
                    }
                    //获得键盘高度
                    keyboardHeight = hight - displayHight - statusBarHeight - (hight - viewHight);
                    Logger.e("displayHight-" + displayHight + "keyboardHeight =" + keyboardHeight);
                    if (keyboardHeight > 200) {
                        setKeyHeight();
                    }
                } else {
                    //获得键盘高度
                    int keyboardHeight = hight - displayHight;
                    boolean visible = (double) displayHight / hight < 0.8;
                    if (visible) {
                        ViewGroup.LayoutParams layoutParams = emojionRootLayout.getLayoutParams();
                        layoutParams.height = keyboardHeight - statusBarHeight;
                        emojionRootLayout.setLayoutParams(layoutParams);
                    }
                }
            }
        });
    }

    private void setKeyHeight() {
        if ("Meizu".equalsIgnoreCase(Build.MANUFACTURER) && keyboardHeight > 200) {
            if (keyFirst) {
                ViewGroup.LayoutParams layoutParams = emojionRootLayout.getLayoutParams();
//                Logger.e("第一次-- layoutParams.height="+ layoutParams.height +"键盘高度="+keyboardHeight);
                layoutParams.height = keyboardHeight;
                emojionRootLayout.setLayoutParams(layoutParams);
            } else {
                ViewGroup.LayoutParams layoutParams = emojionRootLayout.getLayoutParams();
                layoutParams.height = keyboardHeight + 45;
                emojionRootLayout.setLayoutParams(layoutParams);
            }
        } else if (keyboardHeight > 200) {
            ViewGroup.LayoutParams layoutParams = emojionRootLayout.getLayoutParams();
            layoutParams.height = keyboardHeight;
            emojionRootLayout.setLayoutParams(layoutParams);
        }
    }

    String access = "PUBLIC";

    @OnClick({R.id.iv_keyShade,R.id.iv_sendMsg, R.id.ll_CKeyBoard, R.id.iv_c_lock, R.id.add_img, R.id.icon_img, R.id.chat_input_et, R.id.ll_sendRedPack, R.id.icon_big, R.id.icon_love})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sendMsg:
                sendTextMessage();
                break;
            case R.id.iv_keyShade:
                hideKeyBoard();
                break;


            case R.id.ll_CKeyBoard:
                if (!KeyboardUtils.isShowSoftInput(mContext)) {
                    KeyboardUtils.showSoftInput(mContext, llCKeyBoard);
                }
                break;
            case R.id.add_img:


                break;
            case R.id.icon_img:
                changeEmojionLayout();
                break;
            case R.id.chat_input_et:
                closeAllKeyboardLayout();
                break;
            case R.id.ll_sendRedPack:


                break;
            case R.id.icon_big:
                iconBig.setImageResource(R.color.drak_gray);
                iconLove.setImageResource(R.color.gray_normal);
                iconViewpager.setCurrentItem(0);
                break;
            case R.id.icon_love:
                iconBig.setImageResource(R.color.gray_normal);
                iconLove.setImageResource(R.color.drak_gray);
                iconViewpager.setCurrentItem(1);
                break;
        }
    }


    private void showHeadView() {
//        if (avatarSelf == null || avatarSelf.equals("")) {
//            finalBitmap.displayCircle(IV_lock, R.mipmap.ic_launcher, true);
//        } else {
//            finalBitmap.displayCircle(IV_lock, avatarSelf, false);
//        }
    }

    private String emojinoImageC = "emojinoImageC";
    private String vioceImageC = "vioceImageC";
    private String addImageC = "addImageC";


    /**
     * 变换 emojino 表情布局
     */
    private void changeEmojionLayout() {
        keyFirst = false;
        setKeyHeight();
        SharedPreferencesUtils.getInstance(mContext).setBoolean(addImageC, true);
        if (SharedPreferencesUtils.getInstance(mContext).getBoolean(emojinoImageC)) {
//            Logger.e("显示表情");
            KeyboardUtils.hideSoftInput(mContext, llCKeyBoard);
            iconImg.setImageResource(R.mipmap.ic_launcher);
            addImg.setImageResource(R.mipmap.ic_launcher);
            emojionRootLayout.setVisibility(View.VISIBLE);
            SharedPreferencesUtils.getInstance(mContext).setBoolean(emojinoImageC, false);
            if (iconViewpager.getCurrentItem() == 0) {
            }
        } else {
            KeyboardUtils.showSoftInput(mContext, llCKeyBoard);
            iconImg.setImageResource(R.mipmap.ic_launcher);
            SharedPreferencesUtils.getInstance(mContext).setBoolean(emojinoImageC, true);
        }
    }


    /**
     * 关闭所有的键盘
     */
    private void closeAllKeyboardLayout() {
        iconImg.setImageResource(R.mipmap.ic_launcher);
        SharedPreferencesUtils.getInstance(mContext).setBoolean(emojinoImageC, true);
        SharedPreferencesUtils.getInstance(mContext).setBoolean(addImageC, true);
        addImg.setImageResource(R.mipmap.ic_launcher);
    }

    /**
     * 发送纯文本消息
     */
    private void sendTextMessage() {
        if (ClickUtils.isFastClick()) {
            Log.e("CLOG", "快速点击");
        } else {
            String s = chatInputEt.getText().toString().trim();
            if (TextUtils.isEmpty(s)) {
                showToast("输入不能为空！");

            } else {
                //发送请求
                showToast("发送评论请求！"+s);
                chatInputEt.setText("");
            }
        }
    }


    //插入评论成功的数据，并进行显示
    private void addCommentShow(int size, View view, CommontBack4C cb4) {

        TextView childAt = (TextView) holder.ll_comment.getChildAt(holder.ll_comment.getChildCount() - 1);
        if (size != 1) {
            childAt = (TextView) childAt;
            if (childAt == null) {
                childAt = new TextView(mContext);
            }

            String s = childAt.getText().toString();
            String[] split = s.split(" ");
            childAt.setText(Html.fromHtml("<font color=\"#919090\">" + split[0] + " </font>" + "<font color=\"#4A799A\">" + size + "条评论" + "</font>"));
        } else {
            holder.ll_comment.removeAllViews();
            TextView child = new TextView(mContext);
            child.setText(Html.fromHtml("<font color=\"#919090\">" + DataUtil.getFriendTimeShow(cb4.getMoment().getTimestamp()) + " </font>" + "<font color=\"#4A799A\">" + size + "</font>" + "条评论"));
            holder.ll_comment.addView(child);
        }
        holder.ll_comment.addView(view, holder.ll_comment.getChildCount() - 1);
    }

    public void Toast(String s) {
        showToast(s);
    }


    public void finish() {
        parentAcy.finish();
    }

    /**
     * 表情信息发送
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendSmaile(Object bean) {
        if (bean instanceof SmailBean4C) {
                // 直接发送大 表情
                SmailBean4C sb = (SmailBean4C) bean;
//              Toast("大 表 情 -- "+sb.getSpannable().toString());
                String emNum = sb.getSpannable().toString();
                if (mRecyclerView.isShown()) {
//                    Logger.e("MOmentList 显示，发大表情数据");
                    if ("c_key_red".equals(emNum)) {
                        showToast("红包");
                    } else {
                        showToast(""+emNum.substring(emNum.indexOf("_") + 1));
                    }
                }
        } else if (bean instanceof SmailSmallBean) {
            SmailSmallBean sb = (SmailSmallBean) bean;
            if (sb.getSpannable().equals("delete")) {
                //删除
                ImageUtils.deleteSmallText(chatInputEt);
            } else {
                chatInputEt.setText(chatInputEt.getText() + sb.getSpannable());
                chatInputEt.setSelection(chatInputEt.getText().length());
            }
        }
    }

}