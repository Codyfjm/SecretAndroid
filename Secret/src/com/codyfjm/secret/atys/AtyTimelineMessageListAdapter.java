package com.codyfjm.secret.atys;

import java.util.ArrayList;
import java.util.List;

import com.codyfjm.secret.R;
import com.codyfjm.secret.net.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AtyTimelineMessageListAdapter extends BaseAdapter{
	
	private Context context;
	private List<Message> data = new ArrayList<Message>();
	
	public AtyTimelineMessageListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		if (converView!=null) {
			converView = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_message, null);
			
			TextView tvCellLable = (TextView) converView.findViewById(R.id.tvCellLable);
		}
		return converView;
	}
	
	public Context getContext(){
		return context;
	}
	
	public void addAll(List<Message> data){
		this.data.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}

}
