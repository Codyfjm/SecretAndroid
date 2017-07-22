package com.codyfjm.secret.atys;

import java.util.List;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;
import com.codyfjm.secret.net.Comment;
import com.codyfjm.secret.net.GetComment;
import com.codyfjm.secret.net.GetComment.FailCallback;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AtyMessage extends ListActivity {
	
	private String phone_md5,msg,msgId,token;
	private TextView tvMessage;
	private AtyMessageCommentListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_message);
		
		adapter = new AtyMessageCommentListAdapter(this);
		setListAdapter(adapter);
		
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		
		Intent data=getIntent();
		phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
		msgId = data.getStringExtra(Config.KEY_MSG_ID); 
		msg = data.getStringExtra(Config.KEY_MSG);
		token = data.getStringExtra(Config.KEY_TOKEN);
		
		tvMessage.setText(msg);
		
		final ProgressDialog pdDialog = ProgressDialog.show(AtyMessage.this, "链接中","连接到服务器，请等待");
		new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallback() {
			
			@Override
			public void onSuccess(String msgId, int page, int perpage,
					List<Comment> comments) {
				pdDialog.dismiss();
				
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
