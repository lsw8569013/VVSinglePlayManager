package movie.example.ls.vvmoviemanager.glide;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.bean.MediaBean;
import movie.example.ls.vvmoviemanager.utils.CenterUtil;
import movie.example.ls.vvmoviemanager.utils.ConfigUtils;

/**
 * 封装Glide实现图片的缓存优化加载
 * Date :  2016/8/11
 */
public class FinalBitmap {

    private static final String TAG = "FinalBitmap";

    private static ConcurrentHashMap<String, SoftReference<FinalBitmap>> withs;
    private static ConcurrentHashMap<String, SoftReference> contexts;

    private RequestManager with;
    private Context mContext;

    private onGlideCacheSizeListener listener;

    static {
        withs = new ConcurrentHashMap<>();
        contexts = new ConcurrentHashMap<>();
    }

    public interface onGlideCacheSizeListener{
        void getCacheSize(long size);
    }

    public void setListener(onGlideCacheSizeListener listener) {
        this.listener = listener;
    }

    private FinalBitmap(Context context) {
        if (context != null){
            Glide.get(context).clearMemory();
        }
        mContext=context;
        with = Glide.with(context);
        contexts.put(context.getClass().getName(), new SoftReference(context));
    }

    private FinalBitmap(Application context) {
        if (context != null)
            Glide.get(context).clearMemory();
        mContext=context;
        with = Glide.with(context);
        contexts.put(context.getClass().getName(), new SoftReference(context));
    }

    private FinalBitmap(Fragment context) {
        if (context.getActivity() != null)
            Glide.get(context.getActivity()).clearMemory();
        mContext=context.getActivity();
        with = Glide.with(context);
        contexts.put(context.getClass().getName(), new SoftReference(context));
    }

    public FinalBitmap(Activity context) {
        if (context != null)
            Glide.get(context).clearMemory();
        mContext=context;
        with = Glide.with(context);
        contexts.put(context.getClass().getName(), new SoftReference(context));
    }

    /**
     * 创建finalbitmap
     *
     * @param ctx
     * @return
     */
    public static synchronized FinalBitmap create(Context ctx) {
        Map<String, SoftReference> tempContexts;
        if (contexts != null && !contexts.isEmpty()) {
            tempContexts = new ConcurrentHashMap<>(contexts);
            if (contexts.containsKey(ctx.getClass().getName())) {
                contexts.remove(ctx.getClass().getName());
                withs.remove(ctx.getClass().getName());
            }
            for (Map.Entry<String, SoftReference> entry : tempContexts.entrySet()) {
                if (entry.getValue().get() == null) {
                    contexts.remove(entry.getKey());
                    withs.remove(entry.getKey());
                }
            }
            tempContexts.clear();
        }
        FinalBitmap mFinalBitmap;
        if (ctx == null)
            return null;
        if (withs.containsKey(ctx.getClass().getName())) {
            mFinalBitmap = withs.get(ctx.getClass().getName()).get();
        } else {
            mFinalBitmap = new FinalBitmap(ctx);
            withs.put(ctx.getClass().getName(), new SoftReference<FinalBitmap>(mFinalBitmap));
        }

        return mFinalBitmap;
    }

    public static synchronized FinalBitmap create(Activity ctx) {
        Map<String, SoftReference> tempContexts;
        if (contexts != null && !contexts.isEmpty()) {
            if (contexts.containsKey(ctx.getClass().getName())) {
                contexts.remove(ctx.getClass().getName());
                withs.remove(ctx.getClass().getName());
            }
            tempContexts = new ConcurrentHashMap<>(contexts);
            for (Map.Entry<String, SoftReference> entry : tempContexts.entrySet()) {
                if (entry.getValue().get() == null) {
                    contexts.remove(entry.getKey());
                    withs.remove(entry.getKey());
                }
            }
            tempContexts.clear();
        }
        FinalBitmap mFinalBitmap = null;
        if (withs.containsKey(ctx.getClass().getName())) {
            mFinalBitmap = withs.get(ctx.getClass().getName()).get();
        } else {
            mFinalBitmap = new FinalBitmap(ctx);
            withs.put(ctx.getClass().getName(), new SoftReference<FinalBitmap>(mFinalBitmap));
        }
        return mFinalBitmap;
    }

    public static synchronized FinalBitmap create(Fragment ctx) {
        Map<String, SoftReference> tempContexts;
        if (contexts != null && !contexts.isEmpty()) {
            if (contexts.containsKey(ctx.getClass().getName())) {
                contexts.remove(ctx.getClass().getName());
                withs.remove(ctx.getClass().getName());
            }
            tempContexts = new ConcurrentHashMap<>(contexts);
            for (Map.Entry<String, SoftReference> entry : tempContexts.entrySet()) {
                if (entry.getValue().get() == null) {
                    contexts.remove(entry.getKey());
                    withs.remove(entry.getKey());
                }
            }
            tempContexts.clear();
        }
        FinalBitmap mFinalBitmap = null;
        if (withs.containsKey(ctx.getClass().getName())) {
            mFinalBitmap = withs.get(ctx.getClass().getName()).get();
        } else {
            mFinalBitmap = new FinalBitmap(ctx);
            withs.put(ctx.getClass().getName(), new SoftReference<FinalBitmap>(mFinalBitmap));
        }
        return mFinalBitmap;
    }

    public static synchronized FinalBitmap create(Application ctx) {
        Map<String, SoftReference> tempContexts;
        if (contexts != null && !contexts.isEmpty()) {
            if (contexts.containsKey(ctx.getClass().getName())) {
                contexts.remove(ctx.getClass().getName());
                withs.remove(ctx.getClass().getName());
            }
            tempContexts = new ConcurrentHashMap<>(contexts);
            for (Map.Entry<String, SoftReference> entry : tempContexts.entrySet()) {
                if (entry.getValue().get() == null) {
                    contexts.remove(entry.getKey());
                    withs.remove(entry.getKey());
                }
            }
            tempContexts.clear();
        }
        FinalBitmap mFinalBitmap = null;
        if (withs.containsKey(ctx.getClass().getName())) {
            mFinalBitmap = withs.get(ctx.getClass().getName()).get();
        } else {
            mFinalBitmap = new FinalBitmap(ctx);
            withs.put(ctx.getClass().getName(), new SoftReference<FinalBitmap>(mFinalBitmap));
        }
        return mFinalBitmap;
    }

    /**
     * 从中间裁剪图片
     * @param imageView 目标控件
     * @param uri 图片地址
     * @param cache 缓存
     */
    public void displayfitCenter(ImageView imageView, String uri,boolean cache) {
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
                        .fitCenter()
                        .error(R.color.grayColor)
                        .crossFade()
                        .thumbnail(0.5f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 从中间裁剪图片
     * @param imageView 目标控件
     * @param uri 图片地址
     * @param cache 缓存
     */
//    public void displayfitCenterBlur(ImageView imageView, String uri,boolean cache,BlurTransformation blurTransformation) {
//        try {
//            if (imageView != null) {
//                with.load(uri)
//                        .skipMemoryCache(cache?false:true)
//                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                        .fitCenter()
//                        .bitmapTransform(blurTransformation)
//                        .error(R.color.grayColor)
//                        .crossFade()
//                        .thumbnail(0.5f)
//                        .into(imageView);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 展示图片
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public void display(ImageView imageView, final String uri, boolean cache) {
        try {
            if (imageView != null) {
                if(!uri.startsWith("http")){
                    File file=new File(uri);
                    if(file.exists()){
                        with.load(file)
                                .skipMemoryCache(cache?false:true)
                                .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                                .error(R.color.grayColor)
                                .crossFade()
                                .thumbnail(0.5f)
                                .into(imageView);
                    }else{
                        with.load(uri)
                                .skipMemoryCache(cache?false:true)
                                .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                                .error(R.color.grayColor)
                                .crossFade()
                                .thumbnail(0.5f)
                                .into(imageView);
                    }
                }else{
                    with.load(uri)
                            .skipMemoryCache(cache?false:true)
                            .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                            .error(R.color.grayColor)
                            .crossFade()
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    Log.e("头像地址加载失败", "onBindData: "+ uri);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .thumbnail(0.5f)
                            .into(imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示图片 不压缩
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public void displayNonThumbnail(ImageView imageView, String uri, boolean cache) {
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                        .error(R.color.grayColor)
                        .crossFade()
//                        .thumbnail(0.5f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayResult(ImageView imageView, String uri, boolean cache) {
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
                        .error(R.color.grayColor)
                        .placeholder(R.color.line_color)
                        .crossFade()
                        .thumbnail(0.1f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param imageView 大图地址
     * @param imageVideoUrl 原图地址
     * @param uri
     */
    public void displayC(ImageView imageView, String imageVideoUrl, String uri) {
        try {
            DrawableRequestBuilder thumbnailBuilder = Glide.with(mContext)
                    .load(uri+ ConfigUtils.QINIU_THUMBURL_IMAGE_SMALL);

            if (imageView != null) {
                with.load(imageVideoUrl)
                        .thumbnail(thumbnailBuilder)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVideoThumbnails(MediaBean mediaBean, int width){
        String mediaUrl;
        if (mediaBean == null || TextUtils.isEmpty(mediaBean.getMediaUrl())){
            return "";
        }
        mediaUrl = mediaBean.getMediaUrl();
        if (mediaBean.getHeight() <= 0 || mediaBean.getWidth() <= 0){
            mediaUrl = mediaUrl + "?vframe/jpg/offset/0/w/" + width +"/h/" + width;
        }else {
//            if (mediaBean.getDuration() > 3){
//                mediaUrl = mediaUrl + "?vframe/jpg/offset/2/w/" + width + "/h/" + (mediaBean.getHeight() * width) / mediaBean.getWidth();
//            }else {
//            }
            mediaUrl = mediaUrl + "?vframe/jpg/offset/0/w/" + width + "/h/" + (mediaBean.getHeight() * width) / mediaBean.getWidth();
        }
        return mediaUrl;
    }

    public void displayCVideo(ImageView imageView, String imageVideoUrl, MediaBean bean) {
        try {
            DrawableRequestBuilder thumbnailBuilder;
                thumbnailBuilder = Glide.with(mContext)
                        .load(imageVideoUrl+ getVideoThumbnails(bean, 50));

            if (imageView != null) {
                with.load(imageVideoUrl)
                        .thumbnail(thumbnailBuilder)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param imageView
     * @param imageVideoUrl
     */
    public void displayCVideoWhite(ImageView imageView, String imageVideoUrl,int width,int height) {
        try {
            DrawableRequestBuilder thumbnailBuilder;
                thumbnailBuilder = Glide.with(mContext)
                        .load(imageVideoUrl+"/w/"+width+"/h/"+height);

//            Logger.e(""+imageVideoUrl+"/w/"+width+"/h/"+height);

            if (imageView != null) {
                with.load(imageVideoUrl)
                        .thumbnail(thumbnailBuilder)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayCCrop(ImageView imageView, String imageVideoUrl, String uri, int width, int height) {
        try {
            DrawableRequestBuilder thumbnailBuilder = Glide.with(mContext)
                    .load(uri+ ConfigUtils.QINIU_THUMBURL_IMAGE_SMALL);

            if (imageView != null) {
                with.load(imageVideoUrl)
                        .thumbnail(thumbnailBuilder)
                        .crossFade()
                        .centerCrop()
                        .override(width,height)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImagePathFromCache(String url, int width, int height){
        FutureTarget<File> fileFutureTarget = with.load(url).downloadOnly(width, height);

        try {
            return fileFutureTarget.get().getAbsolutePath();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  "";
    }

//    public void displayLoveImage(ImageView imageView,String url){
//        if(TextUtils.isEmpty(url)){
//            return;
//        }
//        if(url.contains("http")){
//            with.load(url)
//                    .placeholder(R.drawable.ic_gf_default_photo)
//                    .thumbnail(0.3f)
//                    .dontAnimate()
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(imageView);
//        }else if(url.contains("storage")){
//            File file=new File(url);
//            if(file.exists()){
//                with.load(url)
//                        .dontAnimate()
//                        .placeholder(R.drawable.ic_gf_default_photo)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .into(imageView);
//            }
//        }else{
//            with.load(url)
//                    .dontAnimate()
//                    .placeholder(R.drawable.ic_gf_default_photo)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .into(imageView);
//        }
//    }

    /**
     * 加载网络图片（缓存）
     * @param url 图片地址
     */
//    public void loadNetImageUrlBlur(ImageView imageView,String url,BlurTransformation blurTransformation) {
//        DrawableRequestBuilder builder=with.load(url.contains(ConfigUtils.QINIU_THUMBURL_IMAGE)?url: url+ ConfigUtils.QINIU_THUMBURL_IMAGE);
//        with.load(url)
//                .skipMemoryCache(false)
//                .dontAnimate()
//                .thumbnail(builder)
//                .bitmapTransform(blurTransformation)
//                .placeholder(R.drawable.ic_gf_default_photo)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(new GlideDrawableImageViewTarget(imageView, 0));
//    }

    /**
     * 加载网络图片（缓存）
     * @param url 图片地址
     */
    public void loadNetImageUrl(ImageView imageView,String url) {
        with.load(url)
                .skipMemoryCache(false)
                .dontAnimate()
                .thumbnail(0.3f)
                .placeholder(R.drawable.ic_gf_default_photo)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

//    public void displayLoveImageBlur(ImageView imageView,String url,BlurTransformation bu){
//        if(TextUtils.isEmpty(url)){
//            return;
//        }
//        if(url.contains("http")){
//            with.load(url)
//                    .placeholder(R.drawable.ic_gf_default_photo)
//                    .thumbnail(0.3f)
//                    .dontAnimate()
//                    .bitmapTransform(bu)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(imageView);
//        }else if(url.contains("storage")){
//            File file=new File(url);
//            if(file.exists()){
//                with.load(url)
//                        .dontAnimate()
//                        .bitmapTransform(bu)
//                        .placeholder(R.drawable.ic_gf_default_photo)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .into(imageView);
//            }
//        }else{
//            with.load(url)
//                    .dontAnimate()
//                    .bitmapTransform(bu)
//                    .placeholder(R.drawable.ic_gf_default_photo)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .into(imageView);
//        }
//    }

    /**
     * 详情页面加载图片
     * @param imageView
     * @param uri
     */
    public void displayPlaceholder(ImageView imageView, String uri) {
        try {
            if (imageView != null) {
                with.load(uri)
                        .error(R.color.grayColor)
                        .dontAnimate()
                        .thumbnail(0.4f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 展示图片
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public void display(ImageView imageView, int uri, boolean cache) {
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
                        .error(R.color.grayColor)
                        .crossFade()
                        .thumbnail(0.5f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 展示圆形图片
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public  void displayCircle(ImageView imageView, String uri,boolean cache){
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .error(R.color.grayColor)
                        .crossFade()
                        .thumbnail(0.5f)
                        .priority(Priority.HIGH)
                        .into(imageView);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    /**
     * 展示圆形图片
     * @param imageView
     * @param uri
     * @param thumUri
     * @param cache 缓存
     */
    public  void displayCircleThum(ImageView imageView, String uri,String thumUri,boolean cache){
        try {
            if (imageView != null) {
                DrawableRequestBuilder thumbnailBuilder = with.load(thumUri);
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .error(R.color.grayColor)
                        .crossFade()
                        .thumbnail(thumbnailBuilder)
                        .priority(Priority.HIGH)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 展示圆形图片
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public  void displayCircle(ImageView imageView, @DrawableRes int uri, boolean cache){
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .error(R.color.grayColor)
                        .crossFade()
                        .thumbnail(0.5f)
                        .priority(Priority.HIGH)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 展示圆角
     * @param imageView
     * @param uri
     * @param radio 传递弧度
     * @param margin 间距
     * @param cache 缓存
     */
    public  void displayRound(ImageView imageView, String uri, int radio,int margin,boolean cache){
        try {
            if (imageView != null) {
                if(uri.startsWith("http")){
                    with.load(uri)
                            .skipMemoryCache(cache?false:true)
                            .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                            .bitmapTransform(new RoundedCornersTransformation(mContext,radio,margin))
                            .crossFade()
                            .dontAnimate()
                            .placeholder(R.drawable.ic_gf_default_photo)
                            .thumbnail(0.5f)
                            .into(imageView);
                }else{
                    File file=new File(uri);
                    if (file.exists()) {
                        with.load(file)
                                .skipMemoryCache(cache?false:true)
                                .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                                .bitmapTransform(new RoundedCornersTransformation(mContext,radio,margin))
                                .crossFade()
                                .dontAnimate()
                                .placeholder(R.drawable.ic_gf_default_photo)
                                .thumbnail(0.5f)
                                .into(imageView);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示圆角
     * @param imageView
     * @param uri
     * @param radio 传递弧度
     * @param margin 间距
     * @param cache 缓存
     */
    public  void displayRoundNotHolder(ImageView imageView, String uri, int radio,int margin,boolean cache){
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
//                        .bitmapTransform(new RoundedCornersTransformation(mContext,radio,margin))
                        .crossFade()
                        .dontAnimate()
                        .placeholder(R.color.gray_normal)
                        .thumbnail(0.5f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示圆角
     * @param imageView
     * @param uri
     * @param radio 传递弧度
     * @param margin 间距
     * @param cache 缓存
     */
//    public  void displayRoundBlur(ImageView imageView, String uri, int radio,int margin,boolean cache,BlurTransformation blurTransformation){
//        try {
//            if (imageView != null) {
//                if(uri.startsWith("http")){
//                    with.load(uri)
//                            .skipMemoryCache(cache?false:true)
//                            .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
////                            .bitmapTransform(new RoundedCornersTransformation(mContext,radio,margin),blurTransformation)
//                            .crossFade()
//                            .dontAnimate()
//                            .placeholder(R.drawable.ic_gf_default_photo)
//                            .thumbnail(0.5f)
//                            .into(imageView);
//                }else{
//                    File file=new File(uri);
//                    if (file.exists()) {
//                        with.load(file)
//                                .skipMemoryCache(cache?false:true)
//                                .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
////                                .bitmapTransform(new RoundedCornersTransformation(mContext,radio,margin),blurTransformation)
//                                .crossFade()
//                                .dontAnimate()
//                                .placeholder(R.drawable.ic_gf_default_photo)
//                                .thumbnail(0.5f)
//                                .into(imageView);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 展示GIF图片
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public void displayGIF(final ImageView imageView, final String uri,boolean cache){
        try {
            if (imageView != null) {
                with.load(uri)
                        .asGif()
                        .skipMemoryCache(cache?false:true)
                        .error(R.color.grayColor)
                        .thumbnail(0.5f)
                        .dontAnimate()
                        .listener(new RequestListener<String, GifDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                                displayRound(imageView,uri,13,0,true);
                                return true;
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 展示GIF图片
     * @param imageView
     * @param uri
     * @param cache 缓存
     */
    public void displayGIF(ImageView imageView, int uri,boolean cache){
        try {
            if (imageView != null) {
                with.load(uri)
                        .asGif()
                        .skipMemoryCache(cache?false:true)
                        .error(R.color.grayColor)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayGIFC(ImageView imageView, int uri,boolean cache){
        try {
            if (imageView != null) {
                with.load(uri)
                        .asGif()
                        .error(R.color.grayColor)
                        .diskCacheStrategy(cache?DiskCacheStrategy.ALL:DiskCacheStrategy.NONE)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 展示视频的缩略图
     * @param imageView
     * @param filePath 视频文件路径
     * @param cache 缓存
     */
    public void displayVideoImg(ImageView imageView, String filePath,boolean cache){
        try {
            if (imageView != null) {
                with.load(Uri.fromFile( new File( filePath ) ) )
                        .skipMemoryCache(cache?false:true)
                        .error(R.color.grayColor)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 个人中心展示图片
     * @param imageView
     * @param url
     * @param cache 缓存
     */
    public void displayMini(ImageView imageView, String thumbnailUrl,String url, boolean cache) {
        try {

            DrawableRequestBuilder thumbnailBuilder = Glide.with(mContext).load(thumbnailUrl);
            Glide.with(mContext)
                    .load(url)
                    .skipMemoryCache(cache?false:true)
                    .placeholder(R.color.line_color)
                    .error(R.color.grayColor)
                    .crossFade()
                    .thumbnail(thumbnailBuilder)
                    .diskCacheStrategy(cache?DiskCacheStrategy.SOURCE:DiskCacheStrategy.NONE)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------

    /**
     * 获取缓存的大小
     */
    public void getCacheSize(){
        new GetDiskCacheSizeTask()
                .execute(
                        new File(CenterUtil.GLIDE_CACHE_PATH,
                                DiskCache.Factory.DEFAULT_DISK_CACHE_DIR
                        )
                );
    }

    /**
     * 获取缓存大小
     */
    private class GetDiskCacheSizeTask extends AsyncTask<File, Long, Long> {

        public GetDiskCacheSizeTask() {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Long... values) { /* onPostExecute(values[values.length - 1]); */ }

        @Override
        protected Long doInBackground(File... dirs) {
            try {
                long totalSize = 0;
                for (File dir : dirs) {
                    publishProgress(totalSize);
                    totalSize += calculateSize(dir);
                }
                return totalSize;
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long size) {
            if(listener!=null){
                listener.getCacheSize(size);
            }
        }

        private  long calculateSize(File dir) {
            if (dir == null) return 0;
            if (!dir.isDirectory()) return dir.length();
            long result = 0;
            File[] children = dir.listFiles();
            if (children != null)
                for (File child : children)
                    result += calculateSize(child);
            return result;
        }

    }


    /**
     * ImageView 可能会完全填充，但图像可能不会完整显示。
     * @param imageView
     * @param uri
     * @param cache
     */
    public void displayCenterCrop(ImageView imageView, String uri,boolean cache) {
        try {
            if (imageView != null) {
                with.load(uri)
                        .skipMemoryCache(cache?false:true)
                        .diskCacheStrategy(cache?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
                        .centerCrop()
                        .placeholder(R.color.grayColor)
                        .error(R.color.grayColor)
                        .crossFade()
                        .thumbnail(0.5f)
                        .into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearMemory() {
        Glide.get(mContext).clearMemory();
    }
    public void clearDiskCache() {
        Glide.get(mContext).clearDiskCache();
    }

    public void pauseRequests() {
        with.pauseRequests();
    }
    public void resumeRequests() {
        with.resumeRequests();
    }

    public RequestManager getWith() {
        return with;
    }
}

