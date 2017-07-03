package movie.example.ls.vvmoviemanager.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.utils.ImageUtils;


/**
 * Created by Administrator on 2016/12/13.
 */
public class CImageView extends ImageView {

    private  Context ctx;
    private boolean isVideo = false;

    public CImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CImageView(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public CImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CImageView);
        isVideo = a.getBoolean(R.styleable.CImageView_video,false);
    }

    @Override
    protected void onDraw(Canvas canvas) {   //这里就是重写的方法了，想画什么形状自己动手
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        if(isVideo){
            Bitmap videoBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.white_video_icon);

            Paint paint = new Paint();
            paint.setAntiAlias(true);

            int lide = ImageUtils.dipToPX(ctx, 10);
            canvas.drawBitmap(videoBitmap, ImageUtils.dipToPX(ctx, 8) +getPaddingLeft(),getHeight()-lide-videoBitmap.getHeight()-getPaddingBottom(),paint);
        }
    }


    public void setVideo(boolean video) {
        isVideo = video;
        postInvalidate();
    }


}
