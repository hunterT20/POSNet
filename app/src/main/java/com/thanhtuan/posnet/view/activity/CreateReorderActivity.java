package com.thanhtuan.posnet.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.view.adapter.ViewPagerAdapter;
import com.thanhtuan.posnet.view.fragment.InfoPRFragment;
import com.thanhtuan.posnet.view.fragment.ReorderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateReorderActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reorder);
        ButterKnife.bind(this);

        addViews();
    }

    private void addViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Check Product");

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        callFragmentInfoPR();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void callFragmentInfoPR(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.viewpager, new InfoPRFragment())
                .commit();
    }
}
