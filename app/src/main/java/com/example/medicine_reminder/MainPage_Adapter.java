package com.example.medicine_reminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Member;
import java.util.List;

public class MainPage_Adapter extends RecyclerView.Adapter<MainPage_Adapter.ViewHolder> {


    private List<String> mTime;
    private List<String> mName;

    MainPage_Adapter(List<String> time, List<String> name){
        mTime = time;
        mName = name;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_image;
        private TextView item_tvTime, item_tvName;
        private Button item_btnDel;

        ViewHolder(View itemView) {
            super(itemView);
            item_image = (ImageView) itemView.findViewById(R.id.mainpage_content_img);
            item_tvTime = (TextView) itemView.findViewById(R.id.mainpage_tv_time);
            item_tvName = (TextView) itemView.findViewById(R.id.mainpage_tv_name);
            item_btnDel = (Button)itemView.findViewById(R.id.mainpage_btn_del) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Click"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                }
            });

            item_btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 移除項目，getAdapterPosition為點擊的項目位置
                    removeItem(getAdapterPosition());
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
    }
    // 新增項目
    public void addItem(String time, String name) {
        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
        mTime.add(3,time);
        mName.add(3,name);
        notifyItemInserted(3);
    }

    // 刪除項目
    public void removeItem(int position){
        mTime.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mTime.size();
    }

}
