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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main3Activity extends AppCompatActivity {


    @BindView(R.id.tabbar_home)
    RelativeLayout tabbarHome;
    @BindView(R.id.tabbar_select)
    RelativeLayout tabbarDig;
    @BindView(R.id.tabbar_my)
    RelativeLayout tabbarMy;
    @BindView(R.id.tabbar_sell)
    RelativeLayout tabbarSell;
    @BindView(R.id.lv_select)
    ListView lvSelect;
    private String TAG = "Main3Activity---------";
    private String ajax;
    private User user;
    private List<Goods> goodsList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        init();


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        SharedPreferences sharedPreferences= getSharedPreferences("bear", Context.MODE_PRIVATE);
        String uid=sharedPreferences.getInt("userid",0)+"";
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Selectlist?id="+uid;
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
                Toast.makeText(Main3Activity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
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
                            GoodsAdapter goodsAdapter = new GoodsAdapter(Main3Activity.this, goodsList);
                            lvSelect.setAdapter(goodsAdapter);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(Main3Activity.this, "获取失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "发布失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });


        lvSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main3Activity.this, GoodsActivity.class);
                intent.putExtra("goods", goodsList.get(position));
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.tabbar_home)
    public void onTabbarHomeClicked() {
        Intent intent = new Intent(Main3Activity.this, Main1Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_my)
    public void onTabbarMyClicked() {
        Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_select)
    public void onTabbarDigClicked() {
        Intent intent = new Intent(Main3Activity.this, Main3Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tabbar_sell)
    public void onTabbarSellClicked() {
        Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }


}
