package com.findingdata.oabank.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_add_note)
public class CustomerListActivity extends BaseActivity {

    @ViewInject(R.id.rv_main)
    private RecyclerView mrv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        mrv.setLayoutManager(new LinearLayoutManager(this));
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
    }
}
