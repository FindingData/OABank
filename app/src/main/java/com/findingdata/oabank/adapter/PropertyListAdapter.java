package com.findingdata.oabank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.PropertyEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.ViewHolder > {
    private List<PropertyEntity> mlist ;
    private int mposition = -1;

    public PropertyListAdapter(List<PropertyEntity> list){
        this.mlist = list;
    }

    public interface onItemClickListener{
        void onClick(int position);
    }
    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public PropertyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property,parent,false);
        final PropertyListAdapter.ViewHolder holder = new PropertyListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyListAdapter.ViewHolder holder, final int position) {
        PropertyEntity propertyEntity = mlist.get(position);
        holder.tv_property_name.setText(propertyEntity.getPROPERTY_NAME());
        holder.tv_property_address.setText("地址："+propertyEntity.getADDRESS());
        holder.tv_pca_code.setText(propertyEntity.getPCA_CODE_CHS());
        holder.tv_property_type.setText(propertyEntity.getPROPERTY_TYPE_CHS());
        holder.tv_property_inspection.setText(propertyEntity.getINSPECTION_CONTACT());
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
        TextView tv_property_name;
        TextView tv_property_address;
        TextView tv_pca_code;
        TextView tv_property_type;
        TextView tv_property_inspection;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_property_name = (TextView)itemView.findViewById(R.id.property_name);
            tv_property_address  = (TextView) itemView.findViewById(R.id.property_address);
            tv_pca_code = (TextView) itemView.findViewById(R.id.PCA_CODE_CHS);
            tv_property_type = (TextView) itemView.findViewById(R.id.PROPERTY_TYPE_CHS);
            tv_property_inspection = (TextView) itemView.findViewById(R.id.INSPECTION_CONTACT);


        }
    }
}
