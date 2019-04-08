package com.bear.ma079;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class MyActivity extends AppCompatActivity {



    @BindView(R.id.tabbar_home)
    RelativeLayout tabbarHome;
    @BindView(R.id.tabbar_select)
    RelativeLayout tabbarDig;
    @BindView(R.id.tabbar_my)
    RelativeLayout tabbarMy;
    @BindView(R.id.tabbar_sell)
    RelativeLayout tabbarSell;
    private String TAG = "MyActivity---------";
    private String ajax;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.tabbar_home)
    public void onTabbarHomeClicked() {
        Intent intent = new Intent(MyActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_my)
    public void onTabbarMyClicked() {
        Intent intent = new Intent(MyActivity.this, MyActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_select)
    public void onTabbarDigClicked() {
        Intent intent = new Intent(MyActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tabbar_sell)
    public void onTabbarSellClicked() {
        Intent intent = new Intent(MyActivity.this, SellActivity.class);
        startActivity(intent);
        finish();
    }



}
