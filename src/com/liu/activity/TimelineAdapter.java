package com.liu.activity;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.liu.message.TimelineListItem;

public class TimelineAdapter extends BaseAdapter {
	private static final int CONTENT_PREFIX_LENGTH = 10;
//	private Context context;
	private List<TimelineListItem> items;
	private LayoutInflater inflater;
	public TimelineAdapter(Context context, List<TimelineListItem> items) {
//		this.context = context;
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
		TimelineListItem item = items.get(position);
		TableLayout layout = (TableLayout)inflater.inflate(R.layout.layout_timeline_msgitem, null);
		TextView associateTV = (TextView)layout.findViewById(R.id.timeline_msgitem_associate);
		associateTV.setText(item.getAssociate());
		TextView unreadTv = (TextView)layout.findViewById(R.id.timeline_msgitem_unread);
		if(item.getUnread() > 0)
			unreadTv.setText("(" + item.getUnread() + ")");
		TextView datetimeTV = (TextView)layout.findViewById(R.id.timeline_msgitem_datetime);
		datetimeTV.setText(item.getFormatedTime());
		TextView contentTV = (TextView)layout.findViewById(R.id.timeline_msgitem_msgglimpse);
		contentTV.setLines(1);
		contentTV.setText(showContentStr(item.getContent()));
		return layout;
	}
	
	private String showContentStr(String content) {
		if(StringUtils.isBlank(content))
			return "";
		if(content.length() < CONTENT_PREFIX_LENGTH) {
			return content;
		} else {
			return content.substring(0, CONTENT_PREFIX_LENGTH).trim() + "...";
		}
	}

}
