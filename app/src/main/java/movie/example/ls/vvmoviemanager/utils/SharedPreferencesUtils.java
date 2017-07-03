package movie.example.ls.vvmoviemanager.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created By: lsw
 * Author : lsw
 * Date :  2016/3/16
 * Email : lsw
 */
public class SharedPreferencesUtils {

	private SharedPreferences sharedPreferences;
	private static SharedPreferencesUtils appSharedPreferences;

	private SharedPreferencesUtils(Context context) {
		sharedPreferences = context.getSharedPreferences("SOURCE", Activity.MODE_PRIVATE);
	}

	public static SharedPreferencesUtils getInstance(Context context) {
		if (null == appSharedPreferences) {
			appSharedPreferences = new SharedPreferencesUtils(context);
		}
		return appSharedPreferences;
	}

	public String get(String key) {
		if (null == sharedPreferences) {
			return "";
		}
		String result = sharedPreferences.getString(key, "");
		if (!TextUtils.isEmpty(result)) {
			return result;
		}
		return "";
	}
	public void set(String key, String value) {
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}

	public Boolean getBoolean(String key) {
		if (null == sharedPreferences) {
			return false;
		}
		return sharedPreferences.getBoolean(key,false);
	}

	public void setBoolean(String key, Boolean value) {
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
	}

	public int getInt(String key) {
		if (null == sharedPreferences) {
			return 0;
		}
		return sharedPreferences.getInt(key,0);
	}

	public void setInt(String key, int value) {
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putInt(key, value);
			editor.commit();
		}
	}

	public void remove(String... key) {
		for (String k : key) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.remove(k);
			editor.commit();
		}
	}

}
