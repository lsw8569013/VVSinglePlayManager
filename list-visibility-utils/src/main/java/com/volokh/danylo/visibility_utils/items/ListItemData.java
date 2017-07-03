package com.volokh.danylo.visibility_utils.items;

import android.app.LauncherActivity;
import android.view.View;

import com.volokh.danylo.visibility_utils.utils.Config;
import com.volokh.danylo.visibility_utils.utils.Logger;

import java.util.List;


public class ListItemData {
    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = LauncherActivity.ListItem.class.getSimpleName();

    private Integer mIndexInAdapter = 0;
    private View mView;

    private boolean mIsMostVisibleItemChanged;

    public int getIndex() {
        return mIndexInAdapter;
    }

    public View getView() {
        return mView;
    }

    public ListItemData fillWithData(int indexInAdapter, View view) {
        mIndexInAdapter = indexInAdapter;
        mView = view;
        return this;
    }

    public boolean isAvailable() {
        boolean isAvailable = mIndexInAdapter != null && mView != null;
//        if(SHOW_LOGS) Log.e(TAG, "isAvailable "+this.getIndex() + isAvailable);
        return isAvailable;
    }

    public int getVisibilityPercents(List<? extends ListItem> listItems) {
        if(listItems == null)
            Logger.e("listitem", "listItems = null");
        if(getView() != null && getView()!=null && !listItems.isEmpty()){

            int visibilityPercents = listItems.get(getIndex()).getVisibilityPercents(getView());
            if(SHOW_LOGS) Logger.v(TAG, "getVisibilityPercents, visibilityPercents " + visibilityPercents);
            return visibilityPercents;
        }
        return 0;
    }

    public void setMostVisibleItemChanged(boolean isDataChanged) {
        mIsMostVisibleItemChanged = isDataChanged;
    }

    public boolean isMostVisibleItemChanged() {
        return mIsMostVisibleItemChanged;
    }

    @Override
    public String toString() {
        return "ListItemData{" +
                "mIndexInAdapter=" + mIndexInAdapter +
                ", mView=" + mView +
                ", mIsMostVisibleItemChanged=" + mIsMostVisibleItemChanged +
                '}';
    }
}
