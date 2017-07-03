package movie.example.ls.vvmoviemanager.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import movie.example.ls.vvmoviemanager.R;


/**
 * 自定义等待对话框
 * Date :  2015/8/16
 */
public class CustomProgressDialog extends Dialog {

    private Context context = null;
    private CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Context context){
        super(context);
        this.context = context;
        createDialog();
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }


    public  CustomProgressDialog createDialog(){
        customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.loading_common);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }
    }

    /**
     * setMessage 提示内容
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(CustomProgressDialog progressDialog,String strMessage){
        TextView tvMsg = (TextView)progressDialog.findViewById(R.id.loading_msg);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }

}

