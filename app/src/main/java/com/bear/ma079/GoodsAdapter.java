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


public class GoodsAdapter extends BaseAdapter {
    Context context;
    List<Goods> GoodsList;
    LayoutInflater inflater;
    private String TAG="GoodsAdapter";

    public GoodsAdapter(Context context, List<Goods> GoodsList){
        this.GoodsList = GoodsList;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return GoodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return GoodsList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_goods, null);
            viewHolder = new ViewHolder();


            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_igoods);
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_igoods_1);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_igoods_2);
            viewHolder.tv3 = (TextView) convertView.findViewById(R.id.tv_igoods_3);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(new Url().urlUploads+GoodsList.get(position).getImage()).into(viewHolder.iv);
        Log.i(TAG,new Url().urlUploads+GoodsList.get(position).getImage());
        viewHolder.tv1.setText(GoodsList.get(position).getName());
        viewHolder.tv2.setText("￥"+GoodsList.get(position).getPrice());
        viewHolder.tv3.setText(GoodsList.get(position).getDescription());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
