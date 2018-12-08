package com.example.lianxiweektwo.baseadapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.BaseAdapter;

import com.example.lianxiweektwo.fragment.HomeFragment;
import com.example.lianxiweektwo.fragment.MyFragment;

public class UserAdapter extends FragmentPagerAdapter {

    private String[] name = new String[]{"首页","我的"};

    public UserAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new HomeFragment();
                default:
                    return new MyFragment();
        }
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
