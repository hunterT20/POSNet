package com.thanhtuan.posnet.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.view.fragment.CheckFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);

        addViews();
    }

    private void addViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Check Product");

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frmContent, new CheckFragment())
                .commit();
    }
}
