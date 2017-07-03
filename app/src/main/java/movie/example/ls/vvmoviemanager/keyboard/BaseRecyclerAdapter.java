package movie.example.ls.vvmoviemanager.keyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import movie.example.ls.vvmoviemanager.glide.FinalBitmap;

/**
 * RecyclerView 通用的适配器
 * Date :  2016/10/27
 */
public abstract  class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BaseRecyclerAdapter";

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mList;
    protected FinalBitmap finalBitmap;
    protected onItemClickListener itemClickListener;
    protected onItemLongClickListener itemLongClickListener;

    public BaseRecyclerAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mList=(mDatas==null)?new ArrayList<T>():mDatas;
        mInflater=LayoutInflater.from(mContext);
        finalBitmap=FinalBitmap.create(mContext);
    }

    public  interface onItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public  interface onItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(onItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    protected abstract void onBindData(final RecyclerView.ViewHolder holder, final int position);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (itemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
        if(itemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }
        onBindData(holder,position);
    }

    @Override
    public int getItemCount() {
        int ret=0;
        if(mList!=null){
            ret=mList.size();
        }
        return ret;
    }

}

