package com.findingdata.oabank.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.ImagePreviewListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.ImageViewInfo;
import com.findingdata.oabank.utils.FilePathUtil;
import com.findingdata.oabank.utils.PermissionsUtils;
import com.findingdata.oabank.weidgt.imagepreview.PreviewBuilder;
import com.findingdata.oabank.weidgt.photopicker.PhotoPicker;
import com.zhihu.matisse.Matisse;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.findingdata.oabank.base.Config.PHOTO_DIR_NAME;
import static com.findingdata.oabank.base.Config.SD_APP_DIR_NAME;


@ContentView(R.layout.activity_property_attachment)
public class PropertyAttachmentActivity extends BaseActivity {
    private static final String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_CHOOSE=0x001;

    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.rlv_image)
    private RecyclerView rlv_image;

    List<ImageViewInfo> dataList=new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    private ImagePreviewListAdapter adapter;
    private boolean permissionFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("附件");

        PermissionsUtils.getInstance().checkPermissions(this, permission, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void success() {
                LogUtil.d("申请权限通过");
                permissionFlag=true;
            }

            @Override
            public void fail() {
                LogUtil.d("申请权限未通过");
                permissionFlag=false;
            }
        });

        dataList.add(new ImageViewInfo("plus"));
        rlv_image.setLayoutManager(mGridLayoutManager = new GridLayoutManager(this,3));
        adapter=new ImagePreviewListAdapter(this,dataList);
        rlv_image.setAdapter(adapter);
        rlv_image.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==dataList.size()-1){
                    if(permissionFlag){
                        PhotoPicker.pick(PropertyAttachmentActivity.this,10,true,REQUEST_CODE_CHOOSE);
                    }else{
                        showToast("相机或读写手机存储的权限被禁止！");
                    }
                }else{
                    List<ImageViewInfo> data=new ArrayList<>();
                    for (int i = 0; i < dataList.size()-1; i++) {
                        data.add(dataList.get(i));
                    }
                    computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
                    PreviewBuilder.from(PropertyAttachmentActivity.this)
                            .setImgs(data)
                            .setCurrentIndex(position)
                            .setSingleFling(true)
                            .setType(PreviewBuilder.IndicatorType.Number)
                            .start();
                }
            }
        });
    }

    //计算返回的边界
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < adapter.getItemCount(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView imageView = itemView.findViewById(R.id.iiv_item_image_preview);
                imageView.getGlobalVisibleRect(bounds);
            }
            adapter.getItem(i).setBounds(bounds);
        }
    }


    @Event({R.id.toolbar_btn_back})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHOOSE&&resultCode==RESULT_OK){
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            List<String> _List = new ArrayList<>();
            for (Uri _Uri : pathList)
            {
                String _Path = FilePathUtil.getPathByUri(this,_Uri);
                File _File = new File(_Path);
                LogUtil.d("压缩前图片大小->" + _File.length() / 1024 + "k");
                _List.add(_Path);
            }
            compress(_List);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    //压缩图片
    private void compress(List<String> list){
        String _Path = FilePathUtil.createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME);
        LogUtil.d("_Path->" + _Path);
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setTargetDir(_Path)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.d(" 压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.d(" 压缩成功后调用，返回压缩后的图片文件");
                        dataList.add(dataList.size()-1,new ImageViewInfo(file.getAbsolutePath()));
                        adapter.notifyDataSetChanged();
                        LogUtil.d("压缩后图片大小->" + file.length() / 1024 + "k");
                        LogUtil.d("getAbsolutePath->" + file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }).launch();
    }
}
