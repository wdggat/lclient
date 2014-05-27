package com.liu.activity;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liu.bean.Message;

public class MsgInfoAdapter extends BaseAdapter{
	
	private List<Message> items;
	private Context context;
	public MsgInfoAdapter(Context context, List<Message> items) {
		this.items = items;
		this.context = context;
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
		TextView v = new TextView(context);
		if(!msg.isSentByMe()) {
		    v.setTextAppearance(context, R.style.msginfo_left);
		    v.setGravity(Gravity.LEFT);
		    v.setPadding(5, 5, 150, 5);
		} else {
			v.setTextAppearance(context, R.style.msginfo_right);
			v.setBackgroundResource(R.color.light_blue);
			v.setGravity(Gravity.RIGHT);
			v.setPadding(150, 5, 5, 5);
		}
		
		v.setText(msg.toShowInMsgInfo());
		return v;
	}

}
