package com.bear.ma079;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main4Activity extends AppCompatActivity {


    @BindView(R.id.tabbar_home)
    RelativeLayout tabbarHome;
    @BindView(R.id.tabbar_select)
    RelativeLayout tabbarDig;
    @BindView(R.id.tabbar_my)
    RelativeLayout tabbarMy;
    @BindView(R.id.tabbar_sell)
    RelativeLayout tabbarSell;
    @BindView(R.id.et_sell_name)
    EditText etSellName;
    @BindView(R.id.et_sell_price)
    EditText etSellPrice;
    @BindView(R.id.sp_sell_type)
    Spinner spSellType;
    @BindView(R.id.et_sell_address)
    EditText etSellAddress;
    @BindView(R.id.et_sell_descriptoin)
    EditText etSellDescriptoin;
    @BindView(R.id.bt_sell_add)
    Button btSellAdd;
    @BindView(R.id.bt_sell_pick1)
//    Button btSellPick1;
//    @BindView(R.id.bt_sell_pick2)
//    Button btSellPick2;
//    @BindView(R.id.bt_sell_pick3)
            Button btSellPick3;
    @BindView(R.id.iv_sell)
    ImageView ivSell;
    private String TAG = "Main4Activity---------";
    private String ajax;
    private User user;
    private int spIndex = 0;
    private String[] types;
    //动态申请权限
    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int REQUEST_PICK_IMAGE = 11101;
    private String realPathFromUri;

    private int updateSuccess=0;
    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        types = new String[]{"生活用品", "电子用品", "电脑配件", "图书"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        spSellType.setAdapter(adapter);
        spSellType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.tabbar_home)
    public void onTabbarHomeClicked() {
        Intent intent = new Intent(Main4Activity.this, Main1Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_my)
    public void onTabbarMyClicked() {
        Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tabbar_select)
    public void onTabbarDigClicked() {
        Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.tabbar_sell)
    public void onTabbarSellClicked() {
        Intent intent = new Intent(Main4Activity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.bt_sell_add)
    public void onViewClicked() {
        String name = etSellName.getText().toString();
        String price = etSellPrice.getText().toString();
        String description = etSellDescriptoin.getText().toString();
        String address = etSellAddress.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("bear", Context.MODE_PRIVATE);
        String sid = sharedPreferences.getInt("userid", 0) + "";
        String sname = sharedPreferences.getString("nickname", "");
        if (name.length() <= 0 || price.length() <= 0 || description.length() <= 0 || address.length() <= 0) {
            Toast.makeText(Main4Activity.this, "各项输入不可为空", Toast.LENGTH_LONG).show();
            return;
        }if (updateSuccess<=0) {
            Toast.makeText(Main4Activity.this, "请等待图片上传成功", Toast.LENGTH_LONG).show();
            return;
        }
        btSellAdd.setVisibility(View.INVISIBLE);
        Goods goods = new Goods(name, price, description, address);
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url + "Goodsadd?name=" + name
                + "&price=" + price
                + "&description=" + description
                + "&address=" + address
                + "&sid=" + sid
                + "&sname=" + sname
                + "&image=" + image
                + "&type=" + types[spIndex];
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
                Toast.makeText(Main4Activity.this, "连接失败" + e.getMessage() + e.toString(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ajax = response.body().string();
                Log.i(TAG, "onResponse: " + ajax);
                Gson gson = new Gson();
                Looper.prepare();
                try {
                    Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                    if (ajaxe.getStatus() > 0) {
                        Toast.makeText(Main4Activity.this, "发布成功", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "发布成功: " + ajax);
                    } else {
                        Toast.makeText(Main4Activity.this, "发布失败", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "发布失败: " + ajax);
                    }
                } catch (Exception e) {
                    Toast.makeText(Main4Activity.this, "发布失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "发布失败" + e.getMessage() + ajax);
                }
                Looper.loop();
            }
        });

    }

    @OnClick(R.id.bt_sell_pick1)
    public void onBtSellPick1Clicked() {
        ActivityCompat.requestPermissions(Main4Activity.this, mPermissionList, 100);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    //获取相册返回的Uri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                        upImage();
                        Log.i(TAG,"图片路径"+realPathFromUri);
                        //Toast.makeText(this, "图片路径"+realPathFromUri, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }

    private void upImage() {
        String url = new Url().url +                "update";
        Log.i(TAG, "url: " + url);
        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(realPathFromUri);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "HeadPortrait.jpg",
                        RequestBody.create(MediaType.parse("image/png"), file));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "上传失败: "+e.getMessage() );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main4Activity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.e(TAG, "成功"+response);
                String ajax = response.body().string();
                Log.i(TAG, "onResponse: " + ajax);
                Gson gson = new Gson();
                final Ajaxe ajaxe = gson.fromJson(ajax, Ajaxe.class);
                if(ajaxe.getStatus()>0){
                    updateSuccess=1;
                    image = ajaxe.getMsg();
                    Log.i(TAG, "服务器图片名称: " + image);
                }else{
                    Log.i(TAG, "上传失败: " + ajaxe.getMsg());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Main4Activity.this, ajaxe.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }


}
