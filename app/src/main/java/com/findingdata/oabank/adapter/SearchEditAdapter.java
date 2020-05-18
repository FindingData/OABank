package com.findingdata.oabank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.SearchEntity;
import com.findingdata.oabank.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchEditAdapter extends RecyclerView.Adapter<SearchEditAdapter.ViewHolder > {
    private List<SearchEntity> mlist ;
    private int mposition = -1;

    public interface onItemClickListener{
        void onClick(int position);
    }
    private SearchEditAdapter.onItemClickListener listener;

    public void setOnItemClickListener(SearchEditAdapter.onItemClickListener listener) {
        this.listener = listener;
    }

    public SearchEditAdapter(List<SearchEntity> list){
        this.mlist = list;
    }

    @NonNull
    @Override
    public SearchEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_entity,parent,false);
        final SearchEditAdapter.ViewHolder holder = new SearchEditAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEditAdapter.ViewHolder holder, final int position) {
        SearchEntity entity = mlist.get(position);
        if (entity.getType().equals("1")){
            holder.entity1.setText(entity.getCONSTRUCTION_NAME());
            holder.entity2.setText(Utils.dealwithNull(entity.getPROMOTION_NAME1()));
        }else if (entity.getType().equals("2")){
            holder.entity1.setText(entity.getBUILDING_NAME());
            holder.entity2.setText("");
        }else if (entity.getType().equals("3")){
            holder.entity1.setText(entity.getHOUSE_NAME());
            holder.entity2.setText("");
        }

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
        TextView entity1;
        TextView entity2;


        public ViewHolder(View itemView) {
            super(itemView);
            entity1 =  (TextView) itemView.findViewById(R.id.entity1);
            entity2 = (TextView)itemView.findViewById(R.id.entity2);

        }
    }
}
