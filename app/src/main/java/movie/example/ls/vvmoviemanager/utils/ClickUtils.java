package movie.example.ls.vvmoviemanager.utils;

import android.os.SystemClock;

/**
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/6/27
 * Email : lsw
 */
public class ClickUtils {

    private static long TIME_SPACE=500;
    private static long lastClickTime;

    /**
     * 检测是否快速点击
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < TIME_SPACE) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 谷歌---统计点击次数
     * @param num
     * @return
     */
    public synchronized static  boolean onClickForNum(int num) {
        //数组存储点击次数
        long[] mHits = new long[num];
        //实现双击方法
        //src 拷贝的源数组
        //srcPos 从源数组的那个位置开始拷贝.
        //dst 目标数组
        //dstPos 从目标数组的那个位子开始写数据
        //length 拷贝的元素的个数
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于500，即双击
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - TIME_SPACE)) {
            return true;
        }
         return false;
    }
}
