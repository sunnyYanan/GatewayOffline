package senseHuge.gateway.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalConfigUtil {
	private static Context context = null;
	public static SharedPreferences sharedPreferences;

	public static SharedPreferences getSharedPreferences() {
		sharedPreferences = getContext().getSharedPreferences("config",
				Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public LocalConfigUtil(Context context) {
		super();
		setContext(context);
		if (sharedPreferences == null) {

			getSharedPreferences();
		}
	}

	public void write(String configName, String value) {

		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(configName, value);
		editor.commit();// 提交修改
	}

	public String read(String name) {
		String result = sharedPreferences.getString(name, "");
		return result;
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		LocalConfigUtil.context = context;
	}
}
