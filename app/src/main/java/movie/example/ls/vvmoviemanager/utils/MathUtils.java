package movie.example.ls.vvmoviemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 各种数学工具
 * 
 * @author wangpc
 */
public class MathUtils {

    /**
     * <p>
     * 方法描述: 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * </p>
     * 
     * @param context
     *            承接上下文
     * @param dpValue
     *            将要转换的dp值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * <p>
     * 方法描述: 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * </p>
     * 
     * @param context
     *            承接上下文
     * @param pxValue
     *            将要转换的px值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * <p>
     * 方法描述: 已知两个经纬度，求余弦角（正北方向顺时针夹角）
     * </p>
     * 
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     */
    public static double dot2angle(double latitude1, double longitude1, double latitude2, double longitude2) {
        double a = latitude2 - latitude1;
        double b = longitude2 - longitude1;
        double course = Math.atan(b / a) / Math.PI * 180;
        if (a < 0) {
            course += 180;
        }
        return course;
    }

    /**
     * <p>
     * 方法描述:实际距离换算成字符串
     * </p>
     * 
     * @param distance
     * @return
     */
    public static String getDistance(float distance) {
        float dis = distance / 1000;
        if (distance < 1000.0) {
            int d = (int) distance;
            return String.valueOf(d) + "m";
        } else if (dis < 100.0) {
            return String.valueOf(dis).substring(0, 4) + "km";
        } else {
            return (int) dis + "km";
        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取距离当前时间几天或几分钟 规则：当天显示：几分钟几小时前 1-100天：显示几天前 当年显示：几月几日 非当年显示：某年某月某日
     * 
     * @param date
     *            接收String类型的日期字符串，格式为yyyy-MM-dd HH:mm:ss
     * @author changbl
     * @return
     * 
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimesAgo(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date now = new Date();
                // long timediff = now.getTime() - sdf.parse(date).getTime();
                long timediff = now.getTime() - Long.parseLong(date);
                if (timediff <= 1) {
					return "1秒前";
				}
                long second = timediff / 1000;
                long miniute = timediff / 1000 / 60;
                long hour = miniute / 60;
                long day = hour / 24;
                if (miniute >= 60) {
                    if (hour >= 24) {
                        if (day >= 1 && day < 100) {
                            return day + "天前";
                        } else {
                            if (date.substring(0, 4).equals(sdf.format(now).substring(0, 4))) {
                                return date.substring(5, 10);
                            }
                            return date.substring(0, 10);
                        }
                    }
                    return hour + "小时前";
                } else if (miniute == 0) {
//                    return "刚刚";
                    return second + "秒前";
                }
                return miniute + "分钟前";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 用时间戳算时间差 格式 1小时1分1秒
     * 
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 时间差
     */
    public static String getTimeMargin(String startTime, String endTime) {
        long startT = Long.parseLong(startTime);
        long endT = Long.parseLong(endTime);
        long marginT = endT - startT;
        long days = marginT / (1000 * 60 * 60 * 24);
        long hours = (marginT % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (marginT % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (marginT % (1000 * 60)) / 1000;
        String day = "";
        String hour = "";
        String minute = "";
        String second = "1";
        if (days > 0) {
            day = days + "天";
        }
        if (hours > 0) {
            hour = hours + "时";
        }
        if (minutes > 0) {
            minute = minutes + "分";
        }
        if (seconds > 0) {
            second = seconds + "秒";
        }
        return day + hour + minute + second;
    }

    public static String getTime24(String time) {
        Date date = new Date(Long.valueOf(time));
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }
}
