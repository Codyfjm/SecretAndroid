package com.codyfjm.secret.atys;

import java.util.ArrayList;
import java.util.List;

import com.codyfjm.secret.R;
import com.codyfjm.secret.net.Comment;
import android.content.Context; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AtyMessageCommentListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Comment> comments = new ArrayList<Comment>();

	public AtyMessageCommentListAdapter(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void addAll(List<Comment> data){
		comments.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear(){
		comments.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Comment getItem(int arg0) {
		return comments.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		if (converView==null) {
			converView = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell, null);
			
			
			converView.setTag(new ListCell((TextView) converView.findViewById(R.id.tvCellLable)));
		}
		
		ListCell lc = (ListCell) converView.getTag();
		
		Comment comment = (Comment) getItem(position);
		
		lc.getTvCellLable().setText(comment.getContent());
		
		return converView;
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
