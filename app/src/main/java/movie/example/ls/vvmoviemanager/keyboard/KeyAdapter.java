package movie.example.ls.vvmoviemanager.keyboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager 键盘 通用适配器
 * Date :  2016/11/14
 */
public class KeyAdapter extends FragmentStatePagerAdapter {

    private List<Integer> mList;

    public KeyAdapter(FragmentManager fm, List<Integer> list) {
        super(fm);
        this.mList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return EmojiCFragment.getInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}
