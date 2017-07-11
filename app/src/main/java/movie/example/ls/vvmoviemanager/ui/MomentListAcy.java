package movie.example.ls.vvmoviemanager.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.base.BaseActivity;
import movie.example.ls.vvmoviemanager.bean.CommontBack4C;
import movie.example.ls.vvmoviemanager.ui.fragment.MomentListAdapter;
import movie.example.ls.vvmoviemanager.ui.fragment.MomentListFragment;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;

/**
 * 仿C页 瞬间列表
 *
 *
 * Created by Administrator on 2017/1/4.
 */
public class MomentListAcy extends BaseActivity {


    private FragmentManager mFManager;
    private Bundle bundle;
    public ArrayList<CommontBack4C> moments = new ArrayList<CommontBack4C>();
    public int type;
    public int page;
    public String userId;
    private String title;
    private ImageView ivHead;
    public ArrayList<CommontBack4C> tempMoments=new ArrayList<>();
    public ArrayList<CommontBack4C> videoMoments = new ArrayList<CommontBack4C>();
    private MomentListFragment fragment;
    public int currentTime = 0;
    private boolean single;

    @Override
    protected void setWindowsParams() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return  R.layout.acy_moment_list;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

        String s = "[{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":1,\"liked\":false,\"moment\":{\"accessScope\":\"PUBLIC\",\"content\":\"#Traveling+light#\",\"id\":\"274890309746\",\"mediaList\":[{\"duration\":209,\"height\":1266,\"mediaType\":\"IMAGE\",\"mediaUrl\":\"http://ofpdqsygx.bkt.clouddn.com/2473901170693-7f457bf1-3746-4ec0-a37f-13739d763f6a\",\"width\":852}],\"publisherId\":\"274877915148\",\"rev\":\"1\",\"timestamp\":\"1498639170309\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/headImg/2016/11/24/20161124151758532852527.jpg\",\"nickname\":\"mojieAzuo\",\"userId\":\"274877915148\"},\"textComments\":[]},{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":2,\"liked\":false,\"moment\":{\"accessScope\":\"FOLLOW_ONLY\",\"content\":\"视频\",\"geoInfo\":{\"addr\":\"\",\"des\":\"\",\"lat\":\"\",\"lng\":\"\"},\"id\":\"1374401945667\",\"mediaList\":[{\"duration\":287,\"height\":268,\"mediaType\":\"VIDEO\",\"mediaUrl\":\"http://cdn3.tumbo.com.cn/2473901171480-0053a3c0-8131-4a7b-a269-dc48fa63669b\",\"width\":480}],\"publisherId\":\"1374389539878\",\"rev\":\"0\",\"timestamp\":\"1498636438033\"},\"originalPublisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/library/library_moment_image/2016/11/02/20161102163350222867888.jpg\",\"nickname\":\"意图与\",\"userId\":\"2473901171480\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/head_portrait/systemHeadImg.png\",\"nickname\":\"ZhxIHnChqTAi\",\"userId\":\"1374389539878\"},\"textComments\":[]}]";
        List<CommontBack4C> cb = new Gson().fromJson(s, new TypeToken<ArrayList<CommontBack4C>>() {
        }.getType());
        bundle = extras;
        tempMoments.clear();
        if(cb!=null){
            tempMoments.addAll(cb) ;
        }
        type = extras.getInt("type");
        page = extras.getInt("page");
        single = extras.getBoolean("single");

        if (ConfigUtils.TYPE_MIMEFRAGMENT == type){
            userId = extras.getString("userId");
        }

        ArrayList<CommontBack4C> cb42 = (ArrayList<CommontBack4C>) extras.getSerializable("module");
        int mtype = extras.getInt("mtype");
        currentTime = extras.getInt("time");//当前视频 进度时间
//        Logger.e("视频进度————"+currentTime);
        int pos = extras.getInt("pos");//当前视频
        if(cb42!=null){
            tempMoments.add(cb42.get(pos));
        }

        for (CommontBack4C cb4 : tempMoments) {
            if(cb4.getMoment().getMediaList().get(0).getMediaType().equals("VIDEO")){
                videoMoments.add(cb4);
            }
        }
        if(tempMoments.size()>0){
            moments.add(tempMoments.get(0));
        }

    }

    private int timeToWin = 0;



    @Override
    protected void onPause() {
        super.onPause();

        if (fragment!=null) {
            RecyclerView recyclerView = fragment.getmRecyclerView();
            if(recyclerView!=null){
                MomentListAdapter.ContentHolder viewHolderForAdapterPosition = (MomentListAdapter.ContentHolder) recyclerView.findViewHolderForAdapterPosition(0);
                if(viewHolderForAdapterPosition != null){
                    timeToWin = viewHolderForAdapterPosition.video_player_1.getCurrentPositon();
//                    Logger.e("没有获取到 postion VIew" +timeToWin );
                }
            }
        }

    }

    @Override
    protected void initDataAndViews(Bundle savedInstanceState) {

        String s = "[{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":2,\"liked\":false,\"moment\":{\"accessScope\":\"FOLLOW_ONLY\",\"content\":\"视频\",\"geoInfo\":{\"addr\":\"\",\"des\":\"\",\"lat\":\"\",\"lng\":\"\"},\"id\":\"1374401945667\",\"mediaList\":[{\"duration\":287,\"height\":1266,\"mediaType\":\"IMAGE\",\"mediaUrl\":\"http://ofpdqsygx.bkt.clouddn.com/2473901170693-7f457bf1-3746-4ec0-a37f-13739d763f6a\",\"width\":852}],\"publisherId\":\"1374389539878\",\"rev\":\"0\",\"timestamp\":\"1498636438033\"},\"originalPublisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/library/library_moment_image/2016/11/02/20161102163350222867888.jpg\",\"nickname\":\"意图与\",\"userId\":\"2473901171480\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/head_portrait/systemHeadImg.png\",\"nickname\":\"ZhxIHnChqTAi\",\"userId\":\"1374389539878\"},\"textComments\":[]},{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":1,\"liked\":false,\"moment\":{\"accessScope\":\"PUBLIC\",\"content\":\"#Traveling+light#\",\"id\":\"274890309746\",\"mediaList\":[{\"duration\":209,\"height\":432,\"mediaType\":\"VIDEO\",\"mediaUrl\":\"http://cdn3.tumbo.com.cn/library/library_moment_video/2016/11/12/20161112085843343817788.mp4\",\"width\":768}],\"publisherId\":\"274877915148\",\"rev\":\"1\",\"timestamp\":\"1498639170309\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/headImg/2016/11/24/20161124151758532852527.jpg\",\"nickname\":\"mojieAzuo\",\"userId\":\"274877915148\"},\"textComments\":[]},{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":1,\"liked\":false,\"moment\":{\"accessScope\":\"PUBLIC\",\"content\":\"#Traveling+light#\",\"id\":\"274890309746\",\"mediaList\":[{\"duration\":209,\"height\":432,\"mediaType\":\"VIDEO\",\"mediaUrl\":\"http://cdn3.tumbo.com.cn/library/library_moment_video/2016/11/12/20161112085843343817788.mp4\",\"width\":768}],\"publisherId\":\"274877915148\",\"rev\":\"1\",\"timestamp\":\"1498639170309\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/headImg/2016/11/24/20161124151758532852527.jpg\",\"nickname\":\"mojieAzuo\",\"userId\":\"274877915148\"},\"textComments\":[]},{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":1,\"liked\":false,\"moment\":{\"accessScope\":\"PUBLIC\",\"content\":\"#Traveling+light#\",\"id\":\"274890309746\",\"mediaList\":[{\"duration\":209,\"height\":432,\"mediaType\":\"VIDEO\",\"mediaUrl\":\"http://cdn3.tumbo.com.cn/library/library_moment_video/2016/11/12/20161112085843343817788.mp4\",\"width\":768}],\"publisherId\":\"274877915148\",\"rev\":\"1\",\"timestamp\":\"1498639170309\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/headImg/2016/11/24/20161124151758532852527.jpg\",\"nickname\":\"mojieAzuo\",\"userId\":\"274877915148\"},\"textComments\":[]},{\"commentCount\":0,\"goodID\":0,\"likeComments\":[],\"likeCount\":2,\"liked\":false,\"moment\":{\"accessScope\":\"FOLLOW_ONLY\",\"content\":\"视频\",\"geoInfo\":{\"addr\":\"\",\"des\":\"\",\"lat\":\"\",\"lng\":\"\"},\"id\":\"1374401945667\",\"mediaList\":[{\"duration\":287,\"height\":1266,\"mediaType\":\"IMAGE\",\"mediaUrl\":\"http://ofpdqsygx.bkt.clouddn.com/2473901170693-7f457bf1-3746-4ec0-a37f-13739d763f6a\",\"width\":852}],\"publisherId\":\"1374389539878\",\"rev\":\"0\",\"timestamp\":\"1498636438033\"},\"originalPublisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/library/library_moment_image/2016/11/02/20161102163350222867888.jpg\",\"nickname\":\"意图与\",\"userId\":\"2473901171480\"},\"publisherProfile\":{\"avatar\":\"http://cdn3.tumbo.com.cn/head_portrait/systemHeadImg.png\",\"nickname\":\"ZhxIHnChqTAi\",\"userId\":\"1374389539878\"},\"textComments\":[]}]";
        tempMoments = new Gson().fromJson(s, new TypeToken<ArrayList<CommontBack4C>>() {
        }.getType());


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 防止锁屏
        mFManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFManager.beginTransaction();
        fragment = new MomentListFragment();
        transaction.add(R.id.main_container, fragment, "momentList");
        transaction.commit();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finalBitmap.clearMemory();
    }
}
