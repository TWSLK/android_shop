package com.bear.ma079;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsActivity extends AppCompatActivity {


    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_description)
    TextView tvGoodsDescription;
    @BindView(R.id.tv_goods_address)
    TextView tvGoodsAddress;
    @BindView(R.id.et_goods_buy)
    Button etGoodsBuy;
    @BindView(R.id.bt_goods_select)
    Button btGoodsSelect;
    @BindView(R.id.et_goods_msg)
    EditText etGoodsMsg;
    @BindView(R.id.bt_goods_msg)
    Button btGoodsMsg;
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
    @BindView(R.id.tv_goods_state)
    TextView tvGoodsState;
    private String TAG = "GoodsActivity---------";
    private String ajax;
    private User user;
    private Goods goods;
    private String uid;
    private String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        SharedPreferences sharedPreferences = getSharedPreferences("bear", Context.MODE_PRIVATE);
        uid = sharedPreferences.getInt("userid", 0) + "";
        uname = sharedPreferences.getString("nickname", "");
        goods = (Goods) getIntent().getSerializableExtra("goods");
        tvGoodsName.setText("名称：" + goods.getName()+"\t"+goods.getType());
        tvGoodsPrice.setText("价格：￥" + goods.getPrice());
        tvGoodsState.setText("状态：" + goods.getState());
        tvGoodsDescription.setText("介绍：" + goods.getDescription());
        tvGoodsAddress.setText("地址：" + goods.getAddress());
        Glide.with(this).load(new Url().urlUploads+goods.getImage()).into(ivGoods);

        if (goods.getState().equals("出售中")) {
            llGoodsBuy.setVisibility(View.VISIBLE);
            llGoodsConform.setVisibility(View.GONE);
            llGoodsGet.setVisibility(View.GONE);
        } else if (goods.getState().equals("购买")) {
            llGoodsBuy.setVisibility(View.GONE);
            llGoodsConform.setVisibility(View.VISIBLE);
            llGoodsGet.setVisibility(View.GONE);
        } else if (goods.getState().equals("确认购买")) {
            llGoodsBuy.setVisibility(View.GONE);
            llGoodsConform.setVisibility(View.GONE);
            llGoodsGet.setVisibility(View.VISIBLE);
        } else {
            llGoodsBuy.setVisibility(View.GONE);
            llGoodsConform.setVisibility(View.GONE);
            llGoodsGet.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.et_goods_buy)
    public void onEtGoodsBuyClicked() {
        llGoodsBuy.setVisibility(View.GONE);
        llGoodsConform.setVisibility(View.VISIBLE);
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "Buy?id=" + goods.getId();
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
                Toast.makeText(GoodsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @OnClick(R.id.bt_goods_select)
    public void onBtGoodsSelectClicked() {

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "Select?gid=" + goods.getId() + "&uid=" + uid + "&uname=" + uname;
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
                Toast.makeText(GoodsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @OnClick(R.id.bt_goods_msg)
    public void onBtGoodsMsgClicked() {
        String msg = etGoodsMsg.getText().toString();
        if (msg.length() <= 0) {
            Toast.makeText(GoodsActivity.this, "输入留言", Toast.LENGTH_LONG).show();
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
                Toast.makeText(GoodsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @OnClick(R.id.et_goods_conform)
    public void onEtGoodsConformClicked() {
        llGoodsConform.setVisibility(View.GONE);
        llGoodsGet.setVisibility(View.VISIBLE);
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "Confirm?id=" + goods.getId() + "&bid=" + uid + "&bname=" + uname;
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
                Toast.makeText(GoodsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @OnClick(R.id.bt_goods_cancle)
    public void onBtGoodsCancleClicked() {
        llGoodsBuy.setVisibility(View.VISIBLE);
        llGoodsConform.setVisibility(View.GONE);
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "Cancel?id=" + goods.getId();
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
                Toast.makeText(GoodsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @OnClick(R.id.et_goods_get)
    public void onEtGoodsGetClicked() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "Gethuo?id=" + goods.getId() + "&bid=" + uid + "&bname=" + uname;
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
                Toast.makeText(GoodsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "收到: " + ajax);
                Gson gson = new Gson();
                Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if (ajaxe.getStatus() > 0) {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(GoodsActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }
}
