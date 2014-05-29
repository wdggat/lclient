package com.liu.activity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.liu.bean.Message;

public class MsgInfoAdapter extends BaseAdapter{
	
	private List<Message> items;
	private Context context;
	private LayoutInflater inflater;
	public MsgInfoAdapter(Context context, List<Message> items) {
		this.items = items;
		this.context = context;
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
		TableLayout layout = null;
		if(msg.isSentByMe())
		    layout = (TableLayout)inflater.inflate(R.layout.layout_msginfo_chatitem_right, null);
		else
			layout = (TableLayout)inflater.inflate(R.layout.layout_msginfo_chatitem_left, null);
		TextView contentTV = (TextView)layout.findViewById(R.id.msginfo_chatitem_content);
		contentTV.setText(msg.getContent());
		TextView datetimeTV = (TextView)layout.findViewById(R.id.msginfo_chatitem_datetime);
		datetimeTV.setText(msg.getFormatedTime());
		return layout;
	}

}
