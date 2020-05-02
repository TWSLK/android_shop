package com.bear.ma079;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class MsgAdapter extends BaseAdapter {
    Context context;
    List<Msg> MsgList;
    LayoutInflater inflater;
    private String TAG="MsgAdapter";

    public MsgAdapter(Context context, List<Msg> MsgList){
        this.MsgList = MsgList;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return MsgList.size();
    }

    @Override
    public Object getItem(int position) {
        return MsgList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_msg, null);
            viewHolder = new ViewHolder();


            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_imsg_1);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_imsg_2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv1.setText(MsgList.get(position).getUname()+":");
        viewHolder.tv2.setText(""+MsgList.get(position).getMsg());
        return convertView;
    }
    class ViewHolder{
        TextView tv1;
        TextView tv2;
    }
}
