package movie.example.ls.vvmoviemanager.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/11/16
 * Email : lsw
 */
public class EaseImageUtils  {

    public static File savePicture(Bitmap bm) {
        File fileFolder = new File(CenterUtil.VIDEO_AUDIO_PATH);
        if (!fileFolder.exists()) {
            fileFolder.mkdir();
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
        String filename = format.format(date) + ".jpg";

        File jpgFile = new File(fileFolder.getPath(), filename);

        if (jpgFile.exists()) {
            jpgFile.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(jpgFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jpgFile;
    }

    /**
     * 获取视频图片
     * @param filePath
     * @param tofile
     * @return
     */
    static public File getVideoThumbnail(String filePath, String tofile) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
//            retriever.getFrameAtTime()
            bitmap = retriever.getFrameAtTime(2000);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        File f = saveBitmapAbsolutePath(bitmap, tofile);
        if (bitmap != null)
            bitmap.recycle();
        return f;

    }


    public static File saveBitmapAbsolutePath(Bitmap bm, String filename) {
        File f = new File(filename);
        if (bm == null)
            return f;

        if (f.exists()) {
            f.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }


}
