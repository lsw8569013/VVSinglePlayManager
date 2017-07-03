package movie.example.ls.vvmoviemanager.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/31
 * Email : lsw
 */
public class DataUtil {

    /**
     * 将对比后的时间，格式化为：xx分钟前，xx小时前和日期
     *
     * @param t
     * @return
     */
    public static String getFriendTimeShow(String t) {
        if (t == null) {
            return "";
        } else if (t.isEmpty()) {
            return "";
        }

        long time = Long.parseLong(t);

        if (time < 0)
            return String.valueOf(time);

        int difftime = (int) ((System.currentTimeMillis() - time) / 1000);
        if (difftime < 86400 && difftime > 0) {
            if (difftime < 3600) {
                int min = (int) (difftime / 60);
                if (min == 0)
                    return "刚刚";
                else
                    return (int) (difftime / 60) + "分钟前";
            } else {
                return (int) (difftime / 3600) + "小时前";
            }
        } else {
            Calendar now = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                return new SimpleDateFormat("刚刚").format(c.getTime());
            }
//            以下省去 HH:mm
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && isYesterday(time)) {
                return new SimpleDateFormat("昨天").format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && isBeforeYesterday(time)) {
                return new SimpleDateFormat("前天").format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && isSameMonth(time)) {
                return (int) (difftime / (3600 * 24)) + "天前";
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                return new SimpleDateFormat("M月d日").format(c.getTime());
            } else {
                return new SimpleDateFormat("yy年M月d日").format(c.getTime());
            }
        }
    }

    /**
     * 将对比后的时间，格式化为：xx分钟前，xx小时前和日期
     *
     * @param time
     * @return
     */
    public static String getFriendTimeShowFromLong(Long time) {

        if (time < 0)
            return String.valueOf(time);

        int difftime = (int) ((System.currentTimeMillis() - time) / 1000);
        if (difftime < 86400 && difftime > 0) {
            if (difftime < 3600) {
                int min = (int) (difftime / 60);
                if (min == 0)
                    return "刚刚";
                else
                    return (int) (difftime / 60) + "分钟前";
            } else {
                return (int) (difftime / 3600) + "小时前";
            }
        } else {
            Calendar now = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                return new SimpleDateFormat("刚刚").format(c.getTime());
            }
//            以下省去 HH:mm
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && isYesterday(time)) {
                return new SimpleDateFormat("昨天").format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && isBeforeYesterday(time)) {
                return new SimpleDateFormat("前天").format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && isSameMonth(time)) {
                return (int) (difftime / (3600 * 24)) + "天前";
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                return new SimpleDateFormat("M月d日").format(c.getTime());
            } else {
                return new SimpleDateFormat("yy年M月d日").format(c.getTime());
            }
        }
    }

    /**
     * 传入时间算出星期几
     *
     * @param str
     * @param days
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String str, int days) {
        String dateStr = "";
        try {
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
            Date date = df.parse(str);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date d = dateFormat.parse(dateFormat.format(date));
            c.setTime(d);
            c.add(Calendar.DAY_OF_MONTH, days);
            switch (c.get(Calendar.DAY_OF_WEEK) - 1) {
                case 0:
                    dateStr = "周日";
                    break;
                case 1:
                    dateStr = "周一";
                    break;
                case 2:
                    dateStr = "周二";
                    break;
                case 3:
                    dateStr = "周三";
                    break;
                case 4:
                    dateStr = "周四";
                    break;
                case 5:
                    dateStr = "周五";
                    break;
                case 6:
                    dateStr = "周六";
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    /**
     * 友好时间展示
     * @param time
     * @return
     */
    public static String getTimestampString(long time) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date paramDate = c.getTime();
        String str = null;
        long l = paramDate.getTime();
        Calendar localCalendar = GregorianCalendar.getInstance();
        localCalendar.setTime(paramDate);
        int year = localCalendar.get(Calendar.YEAR);
        if (!isSameYear(year)) { //去年，直接返回
            String paramDate2str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(paramDate);
            return paramDate2str;
        }

//          省略HH:mm
        if (isSameDay(l)) {
            int i = localCalendar.get(Calendar.HOUR_OF_DAY);
            if (i > 17) {
                str = "晚上 HH:mm";//HH表示24小时,hh表示12小时进制，
            } else if ((i >= 0) && (i <= 6)) {
                str = "凌晨 HH:mm";
            } else if ((i > 11) && (i <= 17)) {
                str = "下午 HH:mm";
            } else {
                str = "上午 HH:mm";
            }
        } else if (isYesterday(l)) {
            str = "昨天 HH:mm";
        } else if (isBeforeYesterday(l)) {
            str = "前天 HH:mm";
        } else {
            str = "M月d日 HH:mm";
        }
        String paramDate2str = new SimpleDateFormat(str, Locale.CHINA).format(paramDate);
        return paramDate2str;
    }

    //   获取  今天开始结束 时间
    public static TimeInfo getTodayStartAndEndTime() {

        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);
        localCalendar1.set(Calendar.MINUTE, 0);
        localCalendar1.set(Calendar.SECOND, 0);
        localCalendar1.set(Calendar.MILLISECOND, 0);
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();


        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);
        localCalendar2.set(Calendar.MINUTE, 59);
        localCalendar2.set(Calendar.SECOND, 59);
        localCalendar2.set(Calendar.MILLISECOND, 999);
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();


        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }


//   获取  昨天开始结束 时间

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.add(Calendar.DAY_OF_MONTH, -1);//5
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);//11
        localCalendar1.set(Calendar.MINUTE, 0);//12
        localCalendar1.set(Calendar.SECOND, 0);//13
        localCalendar1.set(Calendar.MILLISECOND, 0);//Calendar.MILLISECOND
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();


        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.add(Calendar.DAY_OF_MONTH, -1);//5
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);//11
        localCalendar2.set(Calendar.MINUTE, 59);//12
        localCalendar2.set(Calendar.SECOND, 59);//13
        localCalendar2.set(Calendar.MILLISECOND, 999);//Calendar.MILLISECOND
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();


        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }


//   获取 前天开始结束 时间

    public static TimeInfo getBeforeYesterdayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.add(Calendar.DAY_OF_MONTH, -2);
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);
        localCalendar1.set(Calendar.MINUTE, 0);
        localCalendar1.set(Calendar.SECOND, 0);
        localCalendar1.set(Calendar.MILLISECOND, 0);
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();


        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.add(Calendar.DAY_OF_MONTH, -2);
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);
        localCalendar2.set(Calendar.MINUTE, 59);
        localCalendar2.set(Calendar.SECOND, 59);
        localCalendar2.set(Calendar.MILLISECOND, 999);
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();
        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }


    private static boolean isSameDay(long paramLong) {
        TimeInfo localTimeInfo = getTodayStartAndEndTime();
        return (paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo.getEndTime());
    }


    private static boolean isYesterday(long paramLong) {
        TimeInfo localTimeInfo = getYesterdayStartAndEndTime();
        return (paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo.getEndTime());
    }

    private static boolean isBeforeYesterday(long paramLong) {
        TimeInfo localTimeInfo = getBeforeYesterdayStartAndEndTime();
        return (paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo.getEndTime());
    }

    public static boolean isSameYear(int year) {
        Calendar cal = Calendar.getInstance();
        int CurYear = cal.get(Calendar.YEAR);
        return CurYear == year;
    }

    public static boolean isSameMonth(long paramLong) {
        Calendar localCalendar = GregorianCalendar.getInstance();
        localCalendar.setTime(new Date(paramLong));
        int oldMonth = localCalendar.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        int CurMonth = cal.get(Calendar.MONTH);
        return CurMonth == oldMonth;
    }

    public static String getCTime(String updatedTime) {
        updatedTime = updatedTime.replace("T", " ");
        updatedTime = updatedTime.replace("Z", " ");
        long mutillTime = getDateTimeToLong(updatedTime);

        return getFriendTimeShowFromLong(mutillTime);
    }

    public static String  getMIAOFen(int duration) {
        String mstr = "";
        String fStr = "";
        int m = duration %60;
        int fen = (duration % ( 60 * 60)) /60 ;
        if(m< 10 ){
             mstr = "0"+m;
        }else{
            mstr = ""+m;
        }
        if(fen < 10){
             fStr = "0"+fen;
        }else{
            fStr = ""+fen;
        }
        return fStr+":"+ mstr;
    }


    public static class TimeInfo {
        private long startTime;
        private long endTime;


        public long getStartTime() {
            return this.startTime;
        }


        public void setStartTime(long paramLong) {
            this.startTime = paramLong;
        }


        public long getEndTime() {
            return this.endTime;
        }


        public void setEndTime(long paramLong) {
            this.endTime = paramLong;
        }
    }


    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compareDateHms(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compareDateShort(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 日期yyyy-MM-dd HH:mm:ss转化为毫秒
     *
     * @param str
     * @return
     */
    public static long getMutillTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 日期2016-12-05T05:58:43Z转化为毫秒
     *
     * @param str
     * @return
     */
    public static long getDateTimeToLong(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 日期yyyy-MM-dd转化为毫秒
     *
     * @param str
     * @return
     */
    public static long getMutillTimeShort(String str) {
        if (str == null) {
            return 0;
        } else if (str.isEmpty()) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 毫秒转化为日期
     *
     * @return
     */
    public static String getStringFromMuillTime(String str) {
        if (str == null) {
            return "";
        } else if (str.isEmpty()) {
            return "";
        }
        Long time = Long.parseLong(str);
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        return sdf.format(d);
    }

    /**
     * 毫秒转化为日期年月日时分秒
     *
     * @return
     */
    public static String getStringFromMuillTimeEntity(long time) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }


    /**
     * 毫秒转化为日期--月-日 -时-分
     *
     * @return
     */
    public static String getStringFromMuillTimeMMDD(String str) {
        if (str == null) {
            return "";
        } else if (str.isEmpty()) {
            return "";
        }
        Long time = Long.parseLong(str);
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(d);
    }

    /**
     * 获取当前的系统时间
     *
     * @return
     */
    public static String getStringNowTimeEntity() {
        Long time = System.currentTimeMillis();
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    //------------红包页面时间封装---------------------------------

    /**
     * 获取两个时间差展示
     * @param before
     * @param last
     * @return
     */
    public static String getRedMoneyTimeShow(String before, String last) {

        String show_time = "";
        if (TextUtils.isEmpty(before) || TextUtils.isEmpty(last)) {
            return show_time;
        } else {

            try {

                show_time = getTimeFriendShow(
                        getTimeZone(before),
                        getTimeZone(last)
                );

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return show_time;
    }

    /**
     * 获取带时区的时间展示
     * @param timeStrInQzon
     * @return
     */
    public static String getTimeShow(String timeStrInQzon){
        try {
            return TimeUtils.formatGMTUnixTime(getTimeZone(timeStrInQzon));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 对比时间两个时间差
     * 返回友好时间展示
     *
     * @param beforeTime
     * @param lastTime
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeFriendShow(long beforeTime, long lastTime) {

        if (beforeTime == 0L) {
            return "";
        }

        String show_time = "";

        if (lastTime != 0L) {
            try {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date now = df.parse(df.format(beforeTime));
                Date date = df.parse(df.format(lastTime));


                long l = now.getTime() - date.getTime();
                if (l < 0) {
                    l = date.getTime() - now.getTime();
                }

                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                long sec = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

                if (day != 0L) {
                    show_time = day + "天" + hour + "时" + min + "分" + sec + "秒";
                } else if (hour != 0L) {
                    show_time = hour + "时" + min + "分" + sec + "秒";
                } else if (min != 0L) {
                    show_time = min + "分" + sec + "秒";
                } else {
                    show_time = sec + "秒";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return show_time;

    }

    //------------聊天页面时间封装---------------------------------

    /**
     * 对比时间两个时间差
     * 超过一分钟显示一次
     *
     * @param time
     * @param before
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(long time, long before) {

        if (time == 0L) {
            return null;
        }
        String show_time = "";
        if (before != 0L) {
            try {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date now = df.parse(df.format(time));
                Date date = df.parse(df.format(before));

                long l = now.getTime() - date.getTime();

                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                if (min >= 1) {
                    //显示 年-月-日 时：分：秒
                    show_time = getTimestampString(time);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            show_time = getTimestampString(time);
        }

        return show_time;
    }

    /**
     * 对比时间两个时间差
     * 超过是否超过2分钟
     *
     * @param time
     * @param before
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean getTimeOutNumberTwo(long time, long before) {

        boolean result=false;

        if (time == 0L) {
            return false;
        }

        if (before != 0L) {
            try {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date now = df.parse(df.format(time));
                Date date = df.parse(df.format(before));

                long l = now.getTime() - date.getTime();

                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);

                if (min >= 2) {
                    result=true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 对比传入时间和系统当前时间
     * 差值 是否 成立
     * @param aminTime
     * @param str
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean getTimeOutInput(long aminTime,String str) {

        boolean result=false;

        if (aminTime == 0L ||  TextUtils.isEmpty(str)) {
            return false;
        }

        long currentTime=System.currentTimeMillis();

        if(aminTime>currentTime){
            return false;
        }

        long calureTime=0;

        switch (str){
            case "OFF":
                result=false;
                break;
            case "3S":
                if (currentTime-aminTime>=3) {
                    result=true;
                }
                break;
            case "4S":
                if (currentTime-aminTime>=4) {
                    result=true;
                }
                break;
            case "5S":
                if (currentTime-aminTime>=5) {
                    result=true;
                }
                break;
            case "6S":
                if (currentTime-aminTime>=6) {
                    result=true;
                }
                break;
            case "7S":
                if (currentTime-aminTime>=7) {
                    result=true;
                }
                break;
            case "8S":
                if (currentTime-aminTime>=8) {
                    result=true;
                }
                break;
            case "9S":
                if (currentTime-aminTime>=9) {
                    result=true;
                }
                break;
            case "10S":
                if (currentTime-aminTime>=10) {
                    result=true;
                }
                break;
            case "30S":
                if (currentTime-aminTime>=30) {
                    result=true;
                }
                break;
            case "1M":
                if (currentTime-aminTime>=60) {
                    result=true;
                }
                break;
            case "2M":
                if (currentTime-aminTime>=60*2) {
                    result=true;
                }
                break;
            case "3M":
                if (currentTime-aminTime>=60*3) {
                    result=true;
                }
                break;
            case "1H":
                if (currentTime-aminTime>=60*60) {
                    result=true;
                }
                break;
            case "1D":
                if (currentTime-aminTime>=24*60*60) {
                    result=true;
                }
                break;
            case "1W":
                if (currentTime-aminTime>=7*24*60*60) {
                    result=true;
                }
                break;
        }

        return result;
    }


    @SuppressLint("SimpleDateFormat")
    private static String returnTime(long time) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(time);
        return date;
    }

    public static long getTimeZone(String timeStr) {
        long time;
        SimpleDateFormat sdf;
        if (TextUtils.isEmpty(timeStr)){
            return System.currentTimeMillis();
        }
        if (timeStr.indexOf("T") != -1 && timeStr.indexOf("Z") != -1){
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT+8"));
        }else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            Date date = sdf.parse(timeStr);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            time = System.currentTimeMillis();
        }
        return time;
    }
}
