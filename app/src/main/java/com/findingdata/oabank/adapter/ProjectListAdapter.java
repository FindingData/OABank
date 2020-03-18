package com.findingdata.oabank.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息列表适配器
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectEntity, BaseViewHolder> {
    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProjectEntity item) {
        helper.setText(R.id.project_list_name,item.getPROJECT_NAME());
        helper.setText(R.id.project_list_client,item.getCUSTOMER_NAME());
        helper.setText(R.id.project_list_price,item.getLOAN_AMOUNT()+"万");
        helper.setText(R.id.project_list_loan,item.getLOAN_TYPE_CHS());
        helper.setText(R.id.project_list_contact,item.getCONTACT_PERSON());
        helper.setText(R.id.project_list_time, Utils.transformIOSTime(item.getCONFIRM_TIME()));
    }
}
