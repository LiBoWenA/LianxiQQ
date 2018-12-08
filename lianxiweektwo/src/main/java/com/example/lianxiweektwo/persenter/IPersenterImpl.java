package com.example.lianxiweektwo.persenter;

import com.example.lianxiweektwo.Moudle.IMoudle;
import com.example.lianxiweektwo.Moudle.IMoudleImpl;
import com.example.lianxiweektwo.callback.MyCallBack;
import com.example.lianxiweektwo.view.IView;

public class IPersenterImpl implements IPersenter {
    private IView iView;
    private IMoudle iMoudle;

    public IPersenterImpl(IView iView) {
        this.iView = iView;
        iMoudle = new IMoudleImpl();
    }

    @Override
    public void showRequestData(String path, Class clazz) {
        iMoudle.requestData(path, clazz, new MyCallBack() {
            @Override
            public void secess(Object data) {
                iView.startRequestData(data);
            }
        });
    }

    public void onDestory(){
        if (iView != null){
            iView = null;
        }
        if (iMoudle != null){
            iMoudle = null;
        }
    }

}
