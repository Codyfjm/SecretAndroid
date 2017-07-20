package com.codyfjm.secret.atys;

import java.util.List;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.codyfjm.secret.Config;
import com.codyfjm.secret.R;
import com.codyfjm.secret.ld.MyContacts;
import com.codyfjm.secret.net.Message;
import com.codyfjm.secret.net.Timeline;
import com.codyfjm.secret.net.UploadContacts;
import com.codyfjm.secret.tools.MD5Tool;

public class AtyTimeline extends ListActivity {
	
	private String phone_md5,token,contacts;
	private AtyTimelineMessageListAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_timeline);
		
		adapter = new AtyTimelineMessageListAdapter(this);
		setListAdapter(adapter);
		
		token = getIntent().getExtras().getString(Config.KEY_TOKEN);
		phone_md5 = MD5Tool.md5(getIntent().getExtras().getString(Config.KEY_PHONE_NUM));
		contacts = MyContacts.getContactsJSONString(this);
		
		final ProgressDialog pdDialog = ProgressDialog.show(AtyTimeline.this, "链接中","连接到服务器，请等待");
		new UploadContacts(phone_md5, token, contacts, new UploadContacts.SuccessCallback() {
			
			@Override
			public void onSuccess() {
				loadMessage();
				pdDialog.dismiss();
			}
		}, new UploadContacts.FailCallback() {
			
			
			@Override
			public void onFail(int errorCode) {
				
				pdDialog.dismiss();
				
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
		
		final ProgressDialog pdDialog = ProgressDialog.show(AtyTimeline.this, "链接中","连接到服务器，请等待");
		new Timeline(phone_md5, token, 1, 20, new Timeline.SuccessCallback() {
			
			@Override
			public void onSuccess(int page, int prepage, List<Message> timeline) {
				pdDialog.dismiss();
				
				//加载数据并且适配
				adapter.addAll(timeline);
			}
		}, new Timeline.FailCallback() {
			
			@Override
			public void onFail() {
				pdDialog.dismiss();
				Toast.makeText(AtyTimeline.this, "加载消息失败，请稍后重试！", Toast.LENGTH_LONG).show();
			}	
		});
	}
	
	//点击每一项的事件
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		 
		Message msg = (Message) adapter.getItem(position);
		Intent intent = new Intent(this,AtyMessage.class);
		intent.putExtra(Config.KEY_MSG, msg.getMsg());
		intent.putExtra(Config.KEY_MSG_ID, msg.getMsgId());
		intent.putExtra(Config.KEY_PHONE_MD5, msg.getPhone_md5());
		startActivity(intent);
	}
}
