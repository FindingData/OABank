package com.findingdata.oabank.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.NotifyEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息列表适配器
 */
public class NotifyListAdapter extends BaseQuickAdapter<NotifyEntity, BaseViewHolder> {
    public NotifyListAdapter(int layoutResId, @Nullable List<NotifyEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NotifyEntity item) {
        helper.setText(R.id.notify_list_name,item.getRFROM());
        helper.setText(R.id.notify_list_time,item.getCREATED_TIME());
        helper.setText(R.id.notify_list_content,item.getMESSAGE_CONTENT());
        if(item.getMESSAGE_STATUS()==0){
            helper.setVisible(R.id.notify_list_read, true);
        }else{
            helper.setVisible(R.id.notify_list_read, false);
        }
    }
}
