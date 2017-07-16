package com.codyfjm.secret.ld;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.tools.MD5Tool;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class MyContacts {

	public static String getContactsJSONString(Context context) {
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		String phoneNum;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		while(cursor.moveToNext()){
			phoneNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			System.out.println("phonenum:"+phoneNum);
			
			if (phoneNum.charAt(0)=='+' && phoneNum.charAt(1)== '8'&&phoneNum.charAt(2)== '6') {
				phoneNum = phoneNum.substring(3);
			}
			
			jsonObject = new JSONObject();
			try {
				jsonObject.put(Config.KEY_PHONE_MD5, MD5Tool.md5(phoneNum));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
}
