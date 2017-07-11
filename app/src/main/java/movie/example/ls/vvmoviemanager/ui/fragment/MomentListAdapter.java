package movie.example.ls.vvmoviemanager.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.adapter.items.VisibilityItem;
import movie.example.ls.vvmoviemanager.application.BaseApplication;
import movie.example.ls.vvmoviemanager.bean.CommentList;
import movie.example.ls.vvmoviemanager.bean.CommontBack4C;
import movie.example.ls.vvmoviemanager.bean.GeoInfo;
import movie.example.ls.vvmoviemanager.bean.MediaBean;
import movie.example.ls.vvmoviemanager.bean.PublisherProfile;
import movie.example.ls.vvmoviemanager.bean.ReleaseCommontBack;
import movie.example.ls.vvmoviemanager.glide.FinalBitmap;
import movie.example.ls.vvmoviemanager.library.imageshow.DragPhotoActivity;
import movie.example.ls.vvmoviemanager.ui.MomentListAcy;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;
import movie.example.ls.vvmoviemanager.utils.DataUtil;
import movie.example.ls.vvmoviemanager.utils.ImageUtils;
import movie.example.ls.vvmoviemanager.utils.ScreenUtils;
import movie.example.ls.vvmoviemanager.views.CImageView;


/**
 * Created by danylo.volokh on 06.01.2016.
 */
public class MomentListAdapter extends RecyclerView.Adapter<ViewHolder>  implements View.OnClickListener {

    private  int currentTime;
    private  ArrayList<CommontBack4C> videoMoments;

    private ArrayList<VisibilityItem> mList;
    private MomentListFragment momentListFrag;
    private Context mContext;
    public int screenWidth;
    private int imageWidth;
    private int HEAD_VIEW = 0;
    private int CONTENT_VIEW = 1;
    private FinalBitmap finalBitmap;
    private View headView;
    private PopupWindow selectPopWin;
    private boolean hasHead = false;
    private FragmentManager supportFragmentManager;
    private PopupWindow commentPopWin;
    private LayoutInflater inflater;

    private boolean redMoneyDialogIsShow;
    private OnMomentItemClickListener itemClickListener;
    private int scalHeight;

    public MomentListAdapter(Context activity, ArrayList<VisibilityItem> list, FinalBitmap finalBitmap, MomentListFragment momentListFrag, FragmentManager supportFragmentManager, ArrayList<CommontBack4C> videoMoments, int currentTime) {
        mList = list;
        this.finalBitmap = finalBitmap;
        this.screenWidth = ScreenUtils.getScreenWidth(activity);
        imageWidth = screenWidth / 6 > 150 ? 150 : (screenWidth / 6);
        this.momentListFrag = momentListFrag;
        this.mContext = activity;
        this.supportFragmentManager = supportFragmentManager;

        this.videoMoments = videoMoments;
        this.currentTime = currentTime;
    }

    public void setItemClickListener(OnMomentItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.moment_list_item, parent, false);
        ContentHolder contentHolder = new ContentHolder(view,currentTime);
        view.setTag(contentHolder);
        contentHolder.iv_good.setTag(contentHolder);
        return contentHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final VisibilityItem item = mList.get(position);
        final ContentHolder mHolder = (ContentHolder) holder;
        mHolder.onBindViewHolder(item.getCommontBack4C(), position);
        item.onBindViewHolder(mHolder, position, finalBitmap, screenWidth );

        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_comment:
                //评论 弹出键盘
                momentListFrag.showKeyBoard((ContentHolder) v.getTag(), null);

                break;
        }
    }


    public class ContentHolder extends ViewHolder {

        private  TextView tv_content;
        public   int currentTime;
        public ImageView firstFrame;
        public VideoPlayerView video_player_1;
        public ProgressBar pb_video;
        private ImageView iv_good;
        public ImageView iv_more;
        private ImageView iv_share;
        private ImageView iv_comment;
        private ImageView iv_showGoodGif;
        private TextView tv_likeNum;
        public TextView tv_ZFFrom;
        public View item_discovery_bottom;
        public LinearLayout ll_comment;
        public ImageView iv_playVideo;

        Boolean isAll = false;

        @BindView(R.id.iv_VideoBack)
        public ImageView iv_VideoBG;
        
        @BindView(R.id.iv_videoPause)
        public ImageView ivVideoPause;
        @BindView(R.id.tv_videoCurrentTime)
        public TextView tvVideoCurrentTime;
        @BindView(R.id.tv_videoLengthTime)
        public TextView tvVideoLengthTime;
        @BindView(R.id.sb_videoSeekBar)
        public SeekBar sb_videoSeekBar;
        @BindView(R.id.ll_pd_video)
        public RelativeLayout ll_pd_video;
        private int titileHeight = -1;
        private int oldY;
        ViewGroup.LayoutParams oldParams = new ViewGroup.LayoutParams(0,0) ;
        ViewGroup. LayoutParams scaleLP = new ViewGroup.LayoutParams(0,0);
        private int oldHeight = -1;
//        public int lastTime = 0;

        public ContentHolder(View itemView,int current) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.currentTime = current;
            iv_playVideo = (ImageView) itemView.findViewById(R.id.iv_playVideo);
            tv_ZFFrom = (TextView) itemView.findViewById(R.id.tv_ZFFrom);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_likeNum = (TextView) itemView.findViewById(R.id.tv_likeNum);
            firstFrame = (ImageView) itemView.findViewById(R.id.firstFrame);
            video_player_1 = (VideoPlayerView) itemView.findViewById(R.id.video_player_1);
            pb_video = (ProgressBar) itemView.findViewById(R.id.pb_Video);

            ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);
//            tv_forward_from = (TextView) itemView.findViewById(R.id.tv_forward_from);
            item_discovery_bottom = itemView.findViewById(R.id.ll_group_bottom);

            iv_good = (ImageView) itemView.findViewById(R.id.iv_good);
            iv_more = (ImageView) itemView.findViewById(R.id.iv_more);
            iv_share = (ImageView) itemView.findViewById(R.id.iv_share);
            iv_comment = (ImageView) itemView.findViewById(R.id.iv_comment);
            iv_showGoodGif = (ImageView) itemView.findViewById(R.id.iv_showGoodGif);
            video_player_1.setVisibility(View.GONE);
        }

        //界面数据适配
        public void onBindViewHolder(final CommontBack4C cb4, final int position) {

            video_player_1.setImageBackView(firstFrame);

            final ReleaseCommontBack moment = cb4.getMoment();


            final MediaBean mediaBean = moment.getMediaList().get(0);
            final String mediaType = mediaBean.getMediaType();

            final int width = mediaBean.getWidth();
            final int height = mediaBean.getHeight();
            int duration = mediaBean.getDuration();

            tv_content.setText("第"+position);

            video_player_1.setTag(duration);
            tvVideoLengthTime.setText(DataUtil.getMIAOFen(duration));

//            Logger.e(width +"高"+height +"time ="+duration);
            //----宽高进行适配 ，如果不是视频不显示视频播放view
            if (width == 0 || height == 0 || width < 120 || height < 120) {
                ViewGroup.LayoutParams layoutParams2 = firstFrame.getLayoutParams();
                layoutParams2.height = screenWidth * 12 / 10;
                if (mediaType.equals("VIDEO")) {
                    ViewGroup.LayoutParams layoutParams = video_player_1.getLayoutParams();
                    layoutParams.height = screenWidth * 12 / 10;
                    oldParams.height = layoutParams.height;
                    scaleLP.height = layoutParams.height;
                    video_player_1.setVisibility(View.VISIBLE);
                } else {
                    video_player_1.setVisibility(View.GONE);
                    pb_video.setVisibility(View.GONE);
                    ll_pd_video.setVisibility(View.GONE);
                    ll_pd_video.setVisibility(View.GONE);
                }
            } else {
                if (mediaType.equals("VIDEO")) {
                    video_player_1.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams layoutParams = video_player_1.getLayoutParams();
                    scaleLP = ScreenUtils.getScalWidthHeightVideoList(layoutParams, width, height, mContext);
                    oldParams = ScreenUtils.getScalWidthHeight(layoutParams, width, height, mContext);
                    video_player_1.setLayoutParams(oldParams);
                } else {
                    ll_pd_video.setVisibility(View.GONE);
                    video_player_1.setVisibility(View.GONE);
                    pb_video.setVisibility(View.GONE);
                }
                ViewGroup.LayoutParams layoutParams2 = firstFrame.getLayoutParams();
                firstFrame.setLayoutParams(ScreenUtils.getScalWidthHeight(layoutParams2, width, height, mContext));
                iv_VideoBG.setLayoutParams(ScreenUtils.getScalWidthHeight(layoutParams2, width, height, mContext));
            }


            //------------------如果是转发信息
            final PublisherProfile originalPublisherProfile = cb4.getOriginalPublisherProfile();
            if (originalPublisherProfile != null) {
                tv_ZFFrom.setText("#"+originalPublisherProfile.getNickname()+"#");
                tv_ZFFrom.setVisibility(View.VISIBLE);
            }else{
                tv_ZFFrom.setVisibility(View.GONE);
            }



            sb_videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if(b){
                        video_player_1.seekTo(i);
                        ivVideoPause.setBackgroundResource(R.mipmap.ic_video_pause);
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {                        }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {                        }
            });
            ivVideoPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(video_player_1.isPlaying()){
                        video_player_1.pause();
                        ivVideoPause.setBackgroundResource(R.mipmap.ic_video_play);
                    }else{
                        video_player_1.setVisibility(View.VISIBLE);
                        video_player_1.start();
                        firstFrame.setVisibility(View.GONE);
                        ivVideoPause.setBackgroundResource(R.mipmap.ic_video_pause);
                    }
                }
            });

            //手动点击播放按钮
            iv_playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    momentListFrag.clickPlayVideo(ContentHolder.this,cb4.getMoment().getMediaList().get(0));
                }
            });

            //------------评论 init
            final List<CommentList> textCommnets = cb4.getTextComments();
            ll_comment.removeAllViews();
            if (textCommnets != null && textCommnets.size() > 0) {
                if (textCommnets.size() >= 5) {
                    //只显示前5个
                    for (int i = 0; i < 5; i++) {
                        createCommentView(moment, textCommnets, i, "1");
                    }
                } else {
                    for (int i = 0; i < textCommnets.size(); i++) {
                        createCommentView(moment, textCommnets, i,"2");
                    }
                }
            }
            if (textCommnets.size() > 0) {
                TextView commentCountTV = createTextView(DataUtil.getFriendTimeShow(moment.getTimestamp()),  textCommnets.size() + "条评论", 0xFF909090, "");
                ll_comment.addView(commentCountTV);

            }else{
                TextView commentCountTV = createTextView(DataUtil.getFriendTimeShow(moment.getTimestamp()),  textCommnets.size() + "条评论", 0, "");
                ll_comment.addView(commentCountTV);
            }



            //------show good num
            if(cb4.getLikeCount()>0){
                tv_likeNum.setText(cb4.getLikeCount() + "");
            }else{
                tv_likeNum.setText("");
            }

            if (cb4.getLiked()) {
                String content = cb4.getLikeComments().get(0).getComment().getContent();
                if (content.equals("i")) {
                    content = "0";
                }
//                showGifGood(iv_showGoodGif,popWindow.zanFaceList[Integer.parseInt(cb4.getLikeComments().get(0).getComment().getContent())]);
                iv_showGoodGif.setVisibility(View.VISIBLE);
                iv_good.setBackgroundResource(R.mipmap.good);
            } else {
                iv_good.setVisibility(View.VISIBLE);
                iv_showGoodGif.setVisibility(View.GONE);
                iv_good.setBackgroundResource(R.mipmap.good);
            }

//            tv_forward.setVisibility(View.VISIBLE);
//            tv_forward.setText("测试转发转发转发转发转发转发转发转发转发转发");


            tv_likeNum.setTag(cb4);
            iv_share.setTag(cb4);
            iv_more.setTag(position);
            iv_comment.setTag(this);


            iv_comment.setOnClickListener(MomentListAdapter.this);




            if (mediaType.equals("VIDEO")) {
                iv_playVideo.setVisibility(View.VISIBLE);
                video_player_1.setVisibility(View.GONE);
            } else {
                iv_playVideo.setVisibility(View.GONE);
            }



            View.OnClickListener imageOnClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mediaType.equals("VIDEO")) {
//                        video_player_1.setVisibility(View.GONE);
//                        momentListFrag.toVideoAcy(video_player_1.getCurrentPositon());
                        if(video_player_1.isShown()){

                            if(titileHeight<0 ){
                                int videoHeight = video_player_1.getHeight();
                                int[] location = new int[2];
                                video_player_1.getLocationOnScreen(location);
                                oldY = location[1];
                                titileHeight = oldY - momentListFrag.statusBarHeight;
//                                Logger.e("视频原来的高度-"+oldY);
                            }
                            if((width == 0 || height ==0) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER) ){
                                momentListFrag.Toast("视频宽高获取失败！");
                                return;
                            }


                            changeVideo(video_player_1,null,ll_pd_video,iv_VideoBG,mediaBean,position);

                        }
                    } else {
                        ArrayList<String> imgUrls = new ArrayList<String>();
                        imgUrls.add(mediaBean.getMediaUrl());
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("imgurls", imgUrls);
//                        momentListFrag.startActivity(ImagePagerActivity.class, video_player_1, bundle);
                        Intent intent = new Intent(mContext, DragPhotoActivity.class);
                        int location[] = new int[2];

                        firstFrame.getLocationOnScreen(location);
                        intent.putExtra("left", location[0]);
                        intent.putExtra("top", location[1]);
                        intent.putExtra("height", firstFrame.getHeight());
                        intent.putExtra("width", firstFrame.getWidth());
                        intent.putStringArrayListExtra("imgurls", imgUrls);
                        intent.putExtra("type", momentListFrag.parentAcy.type);
                        intent.putExtra("bean", momentListFrag.parentAcy.moments);
                        intent.putExtra("position", position);

                        float heightScale = (float) (screenWidth/3) / (float) mediaBean.getWidth();
                        final int height = (int)(mediaBean.getHeight() * heightScale);
                        intent.putExtra("localUrl",imgUrls.get(0));

                        FragmentActivity activity = momentListFrag.getActivity();
                        activity.startActivity(intent);
                        activity.overridePendingTransition(0,0);
                    }
                }
            };
            firstFrame.setOnClickListener(imageOnClick);
            iv_VideoBG.setOnClickListener(imageOnClick);
            video_player_1.setOnClickListener(imageOnClick);
        }

        /**
         * 视频扩大缩小 动画
         * @param video_player_1
         * @param proView
         * @param iv_VideoBG
         * @param mediaBean
         */
        private void changeVideo(final VideoPlayerView video_player_1, final LinearLayout titleLy, final View proView, final ImageView iv_VideoBG, final MediaBean mediaBean, final int position) {
            //如果进度没有显示 扩大动画
            final ViewGroup.LayoutParams layoutParams = iv_VideoBG.getLayoutParams();
            final int screenHeight = ScreenUtils.getScreenHeight(BaseApplication.getmContext());

            if(mediaBean.getHeight() <=0){
                 scalHeight = ScreenUtils.getScalHeightVideoList(ScreenUtils.getScreenWidth(BaseApplication.getmContext()), video_player_1.getLayoutParams().height, BaseApplication.getmContext());
            }else{
                 scalHeight = ScreenUtils.getScalHeightVideoList(mediaBean.getWidth(), mediaBean.getHeight(), BaseApplication.getmContext());
            }

            if(scalHeight >screenHeight){
                scalHeight = screenHeight;
            }

//            Logger.e("现在视频高度-"+oldParams.height);
//            Logger.e("原视频计算后的高度-"+ scalHeight);

            if(!isAll ){
                //TODO 扩大视频动画
                ValueAnimator allAnimal =  ValueAnimator.ofFloat(0,1f);
                allAnimal.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedValue = (float) animation.getAnimatedValue();

                        ViewGroup.LayoutParams VBLP =  iv_VideoBG.getLayoutParams();
                        VBLP.height = (int) (screenHeight * animatedValue);
                        iv_VideoBG.setLayoutParams(VBLP);




                        if(oldHeight <= 0){
                            oldHeight = oldParams.height;
                        }

                        if(oldHeight < scalHeight){
                            ViewGroup.LayoutParams videoLP = video_player_1.getLayoutParams();
                            videoLP.height = oldHeight + (int) ((scalHeight - oldHeight) * ( animatedValue));
                            video_player_1.setLayoutParams(videoLP);
                        }
                    }
                });
                allAnimal.setDuration(500);
                allAnimal.setInterpolator(new AccelerateDecelerateInterpolator());

                allAnimal.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        ll_comment.setVisibility(View.GONE);
                        tv_ZFFrom.setVisibility(View.GONE);
                        item_discovery_bottom.setVisibility(View.GONE);
//                        iv_VideoBG.setVisibility(View.VISIBLE);

//                        rv_momentTitle.setVisibility(View.GONE);
                        fullscreen(true);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        momentListFrag.scrollToPosition(position);
//                        proView.setVisibility(View.VISIBLE);
//                        Logger.e("动画员 视频高度=="+ video_player_1.getLayoutParams().height);
                    }
                });
                allAnimal.start();

                isAll = true;
            }else{
                //进度没有显示 缩小动画
                if(proView.isShown()){

                    ValueAnimator smallAnimal =  ValueAnimator.ofFloat(0,1f);
                    smallAnimal.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();

                            //背景高度缩到原来的值
                            ViewGroup.LayoutParams VBLP =  iv_VideoBG.getLayoutParams();
                            VBLP.height = video_player_1.getHeight() +(int) ((screenHeight - video_player_1.getHeight()) * (1 -animatedValue));
                            iv_VideoBG.setLayoutParams(VBLP);

//                            //视频移到到原来的位置
//                            ViewHelper.setTranslationY(video_player_1, - (int) ((finishY -  oldY ) * animatedValue));

//                            ViewGroup.LayoutParams titleLP = rv_momentTitle.getLayoutParams();
//                            titleLP.height = (int) (titileHeight * (animatedValue));
//                            rv_momentTitle.setLayoutParams(titleLP);

                            //长视频 恢复到原始高度
                            if(oldHeight < scalHeight){
                                ViewGroup.LayoutParams videoLP = video_player_1.getLayoutParams();
                                videoLP.height = oldHeight + (int) ((scalHeight - oldHeight) * (1- animatedValue));
                                video_player_1.setLayoutParams(videoLP);
                            }
                        }
                    });
                    smallAnimal.setDuration(500);
                    smallAnimal.setInterpolator(new AccelerateDecelerateInterpolator());

                    smallAnimal.addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
//                            rv_momentTitle.setVisibility(View.VISIBLE);
                            ll_comment.setVisibility(View.VISIBLE);

                            if(!tv_ZFFrom.getText().toString().trim().equals("")){
                                tv_ZFFrom.setVisibility(View.VISIBLE);
                            }

                            item_discovery_bottom.setVisibility(View.VISIBLE);
//                            proView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            iv_VideoBG.setVisibility(View.GONE);
//                            Logger.e("缩小动画后 视频高度=="+ video_player_1.getLayoutParams().height);
                            fullscreen(false);
//                            proView.setVisibility(View.VISIBLE);
                        }
                    });
                    smallAnimal.start();
                    isAll = false;
                }else{
                    proView.setVisibility(View.VISIBLE);
                }
            }
        }



        public TextView createTextView(String time, String s, int i, String content) {
            TextView child = new TextView(mContext);
//        child.setTextColor(0xFF919090);
            if(i == 0){
                child.setText(Html.fromHtml("<font color=\"#919090\">" + time + " </font>"));
            }else{
                child.setText(Html.fromHtml("<font color=\"#919090\">" + time + " </font>"+"<font color=\"#4A799A\">" + s + "</font>" + content));
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                p.setMargins(0, 15, 0, 10);//4个参数按顺序分别是左上右下
                child.setLayoutParams(p);
            }
            child.setTextSize(15);

            return child;
        }

        private void createCommentView(ReleaseCommontBack moment, List<CommentList> textCommnets, int i, String userId) {
            final CommentList commentList = textCommnets.get(i);
            if (commentList.getComment().getAccessType().equals("PRIVATE")) {
                //如果是自己的瞬间 ，或者是自己发的评论，就显示私密评论
                if (!userId.equals(BaseApplication.getUserID()) && !commentList.getFromUser().getUserId().equals(BaseApplication.getUserID())) {
//                    Logger.e(commentList.getComment().getContent()+" --条不显示");
                    return;
                }
            }
            View child = getCommentView(this, moment, textCommnets, commentList, ll_comment);
            ll_comment.addView(child);
        }

        public void setLastTime(int lastTime) {
            this.currentTime = lastTime;
        }
    }

    //----创建一条 评论
    @NonNull
    public View getCommentView(final ContentHolder contentHolder, final ReleaseCommontBack moment, final List<CommentList> textCommnets, final CommentList commentList, final LinearLayout ll_comment) {

        View child = inflater.inflate(R.layout.coment_item_ll, null);

        TextView tv_comentName = (TextView) child.findViewById(R.id.tv_comentName);
        ImageView iv_commentEmo = (ImageView) child.findViewById(R.id.iv_commentEmo);
        final TextView tv_comentContent = (TextView) child.findViewById(R.id.tv_comentContent);
        View iv_commentRedPack = child.findViewById(R.id.iv_commentRedPack);

        //如果是加密 显示小缩图标
        ImageView iv_lock_comment = (ImageView) child.findViewById(R.id.iv_lock_comment);
        String content = commentList.getComment().getContent();
        if (commentList.getComment().getAccessType().equals("PRIVATE")) {
            iv_lock_comment.setVisibility(View.VISIBLE);
            if(content != null && content.contains("[") && content.contains("]")){
                tv_comentContent.setPadding(0,0,0, ImageUtils.dipToPX(BaseApplication.getmContext(),2.7f));
            }
        }

        String nameStr ;
        if (commentList.getReplyUser() != null) {
            tv_comentName.setText(commentList.getFromUser().getNickname() + Html.fromHtml("<font color=\"#4A799A\">@" + commentList.getReplyUser().getNickname() + "</font>") + " : ");
            nameStr = commentList.getFromUser().getNickname() + "@"+commentList.getReplyUser().getNickname() /*+ " : "*/;
        } else {
            tv_comentName.setText(commentList.getFromUser().getNickname()/* + " : "*/);
            nameStr = commentList.getFromUser().getNickname()/*+" : "*/;
        }

        final View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        };

        final String type = commentList.getComment().getType();
        if (type.equals(ConfigUtils.COMMENT_TYPE_TEXT_COMMENT)) {
//            setCommentText(contentHolder, commentList, tv_comentContent, nameStr, l, content,ll_comment,textCommnets,moment.getId());
            tv_comentName.setVisibility(View.GONE);
//            setCommentText(contentHolder, commentList, tv_comentName, tv_comentContent, nameStr, l);

        } else if (type.equals(ConfigUtils.COMMENT_TYPE_EMOTICON_COMMENT)) {
            iv_commentEmo.setVisibility(View.VISIBLE);
            if(content.length() == 1){
//                iv_commentEmo.setBackgroundResource(IconSmileUtils.getDrawResourceID(BaseApplication.getmContext(), "bee_0" + content));
            }else{
//                iv_commentEmo.setBackgroundResource(IconSmileUtils.getDrawResourceID(BaseApplication.getmContext(), "bee_" + content));
            }
        } else if (type.equals(ConfigUtils.COMMENT_TYPE_REDPACKET_COMMENT)) {
            tv_comentContent.setText(content);
            iv_commentRedPack.setVisibility(View.VISIBLE);
//            Drawable nav_up = mContext.getResources().getDrawable(R.mipmap.c_redpack);
//            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
//            tv_comentContent.setCompoundDrawables(null, null, nav_up, null);
        }else if(type.equals(ConfigUtils.COMMENT_TYPE_MEDIA_COMMENT)){
            //图片评论
            final CImageView civ_coment = (CImageView) child.findViewById(R.id.civ_coment);
            civ_coment.setVisibility(View.VISIBLE);
            if(null ==commentList.getComment().getContent() || commentList.getComment().getContent().equals("") ){
                tv_comentName.setVisibility(View.VISIBLE);
            }else{
                tv_comentName.setVisibility(View.GONE);
            }
            final MediaBean media = commentList.getComment().getMedia();

            ViewGroup.LayoutParams layoutParams2 = civ_coment.getLayoutParams();
            civ_coment.setLayoutParams(ScreenUtils.getScalWidthHeightComment(layoutParams2, media.getWidth(), media.getHeight(), mContext));

            final String smallUrl = media.getMediaUrl() + "?imageView/1/w/" + layoutParams2.width + "/h/"+ layoutParams2.height;
            finalBitmap.displayC(civ_coment,smallUrl,media.getMediaUrl());

            if(content !=null && !"".equals(content)){
//                setCommentText(contentHolder, commentList, tv_comentContent, nameStr, l, content,ll_comment,textCommnets,moment.getId());
            }else{
                tv_comentContent.setText("                                            ");
            }

            civ_coment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    final Intent intent = new Intent(mContext, DragPhotoActivity.class);
//                    int location[] = new int[2];
//
//                    final ArrayList<String> imgUrls = new ArrayList<String>();
//                    imgUrls.add(media.getMediaUrl());
//                    civ_coment.getLocationOnScreen(location);
//                    intent.putExtra("left", location[0]);
//                    intent.putExtra("top", location[1]);
//                    intent.putExtra("height", civ_coment.getHeight());
//                    intent.putExtra("width", civ_coment.getWidth());
//                    intent.putExtra("smallW",screenWidth/3);
//
//                    intent.putStringArrayListExtra("imgurls", imgUrls);
//                    intent.putExtra("localUrl",smallUrl);
//                    momentListFrag.startActivityImage(intent);
                }
            });
        }

        if (commentList.getFromUser().getUserId().equals(BaseApplication.getUserID())) {
            if (!type.equals(ConfigUtils.COMMENT_TYPE_REDPACKET_COMMENT) && !type.equals(ConfigUtils.COMMENT_TYPE_TEXT_COMMENT)) {
                tv_comentContent.setOnClickListener(l);
            }
            iv_commentEmo.setOnClickListener(l);
        } else {
            if (!type.equals(ConfigUtils.COMMENT_TYPE_REDPACKET_COMMENT) && !type.equals(ConfigUtils.COMMENT_TYPE_TEXT_COMMENT)) {

                tv_comentContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        momentListFrag.showKeyBoard(contentHolder, commentList.getFromUser());
                    }
                });
                iv_commentEmo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        momentListFrag.showKeyBoard(contentHolder, commentList.getFromUser());
                    }
                });
            }
        }

        if (type.equals(ConfigUtils.COMMENT_TYPE_REDPACKET_COMMENT)) {
            View.OnClickListener redClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //自己的评论下,才可以领红包
                    if (moment.getPublisherId().equals(BaseApplication.getUserID())) {
                    } else {
//                        Toast("")
                    }
                }
            };
            tv_comentContent.setOnClickListener(redClick);
            iv_commentRedPack.setOnClickListener(redClick);
        }



        return child;
    }












    private void disPlayImage(CImageView view, MediaBean mediaBean, boolean b, int imageWidth) {
        if (mediaBean.getMediaType().equals("IMAGE")) {
            finalBitmap.display(view, mediaBean.getMediaUrl() + "?imageView/1/w/" + imageWidth + "/h/" + imageWidth, true);
        } else {
            finalBitmap.display(view, mediaBean.getMediaUrl() + "?vframe/jpg/offset/0" + "/w/" + imageWidth + "/h/" + imageWidth, true);
        }
    }

    private View findSearchView(int id) {
        return headView.findViewById(id);
    }

    ;


    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }



    public void backgroundAlpha(float bgAlpha) {
        momentListFrag.backgroundAlpha(bgAlpha);
    }

    public interface OnMomentItemClickListener {
        void onItemClick(int position);
    }
    private void fullscreen(boolean enable) {
        Window window = ((MomentListAcy) mContext).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (enable) { //显示状态栏
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(lp);
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else { //隐藏状态栏
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(lp);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }
}