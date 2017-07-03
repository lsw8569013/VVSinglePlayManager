package movie.example.ls.vvmoviemanager.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.InputStream;

import movie.example.ls.vvmoviemanager.utils.CenterUtil;
import movie.example.ls.vvmoviemanager.utils.ThreadManager;

/**
 * 配置Glide 优化加载图片的方式
 * Date :  2016/8/11
 */
public class LocalDiskCacheBuilder implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        File file = new File(CenterUtil.GLIDE_CACHE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
//      根据屏幕大小可用内存计算出合适的缓存配置
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//      缓存内存的大小
        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize / 3));
        builder.setDiskCache(new DiskLruCacheFactory(file.getAbsolutePath(),
                1000 * 1024 * 1024));
        builder.setDecodeFormat(DecodeFormat.DEFAULT);//默认565样式
        builder.setDiskCacheService(ThreadManager.getDownloadPool().getPool());
        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize / 3));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(new OkHttpClient()));
    }

}
