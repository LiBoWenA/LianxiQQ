package com.example.lianxiweektwo.Moudle;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.lianxiweektwo.callback.MyCallBack;
import com.example.lianxiweektwo.httpnet.NetUtils;
import com.google.gson.Gson;

public class IMoudleImpl implements IMoudle {
    private MyCallBack myCallBack;
    @SuppressLint("StaticFieldLeak")
    @Override
    public void requestData(String path, final Class clazz, final MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                String json = NetUtils.getJson(strings[0]);
                return new Gson().fromJson(json,clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                myCallBack.secess(o);
            }
        }.execute(path);

    }
}
