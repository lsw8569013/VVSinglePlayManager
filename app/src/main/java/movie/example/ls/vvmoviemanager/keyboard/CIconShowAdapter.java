package movie.example.ls.vvmoviemanager.keyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;

/**
 * 表情键盘展示
 */
public class CIconShowAdapter extends BaseRecyclerAdapter<String> {


    public CIconShowAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void onBindData(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof IconShowHolder){
            IconShowHolder h= (IconShowHolder) holder;
            h.iv.setVisibility(View.VISIBLE);
            if(mList.get(position).contains("storage")){
                finalBitmap.displayRound(h.iv,mList.get(position),13,0,false);
            }else if(mList.get(position).contains("http")){
                finalBitmap.displayRound(h.iv,mList.get(position)+ ConfigUtils.QINIU_THUMBURL_IMAGE,13,0,true);
            }else{
                int resId = mContext.getResources().getIdentifier(mList.get(position), "mipmap", mContext.getPackageName());
                h.iv.setImageResource(resId);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IconShowHolder(mInflater.inflate(R.layout.icon_iv_row_c,parent,false));
    }

    public static class IconShowHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        public IconShowHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv_expression);
        }
    }

}
