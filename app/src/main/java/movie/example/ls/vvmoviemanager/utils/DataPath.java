package movie.example.ls.vvmoviemanager.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class DataPath {

    /**
     * 全局常量：数据存储路径
     */
    public static final String DATA_PATH = "/atwill/";
    public static final String DATA_PATH_DOWNLOAD = DATA_PATH + "download/";
    public static final String DATA_PATH_CRASH = DATA_PATH + "crash/";
    public static final String DATA_PATH_PIC = DATA_PATH + "pics/";
    public static final String DATA_PATH_VIDEO = DATA_PATH + "video/";
    public static final String DATA_PATH_VIDEO_PIC = DATA_PATH + "videopic/";
    public static final String DATA_PATH_RECORDER = DATA_PATH + "recorder/";
    public static final String DATA_PATH_CAMERA = DATA_PATH + "camera/";
    public static final String DATA_PATH_TEMP = DATA_PATH + "temp/"; // 临时文件夹(例如存放裁剪的头像)
    public static final String DATA_PATH_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cache/";

    public static String getDirectory(String dir) {
        if (DATA_PATH_CACHE.equals(dir)) {
            throw new IllegalArgumentException("dir can't equals DATA_PATH_CACHE");
        }
        File file = new File(Environment.getExternalStorageDirectory().getPath() + DATA_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!DATA_PATH.equals(dir)) {
            File file2 = new File(Environment.getExternalStorageDirectory().getPath() + dir);
            if (!file2.exists()) {
                file2.mkdirs(); 
            }
            if (DATA_PATH_DOWNLOAD.equals(dir) || DATA_PATH_TEMP.equals(dir)) {
                File file3 = new File(Environment.getExternalStorageDirectory().getPath() + dir + ".nomedia");
                if (!file3.exists()) {
                    try { 
                        file3.createNewFile();
                    } catch (IOException e) { 
                        e.printStackTrace();
                    }
                }
            }
        }
        return Environment.getExternalStorageDirectory().getPath() + dir;
    }

    public static void  CreateCacheFolder(){
        File f = new File(DATA_PATH_CACHE);
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
