package com.example.lianxiweektwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lianxiweektwo.bean.NewsBean;
import com.example.lianxiweektwo.bean.UserBean;
import com.example.lianxiweektwo.persenter.IPersenter;
import com.example.lianxiweektwo.persenter.IPersenterImpl;
import com.example.lianxiweektwo.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener
{

    private EditText ed_name,ed_pass;
    private CheckBox ck_pass,ck_login;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private IPersenterImpl iPersenter;
    private String path = "http://www.zhaoapi.cn/user/login?mobile=%s&Password=%s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        iPersenter = new IPersenterImpl(this);
        init();
    }

    private void init() {
        //获取资源ID
        ed_name = findViewById(R.id.et_name);
        ed_pass = findViewById(R.id.et_pass);
        ck_pass = findViewById(R.id.ck_pass);
        ck_login = findViewById(R.id.ck_login);
        findViewById(R.id.b_login).setOnClickListener(this);
        findViewById(R.id.b_zc).setOnClickListener(this);
        findViewById(R.id.b_fx).setOnClickListener(this);

        boolean c_pass = sharedPreferences.getBoolean("c_pass",false);
        if (c_pass){
            String name = sharedPreferences.getString("name", null);
            String pass = sharedPreferences.getString("pass", null);
            ed_name.setText(name);
            ed_pass.setText(pass);
            ck_pass.setChecked(true);
        }

        boolean c_login = sharedPreferences.getBoolean("c_login",false);
        if(c_login){
            String name = ed_name.getText().toString();
            String pass = ed_pass.getText().toString();
            ck_pass.setChecked(true);
            iPersenter.showRequestData(String.format(path,name,pass),UserBean.class);
        }
        ck_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ck_pass.setChecked(true);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.b_login:
                //点击登录获取输入框的值
                String name = ed_name.getText().toString();
                String pass = ed_pass.getText().toString();
                if (ck_pass.isChecked()){
                    editor.putString("name",name);
                    editor.putString("pass",pass);
                    editor.putBoolean("c_pass",true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }

                if (ck_login.isChecked()){
                    editor.putBoolean("c_login",true);
                    editor.commit();
                }

                iPersenter.showRequestData(String.format(path,name,pass),UserBean.class);
                break;
            case R.id.b_zc:
                Intent intents = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intents);
                finish();
                break;
            case R.id.b_fx:
                UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        String name  = map.get("screen_name");
                        //获取头像
                        String img  = map.get("profile_image_url");
                        Intent intent = new Intent(MainActivity.this,SucessActivity.class);
                        intent.putExtra("name",name);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
                default:
                    break;

        }
    }

    @Override
    public void startRequestData(Object data) {
        UserBean bean = (UserBean) data;
        if (bean.getCode().equals("0")){
            Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,SucessActivity.class);
            intent.putExtra("name",ed_name.getText().toString());
            startActivity(intent);
        }else{
            Toast.makeText(this,bean.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.onDestory();
    }
}
