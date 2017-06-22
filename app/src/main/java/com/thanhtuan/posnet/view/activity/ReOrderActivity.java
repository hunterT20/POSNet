package com.thanhtuan.posnet.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.view.fragment.ReorderFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReOrderActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_order);
        ButterKnife.bind(this);
        addViews();
    }

    private void addViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        callFragment(new ReorderFragment(),"Thông tin Order");
    }

    public void callFragment(Fragment fragment, String title) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frmContent, fragment)
                .commit();
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(title);
    }
}
