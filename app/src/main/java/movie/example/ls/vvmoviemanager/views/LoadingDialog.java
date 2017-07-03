package movie.example.ls.vvmoviemanager.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import movie.example.ls.vvmoviemanager.R;


public class LoadingDialog extends Dialog{
    
    private Context context;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }
    
    protected LoadingDialog(Context context, boolean cancelable,
                            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }
    
    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }
    
    public LoadingDialog setContent(String content) {
        TextView textView = (TextView) findViewById(R.id.dialog_progress_tv);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.dialog_progress_rl);
        if (TextUtils.isEmpty(content)) {
            relativeLayout.getLayoutParams().width = dip2px(context, 80);
            relativeLayout.getLayoutParams().height = dip2px(context, 80);
            textView.setVisibility(View.GONE);
        } else {
            relativeLayout.getLayoutParams().width = dip2px(context, 150);
            relativeLayout.getLayoutParams().height = dip2px(context, 100);
            textView.setText(content);
            textView.setVisibility(View.VISIBLE);
        }
        return this;
    }
    
    public static class Builder {
        private Context context;
        private String content;
        
        public Builder(Context context) {
            this.context = context;
        }
  
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }
        
        public LoadingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LoadingDialog dialog = new LoadingDialog(context, R.style.dialog);
            View layout = inflater.inflate(R.layout.layout_progress, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().getAttributes().dimAmount = 0f;
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            RelativeLayout relativeLayout = (RelativeLayout) layout.findViewById(R.id.dialog_progress_rl);
            TextView textView = (TextView) layout.findViewById(R.id.dialog_progress_tv);
            if (TextUtils.isEmpty(content)) {
                relativeLayout.getLayoutParams().width = dip2px(context, 80);
                relativeLayout.getLayoutParams().height = dip2px(context, 80);
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(content);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
    
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
