package com.example.medicine_reminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Member;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {


    private Context mContext;
    private List<Member> memberList;

    public RecycleAdapter(Context context, List<Member> memberList){
        this.mContext = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_content_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {

        final Member member = memberList.get(position);
        holder.imageId.setImageResource(member.getImage());
        holder.textId.setText(String.valueOf(member.getId()));
        holder.textName.setText(member.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(member.getImage());
                Toast toast = new Toast(mContext);
                toast.setView(imageView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageId;
        TextView textId, textName;
        ViewHolder(View itemView) {
            super(itemView);
            imageId = (ImageView) itemView.findViewById(R.id.mainpage_content_img);
            textId = (TextView) itemView.findViewById(R.id.mainpage_content_tv1);
            textName = (TextView) itemView.findViewById(R.id.mainpage_content_tv2);
        }
    }

}
