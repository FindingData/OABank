package com.findingdata.oabank.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.ProjectBusinessEntity;
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
        List<ProjectBusinessEntity> business_list = item.getBUSINESS_LIST();
        String aplication_type = "";
        for (int i = 0; i<business_list.size();i++){
            if (business_list.get(i).getBUSINESS_TYPE() == 40004002){
                if (!aplication_type.contains("预"))
                    aplication_type += "预 ";
            }else {
                if (!aplication_type.contains("报"))
                    aplication_type += "报 ";
            }
        }
        helper.setText(R.id.project_list_type,aplication_type);
        helper.setText(R.id.project_list_name,Utils.dealwithNull(item.getPROJECT_NAME()));
        if (item.getPROJECT_STATUS() == 40001006){
            helper.setText(R.id.project_list_client,Utils.dealwithNull(item.getSHORT_NAME()));
            helper.setText(R.id.project_list_price,Utils.dealwithNull(item.getLOAN_AMOUNT()+"万"));
        }else{
            helper.setText(R.id.project_list_client,Utils.dealwithNull(business_list.get(0).getCOMMISSIONED_NAME()));
            if (business_list.get(0).getTOTAL_PRICE()!=0)
                helper.setText(R.id.project_list_price,Utils.dealwithNull(business_list.get(0).getTOTAL_PRICE()+"万"));
        }


        helper.setText(R.id.project_list_loan,Utils.dealwithNull(item.getLOAN_TYPE_CHS()));
        helper.setText(R.id.project_list_contact,"    "+Utils.dealwithNull(item.getBORROWER()));
        if (item.getCONFIRM_TIME().length()>11){
            helper.setText(R.id.project_list_time, Utils.transformIOSTime(item.getCONFIRM_TIME().substring(0,11)));
        }else{
            helper.setText(R.id.project_list_time, Utils.transformIOSTime(item.getCONFIRM_TIME()));
        }

    }
}
