package com.example.lianxiweektwo.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lianxiweektwo.R;
import com.example.lianxiweektwo.bean.NewsBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<NewsBean.DataBean> list;

    public ListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<NewsBean.DataBean> lists){
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void addData(List<NewsBean.DataBean> lists){
        list.addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NewsBean.DataBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(getItem(position).getTitle());
        return convertView;
    }

    class ViewHolder{
        TextView text;
    }
}
