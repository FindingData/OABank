package com.findingdata.oabank.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.ImagePreviewListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.ImageViewInfo;
import com.findingdata.oabank.entity.ProjectActionEntity;
import com.findingdata.oabank.entity.ProjectCenterListType;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.entity.ProjectNoteEntity;
import com.findingdata.oabank.entity.PropertyEntity;
import com.findingdata.oabank.utils.FilePathUtil;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.PermissionsUtils;
import com.findingdata.oabank.utils.Utils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.NoUnderlineSpan;
import com.findingdata.oabank.weidgt.imagepreview.PreviewBuilder;
import com.findingdata.oabank.weidgt.photopicker.PhotoPicker;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.PHOTO_DIR_NAME;
import static com.findingdata.oabank.base.Config.SD_APP_DIR_NAME;

/**
 * Created by Loong on 2019/11/27.
 * Version: 1.0
 * Describe: 项目详情Activity
 */
@ContentView(R.layout.activity_project_detail)
public class ProjectDetailActivity extends BaseActivity {
    private static final String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_CHOOSE=0x001;

    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.toolbar_btn_action)
    private ImageButton toolbar_btn_action;
    @ViewInject(R.id.project_detail_tv_id)
    private TextView project_detail_tv_id;
    @ViewInject(R.id.project_detail_tv_load_type)
    private TextView project_detail_tv_load_type;
    @ViewInject(R.id.project_detail_tv_load_price)
    private TextView project_detail_tv_load_price;
    @ViewInject(R.id.project_detail_tv_loader)
    private TextView project_detail_tv_loader;
    @ViewInject(R.id.project_detail_tv_loader_tel)
    private TextView project_detail_tv_loader_tel;
    @ViewInject(R.id.project_detail_tv_client)
    private TextView project_detail_tv_client;
    @ViewInject(R.id.project_detail_tv_client_tel)
    private TextView project_detail_tv_client_tel;
    @ViewInject(R.id.project_detail_tv_manager)
    private TextView project_detail_tv_manager;
    @ViewInject(R.id.project_detail_tv_manager_tel)
    private TextView project_detail_tv_manager_tel;
    @ViewInject(R.id.project_detail_ll_business)
    private LinearLayout project_detail_ll_business;
    @ViewInject(R.id.project_detail_tv_company)
    private TextView project_detail_tv_company;
    @ViewInject(R.id.project_detail_tv_company_tel)
    private TextView project_detail_tv_company_tel;
    @ViewInject(R.id.project_detail_tv_contact)
    private TextView project_detail_tv_contact;
    @ViewInject(R.id.project_detail_tv_contact_tel)
    private TextView project_detail_tv_contact_tel;
    @ViewInject(R.id.project_detail_ll_property)
    private LinearLayout project_detail_ll_property;
    @ViewInject(R.id.project_detail_ll_note)
    private LinearLayout project_detail_ll_note;
    @ViewInject(R.id.project_detail_ll_news)
    private LinearLayout project_detail_ll_news;


    @ViewInject(R.id.rlv_image)
    private RecyclerView rlv_image;

    List<ImageViewInfo> dataList=new ArrayList<>();
    private ImagePreviewListAdapter adapter;
    private GridLayoutManager mGridLayoutManager;
    private int project_id;
    private ProjectEntity projectEntity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("项目详情");
        toolbar_btn_action.setVisibility(View.VISIBLE);
        project_id=getIntent().getExtras().getInt("project_id");

        PermissionsUtils.getInstance().checkPermissions(this, permission, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void success() {
                LogUtil.d("申请权限通过");
            }

            @Override
            public void fail() {
                LogUtil.d("申请权限未通过");
                finish();
            }
        });

        rlv_image.setLayoutManager(mGridLayoutManager=new GridLayoutManager(this,3));
        adapter=new ImagePreviewListAdapter(this,dataList);
        rlv_image.setAdapter(adapter);
        rlv_image.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
                PreviewBuilder.from(ProjectDetailActivity.this)
                        .setImgs(adapter.getData())
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setType(PreviewBuilder.IndicatorType.Number)
                        .start();
            }
        });
        getData();

        EventBus.getDefault().register(this);
    }

    private void initView(){
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        project_detail_tv_id.setText(projectEntity.getPROJECT_ID()+"");
        project_detail_tv_load_type.setText(projectEntity.getLOAN_TYPE_CHS());
        project_detail_tv_load_price.setText(projectEntity.getLOAN_AMOUNT()+"");
        project_detail_tv_loader.setText(projectEntity.getBORROWER());
        if (!TextUtils.isEmpty(projectEntity.getBORROWER_PHONE())) {
            project_detail_tv_loader_tel.setText(projectEntity.getBORROWER_PHONE());
        } else {
            project_detail_tv_loader_tel.setVisibility(View.GONE);
        }
        if (project_detail_tv_loader_tel.getText() instanceof Spannable) {
            Spannable s = (Spannable) project_detail_tv_loader_tel.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
        project_detail_tv_client.setText(projectEntity.getCONTACT_PERSON());
        if (!TextUtils.isEmpty(projectEntity.getCONTACT_PHONE())) {
            project_detail_tv_client_tel.setText(projectEntity.getCONTACT_PHONE());
        } else {
            project_detail_tv_client_tel.setVisibility(View.GONE);
        }
        if (project_detail_tv_client_tel.getText() instanceof Spannable) {
            Spannable s = (Spannable) project_detail_tv_client_tel.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
        project_detail_tv_manager.setText(projectEntity.getBCM_NAME());
        if (!TextUtils.isEmpty(projectEntity.getBCM_PHONE())) {
            project_detail_tv_manager_tel.setText(projectEntity.getBCM_PHONE());
        } else {
            project_detail_tv_manager_tel.setVisibility(View.GONE);
        }
        if (project_detail_tv_manager_tel.getText() instanceof Spannable) {
            Spannable s = (Spannable) project_detail_tv_manager_tel.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }

        if(projectEntity.getPROJECT_STATUS()!= ProjectCenterListType.Todo.getType()){
            project_detail_ll_business.setVisibility(View.VISIBLE);
            project_detail_tv_company.setText(projectEntity.getBUSINESS().getCOMMISSIONED_NAME());
            project_detail_tv_company_tel.setText(projectEntity.getBUSINESS().getCOMMISSIONED_PHONE());
            project_detail_tv_contact.setText(projectEntity.getBUSINESS().getEXECUTE_NAME());
            project_detail_tv_contact_tel.setText(projectEntity.getBUSINESS().getEXECUTE_PHONE());
        }else{
            project_detail_ll_business.setVisibility(View.GONE);
        }
        List<PropertyEntity> propertyEntityList = projectEntity.getPROPERTY_LIST();
        for (int i = 0; i <propertyEntityList.size() ; i++) {
            PropertyEntity property=propertyEntityList.get(i);
            View v= LayoutInflater.from(this).inflate(R.layout.item_project_property,null);
            TextView property_city=v.findViewById(R.id.project_property_city);
            property_city.setText("["+property.getPCA_CODE_CHS()+"]");
            TextView property_type=v.findViewById(R.id.project_property_type);
            property_type.setText("["+property.getPROPERTY_TYPE_CHS()+"]");
            TextView property_name=v.findViewById(R.id.project_property_name);
            property_name.setText(property.getPROPERTY_NAME());
            TextView property_address=v.findViewById(R.id.project_property_address);
            property_address.setText(property.getADDRESS());
            TextView property_area=v.findViewById(R.id.project_property_area);
            property_area.setText(property.getAREA()+"平方米");
            TextView property_contact=v.findViewById(R.id.project_property_contact);
            property_contact.setText(property.getINSPECTION_CONTACT());
            TextView property_contact_tel=v.findViewById(R.id.project_property_contact_tel);
            if(!TextUtils.isEmpty(property.getINSPECTION_CONTACT())){
                property_contact_tel.setText(property.getINSPECTION_CONTACT());
                if (property_contact_tel.getText() instanceof Spannable) {
                    Spannable s = (Spannable) property_contact_tel.getText();
                    s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
                }
            }else{
                property_contact_tel.setVisibility(View.GONE);
            }
            TextView property_owner=v.findViewById(R.id.project_property_owner);
            property_owner.setText(property.getPROPERTY_RIGHTS().get(0).getPROPERTY_OWNER());//多个产权人怎么办？
            TextView property_owner_tel=v.findViewById(R.id.project_property_owner_tel);
            if(!TextUtils.isEmpty(property.getPROPERTY_RIGHTS().get(0).getPROPERTY_OWNER_PHONE())){
                property_owner_tel.setText(property.getPROPERTY_RIGHTS().get(0).getPROPERTY_OWNER_PHONE());
                if (property_owner_tel.getText() instanceof Spannable) {
                    Spannable s = (Spannable) property_owner_tel.getText();
                    s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
                }
            }else{
                property_owner_tel.setVisibility(View.GONE);
            }
            ImageButton button=v.findViewById(R.id.project_property_upload);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(PropertyAttachmentActivity.class);
//                    PhotoPicker.pick(ProjectDetailActivity.this,true,REQUEST_CODE_CHOOSE);
                }
            });
            project_detail_ll_property.addView(v);
        }
        initNote();
        initAction();
    }

    private void initAction(){
        List<ProjectActionEntity> actionList=projectEntity.getACT_LIST();
        for (int i = 0; i < actionList.size(); i++) {
            ProjectActionEntity action=actionList.get(i);
            View v= LayoutInflater.from(this).inflate(R.layout.item_project_news,null);
            TextView content=v.findViewById(R.id.project_news_tv_content);
            String time=Utils.transformIOSTime(action.getCREATE_TIME());
            time=time.substring(5,17);
            content.setText("["+time+"]"+action.getACT_CONTENT());
            project_detail_ll_news.addView(v);
        }
    }

    private void initNote(){
        int size = project_detail_ll_note.getChildCount();
        for (int i = 1; i < size; i++) {
            project_detail_ll_note.removeViewAt(1);
        }
        List<ProjectNoteEntity> projectNoteEntities = projectEntity.getNOTE_LIST();
        for (int i = 0; i <projectNoteEntities.size() ; i++) {
            ProjectNoteEntity note=projectNoteEntities.get(i);
            View v= LayoutInflater.from(this).inflate(R.layout.item_project_note,null);
            TextView note_create=v.findViewById(R.id.project_note_create);
            note_create.setText(note.getCREATE_NAME());
            TextView note_customer=v.findViewById(R.id.project_note_customer);
            note_customer.setText("["+note.getCUSTOMER_NAME()+"]");
            TextView note_time=v.findViewById(R.id.project_note_time);
            String time=Utils.transformIOSTime(note.getCREATE_TIME());
            note_time.setText(time.substring(5,17));
            TextView content=v.findViewById(R.id.project_note_content);
            content.setText(note.getPROJECT_NOTE_CONTENT());
            project_detail_ll_note.addView(v,i+1);
        }

    }

    private void getData(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id+"");
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<ProjectEntity> entity= JsonParse.parse(result,ProjectEntity.class);
                if(entity.isStatus()){
                    projectEntity=entity.getResult();
                    initView();
                }else{
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,true);
    }

    private void getProjectNote(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/GetProejctNotes");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id+"");
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                Type type = new TypeToken<List<ProjectNoteEntity>>() {}.getType();
                BaseEntity<List<ProjectNoteEntity>> entity= JsonParse.parseList(result,type);
                if(entity.isStatus()){
                    projectEntity.setNOTE_LIST(entity.getResult());
                    initNote();
//                    getData();
                }else{
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,false);
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

    @Event({R.id.toolbar_btn_back,R.id.project_detail_tv_note,R.id.add_note_btn_estimate,R.id.add_note_btn_apply,
            R.id.toolbar_btn_action,
            R.id.project_detail_tv_test1,R.id.project_detail_tv_test2,R.id.project_detail_tv_test3})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.project_detail_tv_note:
                Bundle bundle=new Bundle();
                bundle.putInt("project_id",project_id);
                bundle.putInt("actionid",1);
                startActivity(AddNoteActivity.class,bundle);
                break;
            case R.id.add_note_btn_apply:
                Bundle bundle1=new Bundle();
                bundle1.putInt("project_id",project_id);
                startActivity(BusinessApplyActivity.class,bundle1);
                break;
            case R.id.add_note_btn_estimate:
                showToast("评估成果");
                break;
            case R.id.toolbar_btn_action:
                showItemDialog();
                break;
            case R.id.project_detail_tv_test1:
                PhotoPicker.pick(this,REQUEST_CODE_CHOOSE);
                break;
            case R.id.project_detail_tv_test2:
                PhotoPicker.pick(this,10,REQUEST_CODE_CHOOSE);
                break;
            case R.id.project_detail_tv_test3:
                PhotoPicker.pick(this,10,true,REQUEST_CODE_CHOOSE);
                break;
        }
    }
    /**
     * 显示功能dialog
     */
    private void showItemDialog() {
        PopupMenu popup = new PopupMenu(this, toolbar_btn_action);
        Menu menu = popup.getMenu();
        menu.add(0, 2, 2, "项目暂停").setIcon(R.drawable.ic_action_pause_normal);
        menu.add(0, 3, 3, "项目终止").setIcon(R.drawable.ic_action_stop_normal);
        menu.add(0, 4, 4, "继续项目").setIcon(R.drawable.ic_action_help);
        menu.add(0,5,5,"派单").setIcon(R.drawable.ic_action_help);
//        menu.add(0, 5, 5, "改派公司").setIcon(R.drawable.ic_action_todo_normal);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                showToast(item.getTitle().toString());
                switch (item.getItemId()) {
                    case 1:
                        //projectAction(3);
                        break;
                    case 2:
                        projectAction(2);
                        break;
                    case 3:
                        projectAction(3);
//                        startActivity(new Intent(ProjectDetailsActivity.this, ApplyTerminationActivity.class).putExtra("project_id", project_id));
                        break;
                    case 4:
                        projectAction(4);
                        break;
                    case 5:
//                        startActivity(new Intent(ProjectDetailsActivity.this, AddNoteActivity.class).putExtra("project_id", project_id));
                        projectAction(5);
                        break;
                }
                return true;
            }
        });
        /*
         * 反射显示布局中的图标  我也不懂..抄的
         * */
        try {
            Field field = popup.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(popup);
            //helper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        popup.show();

    }

    /**
     * 项目动作
     *
     * @param action 2:暂停,3:继续,6:价格异议
     */
    private void projectAction(int action) {
        if (action == 2){
            Bundle bundle=new Bundle();
            bundle.putInt("project_id",project_id);
            bundle.putInt("actionid",2);
            startActivity(AddNoteActivity.class,bundle);
        }else if (action == 3){
            Bundle bundle=new Bundle();
            bundle.putInt("project_id",project_id);
            bundle.putInt("actionid",3);
            startActivity(AddNoteActivity.class,bundle);
        }else if (action == 4){
            modifyProjectStatus("40001001");
        }else if (action == 5){
            Bundle bundle=new Bundle();
            bundle.putInt("project_id",project_id);
            startActivity(CustomerListActivity.class,bundle);
        }
    }

    private void modifyProjectStatus(String statusVal){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/ModifyProjectStatus");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("project_status",statusVal);
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                if(entity.isStatus()){
                    //LogUtil.d("留言ID"+entity.getResult());
                    //EventBus.getDefault().post(new EventBusMessage<>("AddNote"));
                    //finish();
                    showToast("启用成功");
                }else{
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,true);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("AddNote".equals(message.getMessage())){
            getProjectNote();
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
                        dataList.add(new ImageViewInfo(file.getAbsolutePath()));
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
