package com.example.chengjie.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import base.UserInfoJSON;
import util.HttpRequest;

public class LoginActivity extends Activity {
    private EditText userInfo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userInfo = (EditText) findViewById(R.id.userInfo);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = userInfo.getText().toString().trim().toLowerCase();
                String pwd = password.getText().toString();
                if (info.equals("") || pwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                } else {
                    login(info, pwd);
                }
            }
        });
    }

    private void login(final String userInfo, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://49.140.61.67:8080/JLUServer/UserSignIn";
                String content = "info=" + userInfo + "&passWord=" + password + "&opInfo.type=signIn&opInfo.opTime=2017-08-09 15:31:00&opInfo.opLoc=unkown&opInfo.opDev=xiao mi5&opInfo.note=null";
                final String result = HttpRequest.request(url, content);
                Gson gson = new Gson();
                UserInfoJSON infoJSON = gson.fromJson(result, UserInfoJSON.class);
                if (infoJSON.getCode() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登陆成功\n"+result, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误\n"+result, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
