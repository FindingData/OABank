package com.findingdata.oabank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.CustomerEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder > {
    private List<CustomerEntity> mlist ;
    private int mposition = -1;

    public CustomerListAdapter(List<CustomerEntity> list){
        this.mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将点击的位置传出去
                mposition = holder.getAdapterPosition();
                //在点击监听里最好写入setVisibility(View.VISIBLE);这样可以避免效果会闪
                holder.img_select.setVisibility(View.VISIBLE);
                //刷新界面 notify 通知Data 数据set设置Changed变化
                //在这里运行notifyDataSetChanged 会导致下面的onBindViewHolder 重新加载一遍
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerEntity customerEntity = mlist.get(position);
        holder.tv_customer.setText(customerEntity.getCUSTOMER_NAME());
        holder.tv_process.setText("执行中项目："+customerEntity.getPROCESSING());
        holder.tv_total.setText("所有项目："+customerEntity.getTOTAL()+"");
        holder.tv_link_man.setText(customerEntity.getBUSINESS_CONTACT());
        holder.tv_link_telephone.setText(customerEntity.getPHONE()+"");

        if (position == mposition) {
            holder.img_select.setVisibility(View.VISIBLE);
        } else {
            holder.img_select.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_select;
        TextView tv_customer;
        TextView tv_process;
        TextView tv_total;
        TextView tv_link_man;
        TextView tv_link_telephone;
        LinearLayout mListLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            img_select =  (ImageView)itemView.findViewById(R.id.img_select);
            tv_customer = (TextView)itemView.findViewById(R.id.customer_name);
            tv_process  = (TextView) itemView.findViewById(R.id.process);
            tv_total = (TextView) itemView.findViewById(R.id.total);
            tv_link_man = (TextView) itemView.findViewById(R.id.link_man);
            tv_link_telephone = (TextView) itemView.findViewById(R.id.link_telephone);
            mListLayout = (LinearLayout) itemView.findViewById(R.id.item_linearlayou);

        }
    }
}
