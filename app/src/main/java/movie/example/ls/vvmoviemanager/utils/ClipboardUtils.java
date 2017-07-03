package movie.example.ls.vvmoviemanager.utils;

import android.content.Context;
import android.text.ClipboardManager;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/4/12
 * Email : lsw
 */
public class ClipboardUtils {

    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 剪贴板--复制黏贴
     * @param mContext
     * @param text
     */
    public static void CopyBord(Context mContext,String text){
        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
    }
}
