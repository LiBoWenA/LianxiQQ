package com.example.lianxiweektwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class SysActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView xingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys);
        //获取资源ID
        xingView = findViewById(R.id.zxing);
        xingView.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        xingView.startCamera();
        xingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xingView.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(SysActivity.this,result.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
