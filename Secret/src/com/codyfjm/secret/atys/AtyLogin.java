package com.codyfjm.secret.atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;
import com.codyfjm.secret.net.GetCode;
import com.codyfjm.secret.net.Login;
import com.codyfjm.secret.tools.MD5Tool;

public class AtyLogin extends Activity {
	
	private EditText etPhone,etCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_login);
		
		etPhone = (EditText) findViewById(R.id.etPhoneNum);
		etCode = (EditText) findViewById(R.id.etCode);
		
		//获取验证码  
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
						Toast.makeText(AtyLogin.this, "验证码获取失败", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
		//登陆验证
		findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//手机号判空处理       
				if (TextUtils.isEmpty(etPhone.getText().toString())) {
					Toast.makeText(AtyLogin.this, "电话号码不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				//验证码判空处理       
				if (TextUtils.isEmpty(etCode.getText().toString())) {
					Toast.makeText(AtyLogin.this, "验证码不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				
				new Login(MD5Tool.md5(etPhone.getText().toString()), etCode.getText().toString(), new Login.SuccessCallback() {
					
					@Override
					public void onSuccess(String token) {
						Config.cacheToken(AtyLogin.this, token);
						Config.cachePhoneNum(AtyLogin.this, etPhone.getText().toString());
						
						Intent intent = new Intent(AtyLogin.this,AtyTimeline.class);
						intent.putExtra(Config.KEY_TOKEN, token);
						intent.putExtra(Config.KEY_PHONE_NUM, etPhone.getText().toString());
						startActivity(intent);
						
						finish();
					}
				}, new Login.FailCallback() {
					
					@Override
					public void onFail() {
						Toast.makeText(AtyLogin.this, "登录失败，请重新登陆！", Toast.LENGTH_LONG).show();
					}
				});
				
			}
		});
	}
	
	
}
