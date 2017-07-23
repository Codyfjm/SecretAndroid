package com.codyfjm.secret.atys;

import java.util.List;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;
import com.codyfjm.secret.net.Comment;
import com.codyfjm.secret.net.GetComment;
import com.codyfjm.secret.net.GetComment.FailCallback;
import com.codyfjm.secret.net.PubComment;
import com.codyfjm.secret.tools.MD5Tool;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AtyMessage extends ListActivity {
	
	private String phone_md5,msg,msgId,token;
	private TextView tvMessage;
	private EditText etComment;
	private AtyMessageCommentListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_message);
		
		adapter = new AtyMessageCommentListAdapter(this);
		setListAdapter(adapter);
		
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		etComment = (EditText) findViewById(R.id.etComment);
		
		Intent data=getIntent();
		phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
		msgId = data.getStringExtra(Config.KEY_MSG_ID); 
		msg = data.getStringExtra(Config.KEY_MSG);
		token = data.getStringExtra(Config.KEY_TOKEN);
		
		tvMessage.setText(msg);
		
		//获取对方评论
		getComments();
		
		//发布评论
		publishComments();
		
	}

	private void publishComments() {
		findViewById(R.id.btnSendComment).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (TextUtils.isEmpty(etComment.getText())) {
					Toast.makeText(AtyMessage.this,"评论内容不能为空",Toast.LENGTH_LONG).show();
					return;
				}
				
				final ProgressDialog pdDialog = ProgressDialog.show(AtyMessage.this, "链接中","连接到服务器，请等待");
				new PubComment(MD5Tool.md5(Config.getCachedPhoneNum(AtyMessage.this)), token, etComment.getText().toString(), msgId,
						new PubComment.SuccessCallback() {
					
					@Override
					public void onSuccess() {
						pdDialog.dismiss();
						
						etComment.setText("");
						
						getComments();
					}
				}, new PubComment.FailCallback() {
					
					@Override
					public void onFail(int errorCode) {
						
						pdDialog.dismiss();
						
						if (errorCode==Config.RESULT_STATUS_INVALID_TOKEN) {
							
							startActivity(new Intent(AtyMessage.this,AtyLogin.class));
							finish();
							
						}else {
							Toast.makeText(AtyMessage.this, "发布评论失败，请稍后重试", Toast.LENGTH_LONG).show();
						}
					}
				});
				
			}
		});
	}

	private void getComments() {
		final ProgressDialog pdDialog = ProgressDialog.show(AtyMessage.this, "链接中","连接到服务器，请等待");
		new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallback() {
			
			@Override
			public void onSuccess(String msgId, int page, int perpage,
					List<Comment> comments) {
				pdDialog.dismiss();
				
				adapter.clear();
				
				adapter.addAll(comments);
				
			}
		}, new GetComment.FailCallback() {
			
			@Override
			public void onFail(int errorCode) {
				pdDialog.dismiss();

				if (errorCode==Config.RESULT_STATUS_INVALID_TOKEN) {
					startActivity(new Intent(AtyMessage.this,AtyLogin.class));
					finish();
				}else {
					Toast.makeText(AtyMessage.this, "加载评论失败，请重新登陆",Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
