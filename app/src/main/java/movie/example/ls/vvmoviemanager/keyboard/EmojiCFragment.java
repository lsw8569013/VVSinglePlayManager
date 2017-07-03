package movie.example.ls.vvmoviemanager.keyboard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.bean.SmailBean4C;
import movie.example.ls.vvmoviemanager.bean.SmailSmallBean;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;
import movie.example.ls.vvmoviemanager.utils.ImageUtils;

/**
 *  C 页表情
 */
public class EmojiCFragment extends Fragment implements BaseRecyclerAdapter.onItemClickListener, BaseRecyclerAdapter.onItemLongClickListener {

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter adapter;

    private List<String> mIconList;// 大表情资源文件名

    private static final String TAG = "EmojiFragment";

    private int type;//收藏类型

    private EmojiCAdapter adapter1;

    public static EmojiCFragment getInstance(int type) {
        EmojiCFragment fragment = new EmojiCFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.single_recyclerview_item, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle extras = getArguments();
        if (extras != null) {
            type = extras.getInt("type");
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.single_recycler);
        mRecyclerView.setPadding(5,30,5,15);
        mIconList = new ArrayList<>();
        switch (type) {
            case ConfigUtils.ICON_PARENT:
                mIconList = getBigExpressionResC(CIconSmileUtils.getBigSmilesSize());
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                adapter = new CIconShowAdapter(getActivity(), mIconList);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setAdapter(adapter);
                break;
            case ConfigUtils.ICON_LOVES:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                break;
            case ConfigUtils.ICON_SMALL:
                for(int i=0;i<IconSmileUtils.bigMoticonsResId.length+1;i++){

                    if(i<15){
                        mIconList.add(IconSmileUtils.bigMoticonsResId[i]+"");
                    }else if(i==34){
                        mIconList.add(R.mipmap.ease_delete_expression+"");
                    }else{
                        mIconList.add(IconSmileUtils.bigMoticonsResId[i-1]+"");
                    }
                }

                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
                adapter = new EmojiCAdapter(getActivity(), mIconList);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setAdapter(adapter);

                break;
        }

        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(type==ConfigUtils.ICON_LOVES){
        }
    }

    // 获得大表情资源文件名
    private List<String> getBigExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<>();
        for (int x = 1; x <= getSum; x++) {
            String filename;
            if (x < 10) {
                filename = "bee_0" + x;
            } else {
                filename = "bee_" + x;
            }
            reslist.add(filename);
        }
        return reslist;
    }

    // 获得带红包的大表情资源文件名
    private List<String> getBigExpressionResC(int getSum) {
        List<String> reslist = new ArrayList<>();
        for (int x = 1; x <= getSum; x++) {
            String filename;
            if (x < 11) {
                if(x == 4){
                    filename = "c_key_red";
                }else if(x < 4){
                    filename = "bee_0" + x;
                }else{
                    filename = "bee_0" + (x-1);
                }
            } else {
                filename = "bee_" + (x-1);
            }
            reslist.add(filename);
        }
        return reslist;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (type) {
            case ConfigUtils.ICON_PARENT:
                Spannable smiledTextStr = CIconSmileUtils.getSmiledTextStr(getActivity(), mIconList.get(position), 0);
                final int resId = getActivity().getResources().getIdentifier(smiledTextStr.toString(), "mipmap", getActivity().getPackageName());

                if (CIconSmileUtils.containsValue(resId)) {
                    EventBus.getDefault().post(new SmailBean4C(smiledTextStr, IconSmileUtils.getSelectSmile2Value(resId)));
                } else {
                    ToastSHORT(getActivity(), "该表情已丢失");
                }
                break;
            case ConfigUtils.ICON_SMALL:
                String smiledTextStrSmall = null;
                if(position<16){
                    smiledTextStrSmall =IconSmileUtils.getSelectSmall2Value(Integer.parseInt(mIconList.get(position)));
                }else if(position==16){
                    smiledTextStrSmall="delete";
                }else{
                    smiledTextStrSmall =IconSmileUtils.getSelectSmall2Value(Integer.parseInt(mIconList.get(position)));
                }
                EventBus.getDefault().post(new SmailSmallBean(smiledTextStrSmall, ""));

                break;

        }
    }

    private void ToastSHORT(FragmentActivity activity, String s) {
        Toast.makeText(activity,s,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemLongClick(View view, int position) {
        switch (type){

        }
    }

    private void confirmDel(final String url){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("确认操作");
        builder.setMessage("是否要删除这个收藏？");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                delLoveCollections(url);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 删除 (匹配相同的url)
     * @param url
     */
    private void delLoveCollections(String url){
        for (int i = 0; i < mIconList.size(); i++) {
            if(mIconList.get(i).equals(url)){
                mIconList.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
