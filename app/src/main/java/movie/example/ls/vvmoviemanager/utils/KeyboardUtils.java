package movie.example.ls.vvmoviemanager.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Date :  2016/3/18
 */
public class KeyboardUtils {
    /**
     * 弹出软键盘
     * @param context
     * @param view
     */
    public static void showSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static void ShowKeyboard(Context ctx,View v)    {
        InputMethodManager imm = ( InputMethodManager ) ctx.getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 软键盘的状态
     * @param context
     * @return
     */
    public static boolean isShowSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //获取状态信息
        return imm.isActive();
    }
}
