package com.canbot.u05.sdk.clientdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.canbot.u05.sdk.clientdemo.bean.IndustryDatas;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.uurobot.sdkclientdemo.R;

import java.util.List;

/**
 * 首页适配器
 */

public class MainAdapter extends BaseAdapter {

        List<IndustryDatas>  stringList;

        LayoutInflater inflater;

        Context mContext;

        public MainAdapter(Context mContext, List<IndustryDatas> stringList) {
                this.stringList = stringList;
                this.mContext = mContext;
                inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
                return stringList == null ? 0 : stringList.size();
        }

        @Override
        public Object getItem(int i) {
                return stringList.get(i);
        }

        @Override
        public long getItemId(int i) {
                return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
                ViewHolder holder;
                if (convertView == null) {
                        holder = new ViewHolder();
                        convertView = inflater.inflate(R.layout.main_item, viewGroup, false);
                        holder.iconName = (Button) convertView.findViewById(R.id.text_main);
                        convertView.setTag(holder);
                }
                else {
                        holder = (ViewHolder) convertView.getTag();
                }
                holder.iconName.setText(stringList.get(i).getTaskName());
                return convertView;
        }


        protected class ViewHolder {

                public Button iconName;
        }

}
