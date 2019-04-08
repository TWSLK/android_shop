package com.bear.ma079;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.bt_login_login)
    Button btLoginLogin;
    @BindView(R.id.bt_login_regist)
    Button btLoginRegist;
//    @BindView(R.id.bt_login_forget)
//    Button btLoginForget;
    private String TAG = "LoginActivity---------";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

    }

    @OnClick(R.id.bt_login_login)
    public void onBtLoginLoginClicked() {
        //------------------------------------------------
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

//        OkHttpClient okHttpClient = new OkHttpClient();
//        //String url=Url.url+"username=telangpu&password=1111";
//        String url = new Url().url + "/Api/Index/Login?username=" + etLoginUsername.getText().toString() + "&password=" + etLoginPassword.getText().toString();
//        final Request request = new Request.Builder()
//                .url(url)
//                .get()//默认就是GET请求，可以不写
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i(TAG, "onFailure: " + e.getMessage() + e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String ajax = response.body().string();
//                Log.i(TAG, "onResponse: " + ajax);
//                Gson gson = new Gson();
//                User user = gson.fromJson(ajax, User.class);
//                if (user.getId() > 0) {
//                    SharedPreferences sharedPreferences = getSharedPreferences("taojinzhe", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("username", user.getUsername());
//                    editor.putString("nickname", user.getNickname());
//                    editor.putInt("userid", user.getId());
//                    editor.putString("phone", user.getPhone());
//                    editor.putString("password", user.getPassword());
//                    editor.putString("realname", user.getRealname());
//                    editor.putString("zhifubao", user.getZhifubao());
//                    editor.commit();
//                    Looper.prepare();
//                    Toast.makeText(LoginActivity.this, "登录成功" + user.toString(), Toast.LENGTH_LONG).show();
//                    handler.sendEmptyMessage(1);
//                    Looper.loop();
//                } else {
//                    Looper.prepare();
//                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
//                    Looper.loop();
//                }
////                Log.d(TAG, "user: " + user.toString());
//            }
//        });


    }

    @OnClick(R.id.bt_login_regist)
    public void onBtLoginRegistClicked() {
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent);
    }


}
