package com.thanhtuan.posnet.ui.reorder;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.data.Customer;
import com.thanhtuan.posnet.model.data.Product;
import com.thanhtuan.posnet.model.data.ThongTinGiaoHang;
import com.thanhtuan.posnet.ui.reorder.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReOrderActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)    Toolbar toolbar;
    @BindView(R.id.btnQRcode)  ImageView btnQRCode;
    @BindView(R.id.edtSearch)  EditText edtSearch;
    @BindView(R.id.txtvLogo)   TextView txtvLogo;
    @BindView(R.id.cardview_search)    CardView cardViewSearch;

    /*Các step trong quá trình mua hàng:
    * Step == 0: InfoProductFragment
    * Step == 1: ReOrderFragment
    * Step == 2: KQReOrderFragment*/
    public int Step = 0;
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
        productCurrent = null;
        listPRBuy = new ArrayList<>();
        customer = null;
        thongTinGiaoHang = null;
    }

    private void addViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        callFragment(new SearchFragment(),"Tìm kiếm");
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
    public ImageView getQR(){
        return btnQRCode;
    }
    public EditText getSearch(){
        return edtSearch;
    }
    public TextView getLogo(){
        return txtvLogo;
    }
    public CardView getCardViewSearch(){
        return cardViewSearch;
    }

    public int TongTien(){
        int TongTien = 0;
        for (Product product : listPRBuy){
            TongTien += product.getSalesPrice();
        }
        return TongTien;
    }
}
