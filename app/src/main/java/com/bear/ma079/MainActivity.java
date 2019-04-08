package com.bear.ma079;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


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
    private String TAG = "MainActivity---------";
    private String ajax;
    private List<Goods> goodsList =new ArrayList<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            Goods goods = new Goods("物品", i + "", "物美价廉", "校内");
            goodsList.add(goods);
        }
        GoodsAdapter goodsAdapter = new GoodsAdapter(this, goodsList);
        lvMain.setAdapter(goodsAdapter);

    }

    @OnClick(R.id.tabbar_home)
    public void onTabbarHomeClicked() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_my)
    public void onTabbarMyClicked() {
        Intent intent = new Intent(MainActivity.this, MyActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_select)
    public void onTabbarDigClicked() {
        Intent intent = new Intent(MainActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tabbar_sell)
    public void onTabbarSellClicked() {
        Intent intent = new Intent(MainActivity.this, SellActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.bt_main_search)
    public void onViewClicked() {
    }
}
