package com.findingdata.oabank.adapter;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.findingdata.oabank.R;
import com.findingdata.oabank.entity.ImageViewInfo;
import com.findingdata.oabank.weidgt.imagepreview.IconImageView;

import java.util.List;


/**
 * Created by zengx on 2019/6/11.
 * Describe:
 */
public class ImagePreviewListAdapter extends BaseQuickAdapter<ImageViewInfo, BaseViewHolder> {
    private Context context;
    public ImagePreviewListAdapter(Context context, List<ImageViewInfo> data) {
        super(R.layout.item_image_perview_list, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageViewInfo item) {
        IconImageView imageView = helper.getView(R.id.iiv_item_image_preview);
        imageView.setIsShowIcon(item.getVideoUrl() != null);
        if("plus".equals(item.getUrl())){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_add));
        }else{
            Glide.with(context)
                    .load(item.getUrl())
                    .thumbnail(0.7f)
                    .placeholder(R.drawable.xui_ic_default_img)
                    .into(imageView);
        }

    }
}
