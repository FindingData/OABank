package com.findingdata.oabank.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.FilterItemEntity;
import com.findingdata.oabank.entity.FilterTypeEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息列表适配器
 */
public class FilterListAdapter extends BaseQuickAdapter<FilterItemEntity, BaseViewHolder> {
    private Context context;
    public FilterListAdapter(Context context,int layoutResId, @Nullable List<FilterItemEntity> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FilterItemEntity item) {
        helper.setText(R.id.filter_list_btn,item.getValue());
        if(item.isCheck()){
            helper.setBackgroundRes(R.id.filter_list_btn,R.drawable.btn_box_primary_solid_body_shape);
            helper.setTextColor(R.id.filter_list_btn, context.getResources().getColor(R.color.primary));
        }else{
            helper.setBackgroundRes(R.id.filter_list_btn,R.drawable.bg_solid_body_shape);
            helper.setTextColor(R.id.filter_list_btn,context.getResources().getColor(R.color.text_primary));
        }

    }
}
