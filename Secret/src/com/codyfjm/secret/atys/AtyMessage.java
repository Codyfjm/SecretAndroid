package com.codyfjm.secret.atys;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AtyMessage extends ListActivity {
	
	private String phone_md5,msg,msgId;
	private TextView tvMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_message);
		
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		
		Intent data=getIntent();
		phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
		msg = data.getStringExtra(Config.KEY_MSG);
		msgId = data.getStringExtra(Config.KEY_MSG_ID); 
		
		tvMessage.setText(msg);
		
		
	}
}
