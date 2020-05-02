package com.bear.ma079;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {


    @BindView(R.id.lv_order)
    ListView lvOrder;
    private String TAG = "OrderActivity---------";
    private String ajax;
    private List<Goods> goodsList = new ArrayList<>();
    private User user;
    private String fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        fun = getIntent().getStringExtra("fun");
        SharedPreferences sharedPreferences= getSharedPreferences("bear", Context.MODE_PRIVATE);
        String uid=sharedPreferences.getInt("userid",0)+"";
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Orderlist?fun="+ fun +"&id="+uid;
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "连接失败: " + e.getMessage() + e.toString());
                Looper.prepare();
                Toast.makeText(OrderActivity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到数据: " + ajax);
                Gson gson = new Gson();
                Looper.prepare();
                try {
                    goodsList = gson.fromJson(ajax, new TypeToken<List<Goods>>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GoodsAdapter goodsAdapter = new GoodsAdapter(OrderActivity.this, goodsList);
                            lvOrder.setAdapter(goodsAdapter);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(OrderActivity.this, "获取失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "获取失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fun.equals("sell")){
                    Intent intent = new Intent(OrderActivity.this, OrderEditActivity.class);
                    intent.putExtra("goods", goodsList.get(position));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(OrderActivity.this, GoodsActivity.class);
                    intent.putExtra("goods", goodsList.get(position));
                    startActivity(intent);
                }

            }
        });

    }

}
