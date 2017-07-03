package movie.example.ls.vvmoviemanager.utils;


import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;


/**
 * 图片路径以及数据库路径的处理
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/30
 * Email : lsw
 */
public class FileHelper {

    private static final String PICFILEPATH = "colectSite/image";
    private static final String DATAFILEPATH = "colectSite/data"; // 数据库文件路径
    private static final String DATANAME = "flyfile.db"; // 数据库名称
    private static final String COMPRESS_PICFILEPATH = "colectSite/com_image";

    /**
     * 获得压缩图片保存的基础路径
     *
     * @return
     */
    public static File getCompressPicBaseFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用使用外部存储
            File f = new File(Environment.getExternalStorageDirectory(),
                    COMPRESS_PICFILEPATH);
            if (!f.exists()) {
                f.mkdirs();
            }
            return f;
        } else if ((new File("/mnt/sdcard2")).exists()) {
            String file = "/mnt/sdcard2/" + COMPRESS_PICFILEPATH;
            File f = new File(file);
            if (!f.exists()) {
                f.mkdirs();
            }
            return f;
        } else {
            return null;
        }
    }

    /**
     * 图片基础路径
     *
     * @return
     */
    public static File getPicBaseFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用使用外部存储
            File f = new File(Environment.getExternalStorageDirectory(),
                    PICFILEPATH);
            if (!f.exists()) {
                f.mkdirs();
            }
            return f;
        } else if ((new File("/mnt/sdcard2")).exists()) {
            String file = "/mnt/sdcard2/" + PICFILEPATH;
            File f = new File(file);
            if (!f.exists()) {
                f.mkdirs();
            }
            return f;
        } else {
            return null;
        }
    }

    /**
     * @return 数据库文件路径
     */
    public static File getDBFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 文件可用使用外部存储
            File f = new File(Environment.getExternalStorageDirectory(),
                    DATAFILEPATH);
            if (!f.exists()) {
                f.mkdirs();
            }
            return new File(f, DATANAME);
        } else if ((new File("/mnt/sdcard2")).exists()) {
            String file = "/mnt/sdcard2/" + DATAFILEPATH;
            File f = new File(file);
            if (!f.exists()) {
                f.mkdirs();
            }
            return new File(f, DATANAME);
        } else {
            return null;
        }
    }

    /**
     * 根据Uri返回一个文件
     *
     * @param context
     * @param uri
     * @return
     */
//    public static File getFile(Context context, Uri uri) {
//        if (uri != null) {
//            String path = getPath(context, uri);
//            if (path != null && isLocal(path)) {
//                return new File(path);
//            }
//        }
//        return null;
//    }

    /**
     * @return Whether the URI is a local one.
     */
    public static boolean isLocal(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return true;
        }
        return false;
    }

}