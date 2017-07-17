package com.codyfjm.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Config {
	
//	public static final String SERVER_URL = "http://demo.eoeschool.com/api/v1/nimings/io";
	public static final String SERVER_URL = "http://192.168.31.144:8080/TestServer/api.jsp";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_ACTION = "action";
	public static final String KEY_PHONE_NUM = "phone";
	public static final String KEY_PHONE_MD5 = "phone_md5";
	public static final String KEY_STATUS = "status";
	
	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_FAIL = 0;
	public static final int RESULT_STATUS_INVALID_TOKEN = 2;
	
	public static final String APP_ID = "com.codyfjm.secret";

	public static final String CHARSET = "utf-8";
	
	public static final String ACTION_GET_CODE = "send_pass";

	public static final String ACTION_LOGIN = "login";

	public static final String KEY_CODE = "code";
	
	public static String getCachedToken(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	
	public static void cacheToken(Context context,String token) {
		Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_TOKEN, token);
		editor.commit();
	}
	
	public static String getCachedPhoneNum(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
	}
	
	public static void cachePhoneNum(Context context,String phoneNum) {
		Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_PHONE_NUM, phoneNum);
		editor.commit();
	}
	
}
