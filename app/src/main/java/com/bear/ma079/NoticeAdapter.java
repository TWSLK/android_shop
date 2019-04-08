package com.bear.ma079;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class NoticeAdapter extends BaseAdapter {
    List<Notice> NoticeList;
    LayoutInflater inflater;
    private String TAG="NoticeAdapter";

    public NoticeAdapter(Context context, List<Notice> NoticeList){
        this.NoticeList = NoticeList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return NoticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return NoticeList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_notice, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_item_notice_title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_item_notice_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(NoticeList.get(position).getTitle());
        viewHolder.time.setText(NoticeList.get(position).getTime());
        return convertView;
    }
    class ViewHolder{
        TextView title;
        TextView time;
    }
}
