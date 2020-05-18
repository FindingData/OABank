package com.findingdata.oabank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.AchievementEntity;
import com.findingdata.oabank.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder > {
    private List<AchievementEntity> mlist ;
    private int mposition = -1;

    public interface onItemClickListener{
        void onClick(int position);
    }
    private AchievementAdapter.onItemClickListener listener;

    public void setOnItemClickListener(AchievementAdapter.onItemClickListener listener) {
        this.listener = listener;
    }

    public AchievementAdapter(List<AchievementEntity> list){
        this.mlist = list;
    }

    @NonNull
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement,parent,false);
        final AchievementAdapter.ViewHolder holder = new AchievementAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementAdapter.ViewHolder holder, final int position) {
        AchievementEntity customerEntity = mlist.get(position);
        holder.tv_achievement_title.setText(customerEntity.getFILE_NAME());
        holder.tv_achievement_type.setText(customerEntity.getBUSINESS_TYPE_CHS());
        holder.tv_achievement_price.setText(customerEntity.getTOTAL_PRICE()+"万元");
        holder.tv_createtime.setText(Utils.transformIOSTime(customerEntity.getCOMPLETED_TIME()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_achievement_title;
        TextView tv_achievement_type;
        TextView tv_achievement_price;
        TextView tv_createtime;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_achievement_title =  (TextView) itemView.findViewById(R.id.achievement_title);
            tv_achievement_type = (TextView)itemView.findViewById(R.id.achievement_type);
            tv_achievement_price  = (TextView) itemView.findViewById(R.id.achievement_price);
            tv_createtime = (TextView) itemView.findViewById(R.id.create_time);

        }
    }
}
