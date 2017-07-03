package movie.example.ls.vvmoviemanager.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.application.BaseApplication;


/**
 * 对话框工具类
 */
public class DialogUtils {
    /**
     * DialogUtils实例
     */
    private static DialogUtils mDialogUtils;

    /**
     * Dialog实例
     */
    private Dialog mDialog;

    /**
     * 进度对话框
     */
    private ProgressDialog mProgress;

    /**
     * Toast
     */
    private Toast mToast, mDefineToast;

    /**
     * 字体窗口布局
     */
    private View defineView;

    /**
     * 字体大小
     */
    private TextView defineText;

    /**
     * 获取DialogUtils单例
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static DialogUtils getInstance() {
        if (mDialogUtils == null) {
            mDialogUtils = new DialogUtils();
        }
        return mDialogUtils;
    }

    /**
     * 销毁DialogUtils
     */
    public static void destroyDialogUtils() {
        if (mDialogUtils != null && defineToast != null) {
            mDialogUtils = null;
            defineToast = null;
        }
    }

    /**
     * 关闭提示对话框
     *
     * @see [类、类#方法、类#成员]
     */
    public void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 关闭提示对话框
     *
     * @see [类、类#方法、类#成员]
     */
    public void closeProgressDialog() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    /**
     * 显示Toast信息
     *
     * @see [类、类#方法、类#成员]
     */
    public void showToast(Context context, String message) {
        if (context == null) {
            return;
        }
        //		if (mToast == null)
        //		{
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        //		}
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }


    private static String oldMsg;
    protected static Toast toast = null;
    protected static Toast defineToast = null;
    protected static Toast defineToast1 = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * 显示Toast信息---多次点击只显示一次
     *
     * @see [类、类#方法、类#成员]
     */

    public void showToast1(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 250);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            toast.setGravity(Gravity.CENTER, 0, 250);
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 显示Toast信息 在中间   且多次点击只显示一次
     *
     * @see [类、类#方法、类#成员]
     */
    public void showToastCenter(Context context, String message) {
        if (context == null) {
            return;
        }
        if (defineView == null) {
            defineView = LayoutInflater.from(context).inflate(
                    R.layout.toast_define_center, null);
            defineText = (TextView) defineView.findViewById(R.id.toast_text);
        }
        if (defineToast == null) {
            defineToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            defineToast.setView(defineView);
            defineToast.setGravity(Gravity.CENTER, 0, 0);
            defineToast.setDuration(Toast.LENGTH_SHORT);
            defineText.setText(message);
            defineToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            defineToast.setGravity(Gravity.CENTER, 0, 0);
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    defineToast.show();
                }
            } else {
                oldMsg = message;
                defineText.setText(message);
                defineToast.show();
            }
        }

        oneTime = twoTime;

    }

    /**
     * 显示Toast信息 在中间   且多次点击只显示一次   无自定布局
     *
     * @see [类、类#方法、类#成员]
     */
    public void showToastCenter1(Context context, String message) {
        if (context == null) {
            return;
        }
        if (defineToast1 == null) {
            defineToast1 = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            defineToast1.setGravity(Gravity.CENTER, 0, 0);
            defineToast1.setDuration(Toast.LENGTH_SHORT);
            defineToast1.setText(message);
            defineToast1.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            defineToast1.setGravity(Gravity.CENTER, 0, 0);
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    defineToast1.show();
                }
            } else {
                oldMsg = message;
                defineToast1.setText(message);
                defineToast1.show();
            }
        }

        oneTime = twoTime;

    }


    /**
     * 关闭Popup窗口
     *
     * @param mPopup PopupWindow
     * @see [类、类#方法、类#成员]
     */
    public void displayPopup(PopupWindow mPopup) {
        if (mPopup != null) {
            if (mPopup.isShowing()) {
                mPopup.dismiss();
            }
        }
    }

    public void showDefineToast(Context context, String text) {
        if (defineView == null) {
            defineView = LayoutInflater.from(context).inflate(R.layout.toast_define, null);
            defineText = (TextView) defineView.findViewById(R.id.toast_text);
        }
        if (mDefineToast == null) {
            mDefineToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            mDefineToast.setDuration(Toast.LENGTH_SHORT);
            mDefineToast.setView(defineView);
        }
        defineText.setText(text);
        mDefineToast.show();

    }

    /**
     * 显示Toast信息
     *
     * @see [类、类#方法、类#成员]
     */
    public void showToast(Context context, int msgRes) {
        if (context != null) {

            //			if (mToast == null)
            //			{
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            //			}
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(msgRes);
            mToast.show();

        }
    }

    public ProgressDialog createProgress(int resId, Context m) {
        ProgressDialog dialog = new ProgressDialog(m);
        dialog.getWindow().setWindowAnimations(R.style.popupAnimationAlpha);
        dialog.setMessage(m.getResources().getString(resId));
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 自定义带深色背景toast
     *
     * @param type 类型 text:单文字 image:图片 textImage:图文混合
     * @param text 文字描述
     * @param id   图片id
     */
    public static void showToast(Context context, String type, String text, int id) {

        toast = new Toast(BaseApplication.getmContext());
        View toastView = LayoutInflater.from(BaseApplication.getmContext()).inflate(R.layout.toast_layout, null);
        TextView toastText = (TextView) toastView.findViewById(R.id.tishiyu);
        ImageView toastImage = (ImageView) toastView.findViewById(R.id.iv_activity_toast_icon);
        toastText.setVisibility(View.GONE);
        toastImage.setVisibility(View.GONE);
        if ("text".equals(type)) {
            toastText.setText(text);
            toastText.setVisibility(View.VISIBLE);
        } else if ("image".equals(type)) {
            toastImage.setImageResource(id);
            toastImage.setVisibility(View.VISIBLE);
        } else {
            toastText.setText(text);
            toastText.setVisibility(View.VISIBLE);
            toastImage.setImageResource(id);
            toastImage.setVisibility(View.VISIBLE);
        }
        toast.setGravity(Gravity.BOTTOM, 10, ImageUtils.dipToPX(BaseApplication.getmContext(),55));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }
}
