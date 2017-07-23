package com.codyfjm.secret.atys;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;
import com.codyfjm.secret.net.Publish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AtyPublish extends Activity {

	private EditText etMsgContent;
	private String phone_md5,token;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_publish); 
		
		phone_md5 = getIntent().getStringExtra(Config.KEY_PHONE_MD5);
		token = getIntent().getStringExtra(Config.KEY_TOKEN);
		
		etMsgContent = (EditText) findViewById(R.id.etMsgContent);
		
		
		findViewById(R.id.btnPublish).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(TextUtils.isEmpty(etMsgContent.getText().toString())){
					Toast.makeText(AtyPublish.this, "消息不能为空！", Toast.LENGTH_LONG).show();
					return;
				}
				
				final ProgressDialog pdDialog = ProgressDialog.show(AtyPublish.this, "链接中","连接到服务器，请等待");
				new Publish(phone_md5, token, etMsgContent.getText().toString(), new Publish.SuccessCallback() {
					
					@Override
					public void onSuccess() {
						
						pdDialog.dismiss();
						setResult(Config.ACTIVITY_RESULT_NEED_REFRESH);
						
						Toast.makeText(AtyPublish.this, "发送消息成功", Toast.LENGTH_LONG).show();
						finish();
					}
				}, new Publish.FailCallback() {
					
					@Override
					public void onFail(int errorCode) {
						
						pdDialog.dismiss();
						
						if (errorCode==Config.RESULT_STATUS_INVALID_TOKEN) {
							startActivity(new Intent(AtyPublish.this,AtyLogin.class));
							finish();
						}else {
							Toast.makeText(AtyPublish.this, "发送消息失败，请稍后重试", Toast.LENGTH_LONG).show();
						}
					}
				});
				
			}
		});
	}
}
