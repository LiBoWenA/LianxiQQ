package com.example.lianxiweektwo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lianxiweektwo.baseadapter.UserAdapter;

public class SucessActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager pager;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        //获取资源ID
        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);

        pager.setAdapter(new UserAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
    }

    public String getName(){
        return name;
    }


}
