package com.example.medicine_reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class MainPage_Adapter extends RecyclerView.Adapter<MainPage_Adapter.ViewHolder> {

    private Context mContext;
    private List<String> mTime;
    private List<String> mName;
    private List<Boolean> mEat;
    private List<Integer> mPass;
    private Boolean key = true;
    DBHelper mDBHelper;
    TimeDBHelper timeDBHelper;

    private void setContext(Context context){
        this.mContext = context;
    }

    MainPage_Adapter(){    }

    MainPage_Adapter(List<String> time, List<String> name, List<Integer> pass){
        mTime = time;
        mName = name;
        mPass = pass;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_image;
        private TextView item_tvTime, item_tvName;
        private View titleColor;

        ViewHolder(final View itemView) {
            super(itemView);
//            item_image = (ImageView) itemView.findViewById(R.id.mainpage_content_img);
            titleColor = itemView.findViewById(R.id.viewtitle);
            item_tvTime = (TextView) itemView.findViewById(R.id.mainpage_tv_time);
            item_tvName = (TextView) itemView.findViewById(R.id.mainpage_tv_name);
            setContext(itemView.getContext());

            mDBHelper = new DBHelper(mContext);
            timeDBHelper = new TimeDBHelper(mContext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openOptionDialog(mName.get(getAdapterPosition()), getAdapterPosition());
                }
            });

        }
    }

    @NonNull
    @Override
    public MainPage_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPage_Adapter.ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
        holder.item_tvTime.setText(mTime.get(position));
        holder.item_tvName.setText(mName.get(position));

//        Calendar compare_time = Calendar.getInstance();
//        int hour = compare_time.get(Calendar.HOUR_OF_DAY);
//        int min = compare_time.get(Calendar.MINUTE);
//
//        Cursor sort = timeDBHelper.sort();
//        sort.moveToFirst();
//
//        while (!sort.isAfterLast()) {
//            String splited[] = sort.getString(0).split(" : ");
//            String getid = sort.getString(1);
//            String gettime = sort.getString(0);
//
//            if (hour > Integer.parseInt(splited[0])) {
//                int getNameId = Integer.parseInt(getid);
//                int getTimeId = timeDBHelper.get_time_id(getNameId, gettime);
//                SQLiteDatabase db_time = timeDBHelper.getWritableDatabase();
//                ContentValues values_time = new ContentValues();
//                values_time.put("dead", 1);
//                System.out.println("dead dead");
//                db_time.update("time_table", values_time, "id_time = '" + getTimeId + "'", null);
//
//            } else if (hour == Integer.parseInt(splited[0]) && min >= Integer.parseInt(splited[1])) {
//                int getNameId = Integer.parseInt(getid);
//                int getTimeId = timeDBHelper.get_time_id(getNameId, gettime);
//                SQLiteDatabase db_time = timeDBHelper.getWritableDatabase();
//                ContentValues values_time = new ContentValues();
//                values_time.put("dead", 1);
//                db_time.update("time_table", values_time, "id_time = '" + getTimeId + "'", null);
//            }
//            sort.moveToNext();
//        }

        if(mPass.get(position) == 1){
            holder.titleColor.setBackgroundColor(Color.parseColor("#cc0000"));
            holder.item_tvTime.setTextColor(Color.parseColor("#cc0000"));
        }

    }
    // 新增項目
    public void addItem(String time, String name) {
        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
        mTime.add(0,time);
        mName.add(0,name);
        notifyItemInserted(0);
    }

    // 刪除項目
    public void removeItem(int position){
        Log.i("Remove ", "removeItem at "+ position);
        mTime.remove(position);
        mName.remove(position);
        //mPass.remove(position);

        notifyItemRemoved(position);
    }

    public String[] sendPosition(int count_Eat) {

        String getName;
        String getTime;

        System.out.println("which first = " + 4);

        if (mName.isEmpty())
            getName = "testing";
        else if (mName.size() > count_Eat)
            getName = mName.get(count_Eat);
        else
            getName = "testing";

        if (mTime.isEmpty())
            getTime = "11 : 10";
        else if (mTime.size() > count_Eat)
            getTime = mTime.get(count_Eat);
        else
            getTime = "11 : 10";

        String send[] = {getName, getTime};

        return send;
    }

    @Override
    public int getItemCount() {
        return mTime.size();
    }


    private void openOptionDialog(String message, final int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("確定吃藥了嗎？");
        dialog.setMessage("確定已經吃了 " + message + " 這包藥嗎？");

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = mName.get(position);
                String time = mTime.get(position);
                mDBHelper.decreaseCount(mDBHelper.get_name_id(name));
                int get_med_count = mDBHelper.get_med_count(name);

                if (get_med_count == 0) {
                    reminderDialog(name);
                }

                int getNameId = mDBHelper.get_name_id(name);
                int getTimeId = timeDBHelper.get_time_id(getNameId, time);

                SQLiteDatabase db_time = timeDBHelper.getWritableDatabase();
                ContentValues values_time = new ContentValues();
                values_time.put("eat", 1);
                db_time.update("time_table", values_time, "id_time = '" + getTimeId + "'", null);

                //mEat.set(position, true);
                //mPass.remove(position);
                removeItem(position);
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

    private void reminderDialog(final String message){
        Log.i("ssss", "reminderDialog: "+message);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("藥包沒有嘍！");
        dialog.setMessage("藥包 " + message + "已經沒有嘍！");

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //String name = mName.get(0);
                int get_name_id = mDBHelper.get_name_id(message);

                timeDBHelper.deletezerobag(get_name_id);
                mDBHelper.deletezerobag(mDBHelper.get_name_id(message));
            }
        });

        dialog.show();
    }

}
