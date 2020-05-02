package com.bear.ma079;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Main1Activity extends AppCompatActivity {


    @BindView(R.id.et_main_search)
    EditText etMainSearch;
    @BindView(R.id.bt_main_search)
    Button btMainSearch;
    @BindView(R.id.tabbar_home)
    RelativeLayout tabbarHome;
    @BindView(R.id.tabbar_select)
    RelativeLayout tabbarDig;
    @BindView(R.id.tabbar_my)
    RelativeLayout tabbarMy;
    @BindView(R.id.tabbar_sell)
    RelativeLayout tabbarSell;
    @BindView(R.id.lv_main)
    ListView lvMain;
    @BindView(R.id.sp_mian)
    Spinner spMian;
    @BindView(R.id.bt_mian1_tuijian)
    TextView btMian1Tuijian;
    private String TAG = "Main1Activity---------";
    private String ajax;
    private List<Goods> goodsList = new ArrayList<>();
    private User user;
    private int spIndex = 0;
    private String[] types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        types = new String[]{"生活用品", "电子用品", "电脑配件", "图书"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        spMian.setAdapter(adapter);
        spMian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Goodslist";
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
                Toast.makeText(Main1Activity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
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
                            GoodsAdapter goodsAdapter = new GoodsAdapter(Main1Activity.this, goodsList);
                            lvMain.setAdapter(goodsAdapter);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(Main1Activity.this, "获取失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "发布失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });
//        for (int i = 0; i < 10; i++) {
//            Goods goods = new Goods("物品", i + "", "物美价廉", "校内");
//            goodsList.add(goods);
//        }

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main1Activity.this, GoodsActivity.class);
                intent.putExtra("goods", goodsList.get(position));
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.tabbar_home)
    public void onTabbarHomeClicked() {
        Intent intent = new Intent(Main1Activity.this, Main1Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_my)
    public void onTabbarMyClicked() {
        Intent intent = new Intent(Main1Activity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_select)
    public void onTabbarDigClicked() {
        Intent intent = new Intent(Main1Activity.this, Main3Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tabbar_sell)
    public void onTabbarSellClicked() {
        Intent intent = new Intent(Main1Activity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.bt_main_search)
    public void onViewClicked() {
        String content = etMainSearch.getText().toString().trim();
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Goodssearch?content=" + content + "&type=" + types[spIndex];
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
                Toast.makeText(Main1Activity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
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
                            GoodsAdapter goodsAdapter = new GoodsAdapter(Main1Activity.this, goodsList);
                            lvMain.setAdapter(goodsAdapter);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(Main1Activity.this, "无数据", Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goodsList.clear();
                            GoodsAdapter goodsAdapter = new GoodsAdapter(Main1Activity.this, goodsList);
                            lvMain.setAdapter(goodsAdapter);
                        }
                    });

                    Log.i(TAG, "发布失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });
    }

    @OnClick(R.id.bt_mian1_tuijian)
    public void onClick() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "GoodsHot?";
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
                Toast.makeText(Main1Activity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
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
                            GoodsAdapter goodsAdapter = new GoodsAdapter(Main1Activity.this, goodsList);
                            lvMain.setAdapter(goodsAdapter);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(Main1Activity.this, "无数据", Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goodsList.clear();
                            GoodsAdapter goodsAdapter = new GoodsAdapter(Main1Activity.this, goodsList);
                            lvMain.setAdapter(goodsAdapter);
                        }
                    });

                    Log.i(TAG, "发布失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });
    }
}
