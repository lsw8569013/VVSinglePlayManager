package movie.example.ls.vvmoviemanager.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;


import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import movie.example.ls.vvmoviemanager.R;

/**
 * 实现飞屏下拉刷新的效果
 * Date :  2016/10/31
 */
public class DetailCHeadView extends BGARefreshViewHolder {

    private Context mContext;
    private ImageView imageView;
    private ImageView imageGIF;

    public DetailCHeadView(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        this.mContext=context;
    }

    @Override
    public View getRefreshHeaderView() {
        if(mRefreshHeaderView==null){
            mRefreshHeaderView=View.inflate(mContext, R.layout.fly_refresh_layout,null);
            imageView= (ImageView) mRefreshHeaderView.findViewById(R.id.iv);
            imageGIF= (ImageView) mRefreshHeaderView.findViewById(R.id.iv_gif);
            mRefreshHeaderView.setBackgroundColor(Color.WHITE);
            imageView.setVisibility(View.INVISIBLE);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {

    }

    @Override
    public void changeToIdle() {

    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {

    }

    @Override
    public void changeToRefreshing() {
        imageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEndRefreshing() {
    }
}
