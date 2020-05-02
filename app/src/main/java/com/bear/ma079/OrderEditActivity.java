package com.bear.ma079;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class OrderEditActivity extends AppCompatActivity {


    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    EditText tvGoodsPrice;
    @BindView(R.id.tv_goods_state)
    TextView tvGoodsState;
    @BindView(R.id.tv_goods_description)
    EditText tvGoodsDescription;
    @BindView(R.id.tv_goods_address)
    EditText tvGoodsAddress;
    @BindView(R.id.bt_order_edit_ok)
    Button btOrderEditOk;
    @BindView(R.id.bt_order_edit_delete)
    Button btOrderEditDelete;
    @BindView(R.id.ll_goods_buy)
    LinearLayout llGoodsBuy;
    @BindView(R.id.et_goods_conform)
    Button etGoodsConform;
    @BindView(R.id.bt_goods_cancle)
    Button btGoodsCancle;
    @BindView(R.id.ll_goods_conform)
    LinearLayout llGoodsConform;
    @BindView(R.id.et_goods_get)
    Button etGoodsGet;
    @BindView(R.id.ll_goods_get)
    LinearLayout llGoodsGet;
    @BindView(R.id.et_goods_msg)
    EditText etGoodsMsg;
    @BindView(R.id.bt_goods_msg)
    Button btGoodsMsg;
    @BindView(R.id.lv_order_edit)
    ListView lvOrderEdit;
    private String TAG = "OrderEditActivity---------";
    private String ajax;
    private User user;
    private Goods goods;
    private String uid;
    private String uname;
    private List<Msg> msgList = new ArrayList<>();
    private Ajaxe ajaxe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        SharedPreferences sharedPreferences = getSharedPreferences("bear", Context.MODE_PRIVATE);
        uid = sharedPreferences.getInt("userid", 0) + "";
        uname = sharedPreferences.getString("nickname", "");
        goods = (Goods) getIntent().getSerializableExtra("goods");
        tvGoodsName.setText("名称：" + goods.getName());
        tvGoodsPrice.setText("" + goods.getPrice());
        tvGoodsState.setText("状态：" + goods.getState());
        tvGoodsDescription.setText("" + goods.getDescription());
        tvGoodsAddress.setText("" + goods.getAddress());
        Glide.with(this).load(Url.url+"/imagedown?image=" + goods.getImage()).into(ivGoods);

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "msglist?gid="+goods.getId();
        Log.i(TAG,url);
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
                Toast.makeText(OrderEditActivity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到数据: " + ajax);
                Gson gson = new Gson();
                Looper.prepare();
                try {
                    msgList = gson.fromJson(ajax, new TypeToken<List<Msg>>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MsgAdapter goodsAdapter = new MsgAdapter(OrderEditActivity.this, msgList);
                            lvOrderEdit.setAdapter(goodsAdapter);
                        }
                    });
                } catch (Exception e) {
                    //Toast.makeText(OrderEditActivity.this, "获取失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });
    }


    @OnClick(R.id.bt_order_edit_ok)
    public void onBtOrderEditOkClicked() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Orderedit?id=" + goods.getId() +
                "&price=" + tvGoodsPrice.getText().toString() +
                "&description=" + tvGoodsDescription.getText().toString() +
                "&address=" + tvGoodsAddress.getText().toString();
        Log.i(TAG,url);
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
                Toast.makeText(OrderEditActivity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "onResponse: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(OrderEditActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    //handler.sendEmptyMessage(1);
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(OrderEditActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            }
        });
    }

    @OnClick(R.id.bt_order_edit_delete)
    public void onBtOrderEditDeleteClicked() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Orderdelete?id=" + goods.getId();
        Log.i(TAG,url);
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
                Toast.makeText(OrderEditActivity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "onResponse: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(OrderEditActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    //handler.sendEmptyMessage(1);
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(OrderEditActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            }
        });
    }

    @OnClick(R.id.bt_goods_msg)
    public void onViewClicked() {
        String msg = etGoodsMsg.getText().toString();
        if (msg.length() <= 0) {
            Toast.makeText(OrderEditActivity.this, "输入留言", Toast.LENGTH_LONG).show();
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "Msg?gid=" + goods.getId() + "&uid=" + uid + "&uname=" + uname + "&msg=" + msg;
        Log.i(TAG, "发送: " + url);
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "网络错误: " + e.getMessage() + e.toString());
                Looper.prepare();
                Toast.makeText(OrderEditActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderEditActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                            init();
                        }
                    });

                } else {
                    Looper.prepare();
                    Toast.makeText(OrderEditActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }
}
