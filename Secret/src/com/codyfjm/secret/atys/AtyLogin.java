package com.codyfjm.secret.atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.codyfjm.secret.R;
import com.codyfjm.secret.net.GetCode;

public class AtyLogin extends Activity {
	
	private EditText etPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_login);
		
		etPhone = (EditText) findViewById(R.id.etPhoneNum);
		
		findViewById(R.id.btnGetCode).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(etPhone.getText().toString())) {
					Toast.makeText(AtyLogin.this, "电话号码不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				
				final ProgressDialog pdDialog = ProgressDialog.show(AtyLogin.this, "链接中","连接到服务器，请等待");
				
				new GetCode(etPhone.getText().toString(), new GetCode.SuccessCallback() {
					
					@Override
					public void onSuccess() {
						pdDialog.dismiss();
						Toast.makeText(AtyLogin.this, "验证成功", Toast.LENGTH_LONG).show();
					}
				}, new GetCode.FailCallback() {
					
					@Override
					public void onFail() {
						pdDialog.dismiss();
						Toast.makeText(AtyLogin.this, "验证码不能为空", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}
}
