package com.bear.ma079;

import android.content.Intent;
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

public class RegistActivity extends AppCompatActivity {

    @BindView(R.id.et_regist_username)
    EditText etRegistUsername;
    @BindView(R.id.et_regist_password)
    EditText etRegistPassword;
    @BindView(R.id.et_regist_password2)
    EditText etRegistPassword2;
    @BindView(R.id.bt_regist_regist)
    Button btRegistRegist;
    @BindView(R.id.et_regist_nickname)
    EditText etRegistNickname;
    private String TAG = "RegistActivity------------";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    @OnClick(R.id.bt_regist_regist)
    public void onViewClicked() {
        String username = etRegistUsername.getText().toString().trim();
        String nickname = etRegistNickname.getText().toString().trim();
        String password = etRegistPassword.getText().toString().trim();
        String password2 = etRegistPassword2.getText().toString().trim();
        if (username.length() < 4 || password.length() < 4 || password2.length() < 4) {
            Toast.makeText(RegistActivity.this, "用户名、密码长度小于4！", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.equals(password2)) {
            Toast.makeText(RegistActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        } else {


            User user = new User(username, password,nickname);
            regist(user);

        }
    }

    private void regist(User user) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = new Url().url +
                "/Regist?username=" + user.getUsername() +
                "&password=" + user.getPassword()+
                "&nickname=" + user.getNickname();
        Log.i(TAG, "url: " + url);
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage() + e.toString());
                Looper.prepare();
                Toast.makeText(RegistActivity.this, "网络错误", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(RegistActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    //handler.sendEmptyMessage(1);
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(RegistActivity.this, ajaxe.getMsg(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }
}
