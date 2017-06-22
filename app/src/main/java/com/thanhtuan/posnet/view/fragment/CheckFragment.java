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
import com.rey.material.widget.FloatingActionButton;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.ScanUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.KMAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {
    @BindView(R.id.rcvKhuyenMai)    RecyclerView rcvKhuyenMai;
    @BindView(R.id.fabScan)         FloatingActionButton fabScan;
    @BindView(R.id.Thongtin)        ConstraintLayout ThongTin;
    @BindView(R.id.txtvNamePR)      TextView txtvNamePR;
    @BindView(R.id.txtvDonGiaPR)    TextView txtvDonGiaPR;
    @BindView(R.id.txtvSLPR)        TextView txtvSLPR;
    @BindView(R.id.btnReOrder)      Button btnReOrder;
    @BindView(R.id.btnHuyHang)      Button btnHuyHang;

    private List<Product> listKMAll;
    private List<Product> productList;

    private Boolean coSP = false;

    private KMAdapter adapter;
    public String codeBar;
    private Product product;

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
        productList = new ArrayList<>();
        if (getActivity() == null) return view;
        RecyclerViewUtil.setupRecyclerView(rcvKhuyenMai, new KMAdapter(listKMAll,getActivity()),getActivity());

        addViews();
        initData();

        return view;
    }

    private void addViews() {
        if (!SharePreferenceUtil.getProductChange(getActivity())){
            ThongTin.setVisibility(View.GONE);
            btnHuyHang.setVisibility(View.GONE);
        }
        else {
            fabScan.setVisibility(View.GONE);
        }

    }

    private void addControls(){
        if (getActivity() == null) return;
        adapter = new KMAdapter(listKMAll, getActivity());
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

        if (SharePreferenceUtil.getProductChange(getActivity())){
            product = SharePreferenceUtil.getProduct(getActivity());
            btnReOrder.setText(R.string.xacnhanSP);
        }
        else {
            product = new Product();
            product.setNamePR("TV 40 inch");
            product.setDonGia("6000000");
            product.setChon(false);
            product.setSL("10");
        }

        addControls();
    }

    @OnClick(R.id.txtvRecheck)
    public void ReCheckClick(){

    }
    @OnClick(R.id.fabScan)
    public void ScanClick(){
        ScanUtil.startScan(getActivity(), new MaterialBarcodeScanner.OnResultListener() {
            @Override
            public void onResult(Barcode barcode) {
                txtvNamePR.setText(barcode.rawValue);
                codeBar = barcode.rawValue;
                setGONE();
            }
        });
    }


    @OnClick(R.id.btnReOrder)
    public void ReOrderClick(){
        if (SharePreferenceUtil.getProductChange(getActivity())){
            setProductList();

            Intent intent = new Intent(getActivity(), ReOrderActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }else {
            product.setListKM(adapter.getProductChon());
            productList.add(product);
            SweetDialogUtil.onWarning(getActivity(), "Bạn có muốn mua thêm?", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ScanClick();
                    sweetAlertDialog.dismiss();
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    SharePreferenceUtil.setListProduct(getActivity(),productList);

                    Intent intent = new Intent(getActivity(), ReOrderActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                    sweetAlertDialog.dismiss();
                }
            });
        }
    }

    @OnClick(R.id.btnHuyHang)
    public void HuyHangClick(){
        SweetDialogUtil.onWarning(getActivity(), "Bạn muốn hủy SP?", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(getActivity(), ReOrderActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                sweetAlertDialog.dismiss();
            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (SharePreferenceUtil.getProductChange(getActivity())){
                    setProductList();

                    Intent intent = new Intent(getActivity(), ReOrderActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                    return true;
                }
                if (!coSP){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }else {
                    setVisible();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
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
                return false;
            }
        });
    }

    private void setProductList(){
        int position = SharePreferenceUtil.getPosition(getActivity());
        productList = SharePreferenceUtil.getListProduct(getActivity());
        product.setListKM(adapter.getProductChon());
        productList.add(position,product);
        SharePreferenceUtil.setListProduct(getActivity(),productList);
    }

    private void setGONE(){
        coSP = true;
        ThongTin.setVisibility(View.VISIBLE);
        fabScan.setVisibility(View.GONE);
    }
    private void setVisible(){
        coSP = false;
        ThongTin.setVisibility(View.GONE);
        fabScan.setVisibility(View.VISIBLE);
    }
}
