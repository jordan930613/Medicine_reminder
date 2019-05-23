package com.example.medicine_reminder;


import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomizeAdapter extends BaseAdapter {
    String[] mWords;
    String[] mSubWords;
    int[] mIcon;

    public  CustomizeAdapter(String[] words, String[] subwords, int[] icons ){
        mWords = words;
        mSubWords = subwords;
        mIcon=icons;
    }

    @Override
    public int getCount() {
        return mWords.length;
    }

    @Override
    public Object getItem(int position) {
        return mWords[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content_main, null);
        }
        TextView mTitle =(TextView)convertView.findViewById(R.id.mainpage_content_tv1);
        TextView mContent = (TextView)convertView.findViewById(R.id.mainpage_content_tv2);
        ImageView mImg = (ImageView)convertView.findViewById(R.id.mainpage_content_img);

        String title_text = (String) getItem(position);
        String content_text = mSubWords[position];
        int resId = mIcon[position];

        mTitle.setText(title_text);
        mContent.setText(content_text);
        mImg.setImageResource(resId);

        return convertView;
    }
}
