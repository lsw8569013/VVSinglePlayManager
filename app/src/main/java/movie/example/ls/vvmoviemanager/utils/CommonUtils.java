package movie.example.ls.vvmoviemanager.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/11
 * Email : lsw
 */
public class CommonUtils {
    /**
     * return if str is empty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get format date
     *
     * @param timemillis
     * @return
     */
    public static String getFormatDate(long timemillis) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(timemillis));
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className
     *            某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * decode Unicode string
     *
     * @param s
     * @return
     */
    public static String decodeUnicodeStr(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                for (int j = 0; j < 4; j++) {
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                        cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                    } else {
                        cc = 0;
                        break;
                    }
                }
                if (cc > 0) {
                    i += 5;
                    sb.append(cc);
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * encode Unicode string
     *
     * @param s
     * @return
     */
    public static String encodeUnicodeStr(String s) {
        StringBuilder sb = new StringBuilder(s.length() * 3);
        for (char c : s.toCharArray()) {
            if (c < 256) {
                sb.append(c);
            } else {
                sb.append("\\u");
                sb.append(Character.forDigit((c >>> 12) & 0xf, 16));
                sb.append(Character.forDigit((c >>> 8) & 0xf, 16));
                sb.append(Character.forDigit((c >>> 4) & 0xf, 16));
                sb.append(Character.forDigit((c) & 0xf, 16));
            }
        }
        return sb.toString();
    }

    /**
     * convert time str
     *
     * @param time
     * @return
     */
    public static String convertTime(int time) {

        time /= 1000;
        int minute = time / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    /**
     * url is usable
     *
     * @param url
     * @return
     */
    public static boolean isUrlUsable(String url) {
        if (CommonUtils.isEmpty(url)) {
            return false;
        }

        URL urlTemp = null;
        HttpURLConnection connt = null;
        try {
            urlTemp = new URL(url);
            connt = (HttpURLConnection) urlTemp.openConnection();
            connt.setRequestMethod("HEAD");
            int returnCode = connt.getResponseCode();
            if (returnCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            connt.disconnect();
        }
        return false;
    }

    /**
     * is url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }

    /**
     * get toolbar height
     *
     * @param context
     * @return
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getVersionNum(Context context) {
        try {
            PackageManager pm = context.getPackageManager();

            PackageInfo pinfo = pm.getPackageInfo("cn.cntv",
                    PackageManager.GET_CONFIGURATIONS);
            int versionCode = pinfo.versionCode;
            String versionName = pinfo.versionName;
            return versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int systemVersions() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        return currentapiVersion;
    }

    /**
     * 将秒数转换成日期时间 5.15 09:30  的时间
     *
     * @param time 时间
     * @return 返回的时间格式为 5.15 09:30
     */
    public static String getDataTime2(String time) {
        StringBuffer sb = new StringBuffer();
        try {
            Calendar calendar = Calendar.getInstance();
            Long mill = Long.parseLong(time);
            calendar.setTimeInMillis(mill * 1000);
            sb.append(calendar.get(Calendar.MONTH) + 1);
            sb.append(".");
            sb.append(calendar.get(Calendar.DAY_OF_MONTH));
            sb.append(" ");
            if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
                sb.append("0" + calendar.get(Calendar.HOUR_OF_DAY));
            } else {
                sb.append(calendar.get(Calendar.HOUR_OF_DAY));
            }
            sb.append(":");
            if (calendar.get(Calendar.MINUTE) < 10) {
                sb.append("0" + calendar.get(Calendar.MINUTE));
            } else {
                sb.append(calendar.get(Calendar.MINUTE));
            }
        } catch (Exception e) {

        }

        return sb.toString();
    }

    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取app内存
     *
     * @return
     */
    private String getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                return str2;
            }
        } catch (IOException e) {
            return str2;
        }
        return str2;
    }

    /**
     * 将秒数转换成日期时间 5.15 09:30  的时间
     *
     * @param time 时间
     * @return 返回的时间格式为 2014/3/12 13:33
     */
    public static String getDataTime3(String time) {
        Long sTime = Long.parseLong(time);
        sTime = sTime * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(sTime);
        return format.format(date);
    }

    /**
     * 利用秒数来获取距离现在时间 如果一天之内，返回 如  23分钟前 23小时前  如果大于一天，则返回日期 格式为 2015-12-04
     *
     * @param time 单位是秒
     * @return
     */
    public static String getPaseTime1(long time) {
        Calendar c = Calendar.getInstance();
        long current = c.getTimeInMillis();
        long passTime = current - time * 1000;

        if (passTime < 60000) {
            return passTime / 1000 + "秒";
        }
        long minute = passTime / 60000;
        if (minute < 60) {
            return minute + "分钟";
        }
        long hour = minute / 60;
        if (hour < 24) {
            return hour + "小时";
        }

        return getFormatTime1(time * 1000);
    }

    /**
     * 返回格式化时间
     *
     * @param time 时间当为：毫秒
     * @return 返回的时间格式为 2015-03-22
     */
    public static String getFormatTime1(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return format.format(date);
    }
}
