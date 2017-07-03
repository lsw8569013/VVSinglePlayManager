package movie.example.ls.vvmoviemanager.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/16
 * Email : lsw
 */
public class StreamUtils {
    /**
     * 将输入流读取成String后返回
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        String result = out.toString();
        in.close();
        out.close();
        return result;
    }

    /**
     * 读取文件--换行
     * @param strFilePath
     * @return
     */
    public static String ReadTxtFileInPath(String strFilePath){
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }else{
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            }catch (java.io.FileNotFoundException e){
                Log.d("TestFile", "The File doesn't not exist.");
            }catch (IOException e){
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    /**
     * 读取文件  -- 换行
     * @param instream
     * @return
     */
    public static String ReadTxtFileInStream(InputStream instream){
        String content = ""; //文件内容字符串
            try {
                if (instream != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            }catch (java.io.FileNotFoundException e){
                Log.d("TestFile", "The File doesn't not exist.");
            }catch (IOException e){
                Log.d("TestFile", e.getMessage());
            }
        return content;
    }

    /**
     * 读取一个字符串返回一个输入流
     * @param str
     * @return
     */
    public static InputStream getInputStream(String str){
        if(TextUtils.isEmpty(str)){
            return null;
        }else{
            return new ByteArrayInputStream(str.getBytes());
        }
    }

}
