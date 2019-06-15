package com.example.medicine_reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class MainPage_Adapter extends RecyclerView.Adapter<MainPage_Adapter.ViewHolder> {

    private Context mContext;
    private List<String> mTime;
    private List<String> mName;
    private Boolean key = true;
    DBHelper mDBHelper;
    TimeDBHelper timeDBHelper;
    String getName;
    String getTime;

    private void setContext(Context context){
        this.mContext = context;
    }
    MainPage_Adapter(List<String> time, List<String> name){
        mTime = time;
        mName = name;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_image;
        private TextView item_tvTime, item_tvName;

        ViewHolder(final View itemView) {
            super(itemView);
//            item_image = (ImageView) itemView.findViewById(R.id.mainpage_content_img);
            item_tvTime = (TextView) itemView.findViewById(R.id.mainpage_tv_time);
            item_tvName = (TextView) itemView.findViewById(R.id.mainpage_tv_name);
            setContext(itemView.getContext());

            mDBHelper = new DBHelper(mContext);
            timeDBHelper = new TimeDBHelper(mContext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Click"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(mContext,MainPageIntent.class);
//
//                    mContext.startActivity(intent);
                    openOptionDialog(mName.get(0));
//                    int get_med_count = mDBHelper.get_med_count(getName);
//                    System.out.println("get_med_count = " + get_med_count);
                }
            });

//            item_btnDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(getAdapterPosition()==-1)
//                        return;
//                    // 移除項目，getAdapterPosition為點擊的項目位置
//                    removeItem(getAdapterPosition());
//                }
//            });
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(50);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };
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

        notifyItemRemoved(position);
    }

    public String[] sendPosition() {

        System.out.println("which first = " + 4);

        if (mName.isEmpty())
            getName = "testing";
        else
            getName = mName.get(0);

        if (mTime.isEmpty())
            getTime = "11 : 10";
        else
            getTime = mTime.get(0);

        String send[] = {getName, getTime};

        return send;
    }

    @Override
    public int getItemCount() {
        return mTime.size();
    }


    private void openOptionDialog(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("確定吃藥了嗎？");
        dialog.setMessage("確定已經吃了 " + message + " 這包藥嗎？");

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = mName.get(0);
                mDBHelper.decreaseCount(mDBHelper.get_name_id(name));
                int get_med_count = mDBHelper.get_med_count(name);
                System.out.println("get_med_count = " + get_med_count);
                if (get_med_count == 0) {
                    System.out.println("getname = " + name);
                    reminderDialog(name);
                }
                removeItem(0);
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
