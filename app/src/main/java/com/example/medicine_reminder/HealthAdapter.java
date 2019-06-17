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
//    private List<Integer> mSys;
//    private List<Integer> mDia;
//    private List<Integer> mPul;
//    private List<Integer> mBloodsugar;
//    private List<Integer> mWeight;
//    private List<String> mDate;
//    private List<Long> mID;
    private List<Item> mItem;

    private void setContext(Context context){
        this.mContext = context;
    }
    HealthAdapter(List<Item> items){
        mItem = items;
//        mDia = dia;
//        mPul = pul;
//        mBloodsugar = bloodsugar;
//        mWeight = weight;
//        mDate = date;
//        mID = id;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView item_Sys, item_Dia, item_Pul, item_blood_sugar, item_weight, item_date;
        private ImageView img;

        ViewHolder(final View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.health_img);
            item_Sys = itemView.findViewById(R.id.list_sys);
            item_Dia = itemView.findViewById(R.id.list_dia);
            item_Pul = itemView.findViewById(R.id.list_pul);
            item_blood_sugar = itemView.findViewById(R.id.list_sugar);
            item_weight = itemView.findViewById(R.id.list_weight);
            item_date = itemView.findViewById(R.id.list_date);

            setContext(itemView.getContext());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Click"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(mContext,HealthIntent.class);
                    bundle.putLong("ID", mItem.get(getAdapterPosition()).getId());
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
        if(mItem.get(position).getSys()==0)
            holder.item_Sys.setText("收縮壓 | " + " 未測量");
        else
            holder.item_Sys.setText("收縮壓 | " + mItem.get(position).getSys() + " mmHg");
        if(mItem.get(position).getDia()==0)
            holder.item_Dia.setText("舒張壓 | " + " 未測量");
        else
            holder.item_Dia.setText("舒張壓 | " + mItem.get(position).getDia() + " mmHg");
        if (mItem.get(position).getPUL()==0)
            holder.item_Pul.setText("心率 | " + " 未測量");
        else
            holder.item_Pul.setText("心率 | " + mItem.get(position).getPUL() + " 次/分");
        if(mItem.get(position).getBloodsugar()==0)
            holder.item_blood_sugar.setText("血糖 | " + " 未測量");
        else
            holder.item_blood_sugar.setText("血糖 | " + mItem.get(position).getBloodsugar() + " mg/dL");

        if(mItem.get(position).getWeight()==0)
            holder.item_weight.setText("體重 | " + " 未測量");
        else
            holder.item_weight.setText("體重 | " + mItem.get(position).getWeight() + " kg");

        holder.item_date.setText("紀錄時間  "+ mItem.get(position).getDatetime());

        if(mItem.get(position).getSys()>140 || mItem.get(position).getDia()>90 || mItem.get(position).getPUL()>100 || mItem.get(position).getBloodsugar()>150){
            holder.img.setImageResource(R.drawable.ic_mood_bad);
        }else {
            holder.img.setImageResource(R.drawable.ic_mood);
        }

    }
    // 新增項目
    public void addItem(int sys, int dia, int pul, int bloodsugar, int weight, String date, long id) {
//        mSys.add(0, sys);
//        mDia.add(0, dia);
//        mPul.add(0, pul);
//        mBloodsugar.add(0, bloodsugar);
//        mWeight.add(0, weight);
//        mDate.add(0, date);
//        mID.add(0,id);

        notifyItemInserted(0);
    }

    // 刪除項目
    public void removeItem(int position){
        Log.i("Remove ", "removeItem at "+ position);
        mItem.remove(position);
//        mSys.remove(position);
//        mDia.remove(position);
//        mPul.remove(position);
//        mBloodsugar.remove(position);
//        mWeight.remove(position);
//        mDate.remove(position);
//        mID.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

}
