package com.bear.ma079;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {


    @BindView(R.id.tabbar_home)
    RelativeLayout tabbarHome;
    @BindView(R.id.tabbar_select)
    RelativeLayout tabbarDig;
    @BindView(R.id.tabbar_my)
    RelativeLayout tabbarMy;
    @BindView(R.id.tabbar_sell)
    RelativeLayout tabbarSell;
    @BindView(R.id.tv_my_buy)
    TextView tvMyBuy;
    @BindView(R.id.tv_my_sell)
    TextView tvMySell;
    @BindView(R.id.bt_logout)
    Button btLogout;
    private String TAG = "Main2Activity---------";
    private String ajax;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.tabbar_home)
    public void onTabbarHomeClicked() {
        Intent intent = new Intent(Main2Activity.this, Main1Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_my)
    public void onTabbarMyClicked() {
        Intent intent = new Intent(Main2Activity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_select)
    public void onTabbarDigClicked() {
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tabbar_sell)
    public void onTabbarSellClicked() {
        Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tv_my_buy)
    public void onTvMyBuyClicked() {
        Intent intent = new Intent(Main2Activity.this, OrderActivity.class);
        intent.putExtra("fun","buy");
        startActivity(intent);
    }

    @OnClick(R.id.tv_my_sell)
    public void onTvMySellClicked() {
        Intent intent = new Intent(Main2Activity.this, OrderActivity.class);
        intent.putExtra("fun","sell");
        startActivity(intent);
    }

    @OnClick(R.id.bt_logout)
    public void onBtLogoutClicked() {
        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
