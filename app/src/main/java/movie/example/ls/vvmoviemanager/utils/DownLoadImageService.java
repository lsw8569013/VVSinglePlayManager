package movie.example.ls.vvmoviemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SSSS on 2017/1/16 0016.
 * 图片下载
 */
public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;
    private RequestManager with;
    public DownLoadImageService(Context context, String url, RequestManager with, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
        this.with = with;
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
//            file = with
//                    .load(url)
//                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .get();
            bitmap = with
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                // 在这里执行图片保存方法
//                saveImageToGallery(context, bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (file != null) {
//                callBack.onDownLoadSuccess(file);
//            } else {
//                callBack.onDownLoadFailed();
//            }
            if (bitmap != null) { //  && currentFile.exists()
                callBack.onDownLoadSuccess(bitmap);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
//        String File
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "新建文件夹";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    public interface ImageDownLoadCallBack {
        void onDownLoadSuccess(File file);

        void onDownLoadSuccess(Bitmap bitmap);

        void onDownLoadFailed();
    }
}
