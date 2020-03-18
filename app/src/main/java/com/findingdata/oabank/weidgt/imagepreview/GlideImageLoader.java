package com.findingdata.oabank.weidgt.imagepreview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.findingdata.oabank.weidgt.imagepreview.loader.ImageLoader;

/**
 * Created by zengx on 2019/6/11.
 * Describe:
 */
public class GlideImageLoader implements ImageLoader {
    /**
     * 展示图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .into(imageView);
    }

    /**
     * 展示图片
     *
     * @param context
     * @param path
     * @param imageView
     * @param width
     * @param height
     * @param placeholder
     * @param strategy
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int width, int height, Drawable placeholder, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(width, height)
                .placeholder(placeholder)
                .diskCacheStrategy(strategy);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }

    /**
     * 展示图片
     *
     * @param context
     * @param path
     * @param imageView
     * @param placeholder
     * @param strategy
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView, Drawable placeholder, DiskCacheStrategy strategy) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .diskCacheStrategy(strategy);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }
}
