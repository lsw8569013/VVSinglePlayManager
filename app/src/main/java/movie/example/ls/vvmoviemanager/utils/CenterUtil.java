package movie.example.ls.vvmoviemanager.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/11
 * Email : lsw
 */
public class CenterUtil {

    public final static String CACHE_FILE = "feiscreen" + File.separator + "cache";
    public final static String FIX_NAME="hotfix";

    public final static String DOWN_SERVICE_ACTION="com.bm.feipingflyclip.downservice.action";

    public final static String GLIDE_CACHE_PATH =
            Environment.getExternalStorageDirectory()
                    .getAbsoluteFile()
                    + File.separator
                    + CenterUtil.CACHE_FILE
                    + File.separator;

    public final static String HTTP_CACHE_PATH =
            Environment.getExternalStorageDirectory()
                    .getAbsoluteFile()
                    + File.separator
                    + CenterUtil.CACHE_FILE
                    + File.separator
                    + "httpcache"
                    + File.separator;

    public final static String HTTP_DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory()
                    .getAbsoluteFile()
                    + File.separator + "down"
                    + File.separator + "image"
                    + File.separator;

    public final static String HOT_FIX_PATH=
            Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator + "hotfix"
                    + File.separator;

    public final static String VIDEO_AUDIO_PATH=
            Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator + "vaudio"
                    + File.separator;

    /**
     * 初始化的路径
     */
    public static void initPath(){
        String[] strs=new String[]{GLIDE_CACHE_PATH,HTTP_CACHE_PATH,
                HTTP_DOWNLOAD_PATH,HOT_FIX_PATH,VIDEO_AUDIO_PATH};
        for (String s:strs) {
            File file=new File(s);
            if(!file.exists()){
                file.mkdirs();
            }
        }

    }

}
