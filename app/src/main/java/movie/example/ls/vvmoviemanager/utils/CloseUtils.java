package movie.example.ls.vvmoviemanager.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 可关闭的对象的辅助工具类
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/6/22
 * Email : lsw
 */
public final class CloseUtils {
    private CloseUtils(){}
    public  static void closeQuietly(Closeable closeable){
        if (closeable!=null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
