package com.codyfjm.secret.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codyfjm.secret.Config;

public class Timeline {

	public Timeline(String phone_md5,String token,int page,int prepage,final SuccessCallback successCallback,final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				
				JSONObject obj;
				try {
					obj = new JSONObject(result);
					
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if (successCallback!=null) {
							successCallback.onSuccess(obj.getInt(Config.KEY_PAGE), obj.getInt(Config.KEY_PREPAGE), obj.getJSONArray(Config.KEY_TIMELINE));
						}
						break;

					default:
						if (failCallback!=null) {
							failCallback.onFail();
						}
						break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					
					if (failCallback!=null) {
						failCallback.onFail();
					}
				}
			
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				if(failCallback!=null){
					failCallback.onFail();
				}
			}
		}, Config.KEY_ACTION,Config.ACTION_TIMELINE,
		Config.KEY_PHONE_MD5,phone_md5,Config.KEY_TOKEN,token,
		Config.KEY_PAGE,page+"",
		Config.KEY_PREPAGE,prepage+"");
	}
	
	public static interface SuccessCallback{
		void onSuccess(int page,int prepage,JSONArray timeline);
	}
	
	public static interface FailCallback{
		void onFail();
	}
}
