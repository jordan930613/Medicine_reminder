package com.example.medicine_reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder> {

    private Context mContext;
    private List<Integer> mSys;
    private List<Integer> mDia;
    private List<Integer> mPul;
    private List<Integer> mBloodsugar;
    private List<Integer> mWeight;
    private List<String> mDate;

    private void setContext(Context context){
        this.mContext = context;
    }
    HealthAdapter(List<Integer> sys, List<Integer> dia, List<Integer> pul, List<Integer> bloodsugar, List<Integer> weight, List<String> date){
        mSys = sys;
        mDia = dia;
        mPul = pul;
        mBloodsugar = bloodsugar;
        mWeight = weight;
        mDate = date;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView item_Sys, item_Dia, item_Pul, item_sugar, item_weight, item_date;
        private ImageView img;

        ViewHolder(final View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.health_img);
            item_Sys = itemView.findViewById(R.id.list_sys);
            item_Dia = itemView.findViewById(R.id.list_dia);
            item_Pul = itemView.findViewById(R.id.list_date);
            item_sugar = itemView.findViewById(R.id.list_sugar);
            item_weight = itemView.findViewById(R.id.list_weight);
            item_date = itemView.findViewById(R.id.list_date);

            setContext(itemView.getContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Click"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(mContext,HealthIntent.class);
                    bundle.putInt("ID", getAdapterPosition());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public HealthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content_health, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthAdapter.ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
        holder.item_Sys.setText("收縮壓 | " + mSys.get(position) + " mmHg");
        holder.item_Dia.setText("舒張壓 | " + mDia.get(position) + " mmHg");
        holder.item_Pul.setText("心率 | " + mPul.get(position) + " 次/分");
        holder.item_sugar.setText("血糖 | " + mBloodsugar.get(position) + " mg/dL");
        holder.item_weight.setText("體重 | " + mWeight.get(position) + " kg");
        holder.item_date.setText(mDate.get(position));
//        holder.img.setImageResource();
    }
    // 新增項目
    public void addItem(int sys, int dia, int pul, int bloodsugar, int weight, String date) {
        mSys.add(0, sys);
        mDia.add(0, dia);
        mPul.add(0, pul);
        mBloodsugar.add(0, bloodsugar);
        mWeight.add(0, weight);
        mDate.add(0, date);

        notifyItemInserted(0);
    }

    // 刪除項目
    public void removeItem(int position){
        Log.i("Remove ", "removeItem at "+ position);
        mSys.remove(position);
        mDia.remove(position);
        mPul.remove(position);
        mBloodsugar.remove(position);
        mWeight.remove(position);
        mDate.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mSys.size();
    }

}
