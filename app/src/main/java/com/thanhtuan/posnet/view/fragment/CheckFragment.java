package com.thanhtuan.posnet.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.google.android.gms.vision.barcode.Barcode;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.ScanUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.KMAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {
    @BindView(R.id.rcvKhuyenMai)    RecyclerView rcvKhuyenMai;
    @BindView(R.id.Thongtin)        ConstraintLayout ThongTin;
    @BindView(R.id.txtvNamePR)      TextView txtvNamePR;
    @BindView(R.id.txtvDonGiaPR)    TextView txtvDonGiaPR;
    @BindView(R.id.txtvSLPR)        TextView txtvSLPR;
    @BindView(R.id.btnReOrder)      Button btnReOrder;

    private List<Product>   listKMAll;      /*Tất cả sản phẩm khuyến mãi của sản phẩm*/
    private Boolean         coSP = false;   /*Set điều kiện có sản phẩm hay không để */
    public  String          codeBar;
    private Product         product;

    public CheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        listKMAll = new ArrayList<>();
        if (getActivity() == null) return view;
        RecyclerViewUtil.setupRecyclerView(rcvKhuyenMai, new KMAdapter(listKMAll,getActivity()),getActivity());

        addViews();
        initData();

        return view;
    }

    private void addViews() {
        if (((ReOrderActivity)getActivity()).productCurrent != null){
            product = ((ReOrderActivity)getActivity()).productCurrent;
        }else {
            //ThongTin.setVisibility(View.GONE);
        }
    }

    private void addControls(){
        if (getActivity() == null) return;
        KMAdapter adapter = new KMAdapter(product.getListKM(), getActivity());
        rcvKhuyenMai.setAdapter(adapter);
    }

    private void initData() {
        Product KM1 = new Product("1","Tivi 29 inch","8.000.000","1",false);
        Product KM2 = new Product("2","Bột giặt","50.000", "3",false);
        Product KM3 = new Product("3","Bột giặt2","50.000", "3",false);
        Product KM4 = new Product("4","Bột giặt3","50.000", "3",false);

        listKMAll.add(KM1);
        listKMAll.add(KM2);
        listKMAll.add(KM3);
        listKMAll.add(KM4);

        if (product == null){
            product = new Product();
            product.setNamePR("TV 40 inch");
            product.setDonGia("6000000");
            product.setChon(false);
            product.setSL("10");
            product.setListKM(listKMAll);
        }

        addControls();
    }

    @OnClick(R.id.txtvRecheck)
    public void ReCheckClick(){
    }

    @OnClick(R.id.btnReOrder)
    public void ReOrderClick(){
        ((ReOrderActivity)getActivity()).productCurrent = product;
        ((ReOrderActivity)getActivity()).listPRBuy.add(product);
        ((ReOrderActivity)getActivity()).callFragment(new ReorderFragment(),"Thông tin Order");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!coSP){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }else {
                    setVisible();
                }
                return true;
            case R.id.action_scan:
                Scan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        ((ReOrderActivity)getActivity()).getToolbar().getMenu().findItem(R.id.action_scan).setVisible(true);

        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setGONE();
                searchViewAndroidActionBar.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void Scan(){
        ScanUtil.startScan(getActivity(), new MaterialBarcodeScanner.OnResultListener() {
            @Override
            public void onResult(Barcode barcode) {
                txtvNamePR.setText(barcode.rawValue);
                codeBar = barcode.rawValue;
                setGONE();
            }
        });
    }

    private void setGONE(){
        coSP = true;
        ThongTin.setVisibility(View.VISIBLE);
    }

    private void setVisible(){
        coSP = false;
        ThongTin.setVisibility(View.GONE);
    }
}
