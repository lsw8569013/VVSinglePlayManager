package movie.example.ls.vvmoviemanager.utils;

import android.content.Context;
import android.graphics.Paint;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类：身份证-手机号-邮箱-运营商等等
 */
public class ToolUtils {

	private static final String Phone="^1(3|4|5|7|8)\\d{9}$";

	/**
	 * 是否是手机号码
	 * @return
     */
	public static boolean isPhoneNum(String str){
		if(TextUtils.isEmpty(str)){
			return false;
		}
		return str.matches(Phone);
	}

	/**
	 * 判断是否是身份证
	 * 
	 * @param identifyCard
	 */
	public static boolean isIdentifyCard(String identifyCard){
		Pattern p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Matcher m = p.matcher(identifyCard);
		return m.matches();		
	}
	/**
	 * 判断电话号码是否有效
	 * 移动：134、135、136、137、138、139、147、150、151、152、157、158、159、182、183、187、188
	 * 联通：130、131、132、145、155、156、185、186
	 * 电信：133、153、180、181、189
	 * 虚拟运营商：17x
	 */
	public static boolean isMobileNO(String number) {
		if (number.startsWith("+86")) {
			number = number.substring(3);
		}

		if (number.startsWith("+") || number.startsWith("0")) {
			number = number.substring(1);
		}

		number = number.replace(" ", "").replace("-", "");
		System.out.print("号码：" + number);

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(number);

		return m.matches();
	}

	/**
	 * 号码的运营商类型
	 * @param number
	 * @return
	 */
	public static String getMobileType(String number) {
		String type = "未知用户";
		Pattern p = Pattern.compile("^(([4,8]00))\\d{7}$");
		if (p.matcher(number).matches())
			return "企业电话";

		if (number.startsWith("+86")) {
			number = number.substring(3);
		}

		if (number.startsWith("+") || number.startsWith("0")) {
			number = number.substring(1);
		}

		number = number.replace(" ", "").replace("-", "");
		System.out.print("号码：" + number);

		p = Pattern.compile("^((13[4-9])|(147)|(15[0-2,7-9])|(18[2,3,7,8]))\\d{8}$");
		if (p.matcher(number).matches())
			return "移动用户";

		p = Pattern.compile("^((13[0-2])|(145)|(15[5,6])|(18[5,6]))\\d{8}$");
		if (p.matcher(number).matches())
			return "联通用户";

		p = Pattern.compile("^((1[3,5]3)|(18[0,1,9]))\\d{8}$");
		if (p.matcher(number).matches())
			return "电信用户";

		p = Pattern.compile("^((17[0-9]))\\d{8}$");
		if (p.matcher(number).matches())
			return "虚拟运营端";

		if (number.length() >= 7 && number.length() <= 12)
			return "固话用户";

		return type;
	}

	/**
	 * 获取随机数
	 * @param iRdLength
	 * @return
	 */
	public static String getRandom(int iRdLength) {
		Random rd = new Random();
		int iRd = rd.nextInt();
		if (iRd < 0) { // 负数时转换为正数
			iRd *= -1;
		}
		String sRd = String.valueOf(iRd);
		int iLgth = sRd.length();
		if (iRdLength > iLgth) { // 获取数长度超过随机数长度
			return digitToString(iRd, iRdLength);
		} else {
			return sRd.substring(iLgth - iRdLength, iLgth);
		}
	}

	/**
	 * 把一个整数转化为一个n位的字符串
	 * @param digit
	 * @param n
	 * @return
	 */
	public static String digitToString(int digit, int n) {
		String result = "";
		for (int i = 0; i < n - String.valueOf(digit).length(); i++) {
			result = result + "0";
		}
		result = result + String.valueOf(digit);
		return result;
	}
		
	/**
	 * 计算MD5
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("utf-8"));
			byte[] result = md.digest();
			StringBuffer sb = new StringBuffer(32);
			for (int i = 0; i < result.length; i++) {
				int val = result[i] & 0xff;
				if (val <= 0xf) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();//.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 四舍五入
	 */
	public static String double_convert(double value) {
		long l1 = Math.round(value * 100);//四舍五入
		double ret = l1 / 100.0;//注意：使用100.0,而不是 100
		if (ret - (int) ret == 0) {
			return (int) ret + "";
		} else {
			return ret + "";
		}
	}

	/**
	 * 根据字符串，计算出其占用的宽度
	 * @param str
	 * @param textsize
	 * @return
	 */
	public static float getTextWidthFontPx(String str, float textsize) {
		Paint mPaint = new Paint();
		mPaint.setTextSize(textsize);
		return str.length() * mPaint.getFontSpacing();
	}
	/**
	 * 根据经纬度计算距离
	 * @param longitude1
	 * @param latitude1
	 * @return
	 */
	private static final double EARTH_RADIUS =6378137.0;

	/**
	 * 根据经纬度计算两点的距离
	 * @param longitude1 第一点经度
	 * @param latitude1  第一点维度
	 * @param longitude2 第二点经度
	 * @param latitude2  第二点维度
     * @return
     */
	 public static double getDistance(double longitude1, double latitude1,  
	   double longitude2, double latitude2) {  
	  double Lat1 = rad(latitude1);  
	  double Lat2 = rad(latitude2);  
	  double a = Lat1 - Lat2;  
	  double b = rad(longitude1) - rad(longitude2);  
	  double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
	    + Math.cos(Lat1) * Math.cos(Lat2)
	    * Math.pow(Math.sin(b / 2), 2)));
	  s = s * EARTH_RADIUS;  
	  s = Math.round(s * 10000) / 10000;
	  return s;  
	 }  
	 private static double rad(double d) {  
	  return d * Math.PI / 180.0;
	 }  
	 /** 
	     * 是否含有指定字符 
	     *  
	     * @param expression 
	     * @param text 
	     * @return 
	     */  
	    private static boolean matchingText(String expression, String text) {
	        Pattern p = Pattern.compile(expression);
	        Matcher m = p.matcher(text);
	        boolean b = m.matches();  
	        return b;  
	    }  
	  
	    /** 
	     * 邮政编码 
	     * @param zipcode
	     * @return 
	     */  
	    public static boolean isZipcode(String zipcode) {
	        Pattern p = Pattern.compile("[0-9]\\d{5}");
	        Matcher m = p.matcher(zipcode);
	        System.out.println(m.matches() + "-zipcode-");
	        return m.matches();  
	    }  
	  
	    /** 
	     * 邮件格式 
	     *  
	     * @param email 
	     * @return 
	     */  
	    public static boolean isValidEmail(String email) {
	        Pattern p = Pattern
	                .compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");  
	        Matcher m = p.matcher(email);
	        System.out.println(m.matches() + "-email-");
	        return m.matches();  
	    }  
	  
	    /** 
	     * 固话号码格式 
	     *  
	     * @param telfix 
	     * @return 
	     */  
	    public static boolean isTelfix(String telfix) {
	        Pattern p = Pattern.compile("d{3}-d{8}|d{4}-d{7}");
	        Matcher m = p.matcher(telfix);
	        System.out.println(m.matches() + "-telfix-");
	        return m.matches();  
	    }  
	  
	    /** 
	     * 用户名匹配 
	     *  
	     * @param name 
	     * @return 
	     */  
	    public static boolean isCorrectUserName(String name) {
	        Pattern p = Pattern.compile("([A-Za-z0-9]){2,10}");
	        Matcher m = p.matcher(name);
	        System.out.println(m.matches() + "-name-");
	        return m.matches();  
	    }  
	  
	    /** 
	     * 密码匹配，以字母开头，长度 在6-18之间，只能包含字符、数字和下划线。 
	     *  
	     * @param pwd 
	     * @return 
	     *  
	     */  
	    public static boolean isCorrectUserPwd(String pwd) {
	        Pattern p = Pattern.compile("\\w{6,18}");
	        Matcher m = p.matcher(pwd);
	        System.out.println(m.matches() + "-pwd-");
	        return m.matches();  
	    }  
	    /** 
	     * 计算剩余日期 
	     *  
	     * @param
	     * @return 
	     */  
	    public static String calculationRemainTime(String endTime, long countDown) {
	  
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        try {  
	            Date now = new Date(System.currentTimeMillis());// 获取当前时间
	            Date endData = df.parse(endTime);
	            long l = endData.getTime() - countDown - now.getTime();  
	            long day = l / (24 * 60 * 60 * 1000);  
	            long hour = (l / (60 * 60 * 1000) - day * 24);  
	            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);  
	            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);  
	            return "剩余" + day + "天" + hour + "小时" + min + "分" + s + "秒";  
	        } catch (ParseException e) {
	            e.printStackTrace();  
	        }  
	        return "";  
	    }  
	  
	    public static void showLongToast(Context act, String pMsg) {
	    }
	  
	    public static void showShortToast(Context act, String pMsg) {
	    }
	  
	    /** 
	     * 获取手机Imei号 
	     *  
	     * @param context 
	     * @return 
	     */  
	    public static String getImeiCode(Context context) {
	        TelephonyManager tm = (TelephonyManager) context
	                .getSystemService(Context.TELEPHONY_SERVICE);
	        return tm.getDeviceId();  
	    }

		/**
		 * 获取手机的版本号
		 * @return
		 */
		public static String getSystemVersion(){
			return android.os.Build.VERSION.RELEASE;
		}

	  
	    /** 
	     * @author sunglasses 
	     * @param listView 
	     * @category 计算listview的高度 
	     */  
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
	        params.height = totalHeight  
	                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	        listView.setLayoutParams(params);  
	    }

	/**
	 * 判断当前手机运行的语言环境
	 * @param mContext
	 * @return
	 */
	public boolean isZh(Context mContext) {
		Locale locale = mContext.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	/**
	 * 判断当前手机运行的语言环境
	 * @param mContext
	 * @return
	 */
	public boolean isEn(Context mContext){
		Locale locale=mContext.getResources().getConfiguration().locale;
		if(locale.getLanguage().endsWith("en")){
			return true;
		}else{
			return false;
		}
	}
}
