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
import com.thanhtuan.posnet.model.Customer;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.model.ThongTinGiaoHang;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.view.fragment.CheckFragment;
import com.thanhtuan.posnet.view.fragment.ReorderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReOrderActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)    Toolbar toolbar;

    /*Các step trong quá trình mua hàng:
    * Step == 0: CheckFragment
    * Step == 1: ReOrderFragment
    * Step == 2: KQReOrderFragment*/
    public int Step = 0;
    public Boolean edit = false;
    public List<Product> listPRBuy;             /*List sp đã xác nhận mua*/
    public Product productCurrent;              /*SP đang mua*/
    public Customer customer;                   /*Thông tin khách hàng đang mua*/
    public ThongTinGiaoHang thongTinGiaoHang;   /*Thông tin nơi giao*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_order);
        ButterKnife.bind(this);
        addViews();
        addControls();
    }

    private void addControls() {
        productCurrent = new Product();
        listPRBuy = new ArrayList<>();
        customer = new Customer();
        thongTinGiaoHang = new ThongTinGiaoHang();
    }

    private void addViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        callFragment(new CheckFragment(),"Thông tin sản phẩm");
    }

    @Override
    public void onBackPressed() {
    }

    public void callFragment(Fragment fragment, String title) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frmContent, fragment)
                .commit();
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(title);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
}
