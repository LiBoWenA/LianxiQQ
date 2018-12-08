package com.example.lianxiweektwo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lianxiweektwo.R;
import com.example.lianxiweektwo.SucessActivity;
import com.example.lianxiweektwo.SysActivity;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class MyFragment extends Fragment {

    private Button button;
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.myfragment,container,false);
        //获取资源ID
        button = v.findViewById(R.id.sys);
        imageView = v.findViewById(R.id.imageView);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //点击按钮进行跳转到扫一扫
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SysActivity.class);
                startActivity(intent);
            }
        });
        creatQR();
    }

    //生成二维码
    private void creatQR(){
        String name = ((SucessActivity)getActivity()).getName();
        QRTask qrTask = new QRTask(getActivity(),imageView,name);
        qrTask.execute(name);

    }

    static class QRTask extends AsyncTask<String,Void,Bitmap>{

        private WeakReference<Context> mcontext;
        private WeakReference<ImageView> mimageview;

        public QRTask(Context context,ImageView imageview,String name) {
            mcontext = new WeakReference<>(context);
            mimageview = new WeakReference<>(imageview);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }
            int size = mcontext.get().getResources().getDimensionPixelOffset(R.dimen.vh);
            return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                mimageview.get().setImageBitmap(bitmap);
            }else{
                Toast.makeText(mcontext.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
