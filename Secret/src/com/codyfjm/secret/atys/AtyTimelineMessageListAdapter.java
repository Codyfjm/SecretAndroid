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
			converView = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell, null);
			
			
			converView.setTag(new ListCell((TextView) converView.findViewById(R.id.tvCellLable)));
		}
		
		ListCell lc = (ListCell) converView.getTag();
		
		Message msg = (Message) getItem(position);
		
		lc.getTvCellLable().setText(msg.getMsg());
		
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
	
	private static class ListCell {
		private TextView tvCellLable;
		
		public ListCell(TextView tvCellLable) {
			this.tvCellLable = tvCellLable;
		}
		
		
		public TextView getTvCellLable() {
			return tvCellLable;
		}
	}

}
