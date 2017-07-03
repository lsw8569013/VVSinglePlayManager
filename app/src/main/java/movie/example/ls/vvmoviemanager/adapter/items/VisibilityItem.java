package movie.example.ls.vvmoviemanager.adapter.items;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.volokh.danylo.video_player_manager.Config;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.utils.Logger;
import com.volokh.danylo.visibility_utils.items.ListItem;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.bean.CommontBack4C;
import movie.example.ls.vvmoviemanager.bean.MediaBean;
import movie.example.ls.vvmoviemanager.glide.FinalBitmap;
import movie.example.ls.vvmoviemanager.ui.fragment.MomentListAdapter;
import movie.example.ls.vvmoviemanager.utils.DataUtil;

/**
 * Created by danylo.volokh on 06.01.2016.
 */
public class VisibilityItem implements ListItem {

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;

    private  String imageVideoUrl;
    private final Rect mCurrentViewRect = new Rect();
    private  ItemCallback mItemCallback;
    private  String videoUrl;

    private CommontBack4C commontBack4C;
    private  Boolean isHead;
    private int itemPosition;

    public VisibilityItem(ItemCallback callback,Boolean isHead ,CommontBack4C cb4) {
        mItemCallback = callback;
        this.isHead = isHead;
        this.commontBack4C = cb4;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position, final FinalBitmap finalBitmap, final int width) {
        this.itemPosition = position;
         if(holder1 instanceof  MomentListAdapter.ContentHolder){
            final MomentListAdapter.ContentHolder mHolder = (MomentListAdapter.ContentHolder)holder1 ;

            MediaBean mediaBean = commontBack4C.getMoment().getMediaList().get(0);
            float heightScale = (float) width / (float) mediaBean.getWidth();
            int height = (int)(mediaBean.getHeight() * heightScale);
            if(mediaBean.getMediaType().equals("IMAGE") ){
//                if(width>0 && height >0){
//                    imageVideoUrl = mediaBean.getMediaUrl()+"?imageView/0/w/"+width+"/h/" + height;
//                }else{
//                    imageVideoUrl = mediaBean.getMediaUrl()+"?imageView/0/w/"+350+"/h/"+350;
//                }
                imageVideoUrl = mediaBean.getMediaUrl();
                finalBitmap.displayC(mHolder.firstFrame,imageVideoUrl,imageVideoUrl);
            }else{
                imageVideoUrl = mediaBean.getMediaUrl()+"?vframe/jpg/offset/0";
                setPlayCallBack(position, mHolder);
//                mHolder.iv_playVideo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mItemCallback.onActiveViewChangedActive(holder1.itemView,position,commontBack4C);
//                    }
//                });
                if(position == 0){
                    mItemCallback.onActiveViewChangedActive(holder1.itemView,position,commontBack4C);
                }
                finalBitmap.displayCVideo(mHolder.firstFrame,imageVideoUrl,mediaBean);
            }
        }
    }


    //---仿C页 momentList play监听
    private void setPlayCallBack(final int position, final MomentListAdapter.ContentHolder mHolder) {
        mHolder.video_player_1.addMediaPlayerListener(new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
            @Override
            public void onVideoSizeChangedMainThread(int width, int height) {
            }

            @Override
            public void onVideoPreparedMainThread() {
                mHolder.video_player_1.setVisibility(View.VISIBLE);
                mHolder.iv_playVideo.setVisibility(View.GONE);

//                mHolder.pb_video.setVisibility(View.GONE);
                //小窗口传过来的时间
                if(mHolder.currentTime != 0 ){
                    //小窗口穿过来的进度
                    mHolder.video_player_1.startSeekTo(mHolder.currentTime);
                }
            }
            @Override
            public void onVideoCompletionMainThread() {
                Logger.e("------------CompletionMainThread ++");
//                    mHolder.firstFrame.setVisibility(View.VISIBLE);
                mHolder.video_player_1.setVisibility(View.GONE);
            }

            @Override
            public void onErrorMainThread(int what, int extra) {
                Logger.e("------------播放失败！ ++ "+position);
                mHolder.video_player_1.setVisibility(View.GONE);
                mHolder.pb_video.setVisibility(View.GONE);
                mHolder.iv_playVideo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBufferingUpdateMainThread(int percent) {
                if(percent == -100 ){
                    mHolder.pb_video.setVisibility(View.GONE);
                    mHolder.ivVideoPause.setBackgroundResource(R.mipmap.ic_video_pause);
                }else if(percent == -200){
                    mHolder.pb_video.setVisibility(View.VISIBLE);
                    mHolder.ivVideoPause.setBackgroundResource(R.mipmap.ic_video_pause);
                }else if(percent<96){
                    mHolder.sb_videoSeekBar.setSecondaryProgress(percent);
                }else{
                    mHolder.sb_videoSeekBar.setSecondaryProgress(100);
                }
            }

            @Override
            public void onVideoStoppedMainThread() {
//                    Logger.e("++  StoppedMainThread加载");
                mHolder.pb_video.setVisibility(View.GONE);
                // Show the cover when video stopped
                mHolder.video_player_1.setVisibility(View.GONE);
                mHolder.iv_playVideo.setVisibility(View.GONE);
            }

            @Override
            public void onVideoPlayTimeChanged(int currentPosition) {
                mHolder.tvVideoCurrentTime.setText(DataUtil.getMIAOFen(mHolder.video_player_1.getCurrentPositon()/1000));
                int progress = (int) (100f *  ((double)currentPosition /1000  / (double) (int)mHolder.video_player_1.getTag()));
//                Logger.e("进度-progress"+progress +" 当前值-"+currentPosition /1000 +"时间"+ (int)mHolder.video_player_1.getTag());
                mHolder.sb_videoSeekBar.setProgress(progress);
            }
        });
    }



    public String getVideoUrl() {
        return videoUrl;
    }

    public interface ItemCallback {
        void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition, CommontBack4C cb4);
    }



    @Override
    public int getVisibilityPercents(View view) {
//        if(SHOW_LOGS) Logger.v(TAG, ">> getVisibilityPercents view " + view);

        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
//        if(SHOW_LOGS) Logger.v(TAG, "getVisibilityPercents mCurrentViewRect top " + mCurrentViewRect.top + ", left " + mCurrentViewRect.left + ", bottom " + mCurrentViewRect.bottom + ", right " + mCurrentViewRect.right);

        int height = view.getHeight();
//        if(SHOW_LOGS) Logger.v(TAG, "getVisibilityPercents height " + height);

        if (viewIsPartiallyHiddenTop()) {
            // view is partially hidden behind the top edge
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

//        if(SHOW_LOGS) Logger.v(TAG, "<< getVisibilityPercents, percents " + percents);

        return percents;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
//        if(SHOW_LOGS) Logger.err("VisibilityItem", "setActive, newActiveViewPosition " + newActiveViewPosition);
        //播放视频
        if (newActiveView != null /*&& newActiveViewPosition != 0*/) {
            mItemCallback.onActiveViewChangedActive(newActiveView, newActiveViewPosition, commontBack4C);
        }
    }


    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    @Override
    public void deactivate(View currentView, int position) {
        Logger.e("VisibilityItem" + "  deactivate: ----- " + position);
        if (position != 0) {
            currentView.findViewById(R.id.firstFrame).setVisibility(View.GONE);
        }
    }

    public static boolean isShowLogs() {
        return SHOW_LOGS;
    }

    public Boolean getHead() {
        return isHead;
    }

    public void setHead(Boolean head) {
        isHead = head;
    }

    public CommontBack4C getCommontBack4C() {
        return commontBack4C;
    }

    public void setCommontBack4C(CommontBack4C commontBack4C) {
        this.commontBack4C = commontBack4C;
    }
}
