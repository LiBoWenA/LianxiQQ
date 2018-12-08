package com.example.lianxiweektwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lianxiweektwo.bean.ZcBean;
import com.example.lianxiweektwo.persenter.IPersenterImpl;
import com.example.lianxiweektwo.view.IView;

public class LoginActivity extends AppCompatActivity implements IView {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText e_name,e_pass;
    private Button button;
    private IPersenterImpl iPersenter;
    private String path = "http://120.27.23.105/user/reg?mobile=%s&Password=%s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取资源ID
        iPersenter = new IPersenterImpl(this);
        e_name = findViewById(R.id.et_name);
        e_pass = findViewById(R.id.et_pass);
        findViewById(R.id.btn_zc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e_name != null){
                    if (e_pass != null){
                        String name = e_name.getText().toString();
                        String pass = e_pass.getText().toString();
                       iPersenter.showRequestData(String.format(path,name,pass),ZcBean.class);

                    }
                }
            }
        });
    }

    @Override
    public void startRequestData(Object data) {

        ZcBean zcBean = (ZcBean) data;

        if (zcBean.getMsg().equals("注册成功")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
        }
    }
}
