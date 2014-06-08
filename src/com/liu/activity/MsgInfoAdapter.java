package com.liu.activity;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liu.bean.Message;

public class MsgInfoAdapter extends BaseAdapter{
	
	private List<Message> items;
	private LayoutInflater inflater;
	public MsgInfoAdapter(Context context, List<Message> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message msg = (Message)getItem(position);
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		LinearLayout layout = null;
		if(msg.getFrom().equals(BaseActivity.ME)) {
		    layout = (LinearLayout)inflater.inflate(R.layout.layout_msginfo_chatitem_right, null);
		    layout.setGravity(Gravity.RIGHT);
//		    lp.gravity = Gravity.RIGHT;
		} else {
			layout = (LinearLayout)inflater.inflate(R.layout.layout_msginfo_chatitem_left, null);
			layout.setGravity(Gravity.LEFT);
		}
		layout.setLayoutParams(lp);
		TextView contentTV = (TextView)layout.findViewById(R.id.msginfo_chatitem_content);
		contentTV.setText(msg.getContent());
		TextView datetimeTV = (TextView)layout.findViewById(R.id.msginfo_chatitem_datetime);
		datetimeTV.setText(msg.getFormatedTime());
		return layout;
	}

}
