package com.example.lianxiweektwo.Moudle;

import com.example.lianxiweektwo.callback.MyCallBack;

public interface IMoudle {
    void requestData(String path, Class clazz, MyCallBack myCallBack);
}
