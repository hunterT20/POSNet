package com.thanhtuan.posnet.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thanhtuan.posnet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReorderFragment extends Fragment {
    @BindView(R.id.ThongTinKH)
    ConstraintLayout ThongTinKH;
    @BindView(R.id.ThongTinGiaoHang)
    ConstraintLayout ThongTinGiaoHang;
    @BindView(R.id.TaiNha) ConstraintLayout TaiNha;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnNext) Button btnNext;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    /*Trạng thái của step:
    * step == 0: xác nhận thông tin khách hàng
    * step == 1: xác nhận thông tin giao hàng
    * step == 2: xác nhận danh sách sản phẩm*/
    private int step = 0;

    public ReorderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reorder, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,view);

        addViews();
        return view;
    }

    private void addViews() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                onRadioButtonChange(radioGroup, i);
            }
        });
    }

    private void onRadioButtonChange(RadioGroup radioGroup, int i) {
        int checkedRadioID = radioGroup.getCheckedRadioButtonId();

        switch (checkedRadioID){
            case R.id.rdbNha:
                TaiNha.setVisibility(View.VISIBLE);
                break;
            case R.id.rdbQuay:
                TaiNha.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick(R.id.btnNext)
    public void NextClick(){
        if (step == 0){
            step ++;
            btnNext.setText("Xác nhận SP");
            ThongTinGiaoHang.setVisibility(View.VISIBLE);
            ThongTinKH.setVisibility(View.GONE);
            btnBack.setVisibility(View.VISIBLE);
        }else if (step == 1){
            Toast.makeText(getActivity(), "kiểm tra sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnBack)
    public void BackClick(){
        if (step == 1){
            step --;
            ThongTinGiaoHang.setVisibility(View.GONE);
            ThongTinKH.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
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
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
