package com.codyfjm.secret.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;
import com.codyfjm.secret.ld.MyContacts;
import com.codyfjm.secret.net.UploadContacts;
import com.codyfjm.secret.tools.MD5Tool;

public class AtyTimeline extends Activity {
	
	private String phone_md5,token,contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_timeline);
		
		token = getIntent().getExtras().getString(Config.KEY_TOKEN);
		phone_md5 = MD5Tool.md5(getIntent().getExtras().getString(Config.KEY_PHONE_NUM));
		contacts = MyContacts.getContactsJSONString(this);
		
		new UploadContacts(phone_md5, token, contacts, new UploadContacts.SuccessCallback() {
			
			@Override
			public void onSuccess() {
				
			}
		}, new UploadContacts.FailCallback() {
			
			@Override
			public void onFail(int errorCode) {
				if (errorCode==Config.RESULT_STATUS_INVALID_TOKEN) {
					startActivity(new Intent(AtyTimeline.this,AtyLogin.class));
					finish();
				}else {
					loadMessage();
				}
			}
		});
	}

	//加载数据
	protected void loadMessage() {
		System.out.println(">>>>>>LoadMessage");
	}
}
