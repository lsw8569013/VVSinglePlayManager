package movie.example.ls.vvmoviemanager.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/4/9
 * Email : lsw
 */
public class SDCardUtils {
    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 判断是否装有SD卡、是否可读写、是否有空间
     *
     * @param size 需存入的文件大小，SD剩余空间必须大于该值
     * @return true可用，false不可用
     */
    public static boolean checkSDStatus(long size) {
        try {
			/* 读取SD卡大小 */
            File storage = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(storage.getPath());
            long blocks = stat.getAvailableBlocks();
            long blocksize = stat.getBlockSize();

			/* 判断 */
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && (blocks * blocksize) > size) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath()
    {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize()
    {
        if (isSDCardEnable())
        {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath)
    {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath()))
        {
            filePath = getSDCardPath();
        } else
        {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath()
    {
        return Environment.getRootDirectory().getAbsolutePath();
    }
    /**
     * 复制文件至某个文件夹
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径，包含文件名
     * @return
     */
    public static boolean copyFileToFile(String srcFileName, String destDirName) {
        try {
            int byteread = 0;
            File oldfile = new File(srcFileName);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(destDirName);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 字节的大小，转成口头语
     * @param size
     * @return
     */
    public static String byte2Oral(double size) {
        DecimalFormat df = new DecimalFormat("0.0");
        StringBuffer datas = new StringBuffer();
        if (size < 1048576) {
            datas.append((int) (size / 1024)).append("KB");
        } else if (size > 1048576) {
            datas.append(df.format((size / 1048576))).append("MB");
        } else if (size < 1024) {
            datas.append(size).append("B");
        }
        return datas.toString();
    }
}
