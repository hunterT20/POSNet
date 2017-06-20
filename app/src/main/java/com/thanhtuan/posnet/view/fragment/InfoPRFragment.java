package com.thanhtuan.posnet.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.google.android.gms.vision.barcode.Barcode;
import com.rey.material.widget.FloatingActionButton;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.ScanUtil;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.adapter.InfoPRAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoPRFragment extends Fragment {
    @BindView(R.id.rcvKhuyenMai)
    RecyclerView rcvKhuyenMai;
    @BindView(R.id.fabScan)
    FloatingActionButton fabScan;
    @BindView(R.id.Thongtin)
    ConstraintLayout ThongTin;
    @BindView(R.id.txtvNamePR)
    TextView txtvNamePR;

    private List<Product> productList;
    private InfoPRAdapter adapter;

    private Boolean coSP = false;
    public String codeBar;

    public InfoPRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_pr, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        productList = new ArrayList<>();
        if (getActivity() == null) return view;
        RecyclerViewUtil.setupRecyclerView(rcvKhuyenMai, new InfoPRAdapter(productList,getActivity()),getActivity());

        addViews();
        initData();

        return view;
    }

    private void addViews() {
        ThongTin.setVisibility(View.GONE);
    }

    private void addControls(){
        if (getActivity() == null) return;
        adapter = new InfoPRAdapter(productList, getActivity());
        rcvKhuyenMai.setAdapter(adapter);
    }

    private void initData() {
        Product product1 = new Product("Tivi 29 inch","8.000.000","1",false);
        Product product2 = new Product("Bột giặt","50.000", "3",false);
        Product product3 = new Product("Bột giặt2","50.000", "3",false);
        Product product4 = new Product("Bột giặt3","50.000", "3",false);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
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
        SweetDialogUtil.onWarning(getActivity(), "Bạn có muốn mua thêm?", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                ScanClick();
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
