package com.thanhtuan.posnet.view.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Customer;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.KMAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReorderFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.ThongTinKH)          ConstraintLayout ThongTinKH;
    @BindView(R.id.ThongTinGiaoHang)    ConstraintLayout ThongTinGiaoHang;
    @BindView(R.id.TaiNha)              ConstraintLayout TaiNha;
    @BindView(R.id.ThongTinSP)          ConstraintLayout ThongTinSP;
    @BindView(R.id.TongTien)            LinearLayout TongTien;
    @BindView(R.id.btnBack)             Button btnBack;
    @BindView(R.id.btnNext)             Button btnNext;
    @BindView(R.id.radioGroup)          RadioGroup radioGroup;
    @BindView(R.id.rcvKhuyenMai)        RecyclerView rcvKhuyenMai;
    @BindView(R.id.txtvTongTien)        TextView txtvTongTien;
    @BindView(R.id.txtvDate)            TextView txtvDate;
    @BindView(R.id.txtvTime)            TextView txtvTime;
    @BindView(R.id.txtvNamePR)          TextView txtvNamePR;
    @BindView(R.id.edtTenKH)            EditText edtTenKH;
    @BindView(R.id.edtPhoneKH)          EditText edtPhoneKH;
    @BindView(R.id.edtDiaChi)           EditText edtDiaChi;

    /*Trạng thái của step:
    * step == 0: xác nhận thông tin khách hàng
    * step == 1: xác nhận thông tin giao hàng
    * step == 2: xác nhận danh sách sản phẩm*/
    private int step = 0;

    private List<Product> productList;

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

        productList = new ArrayList<>();
        if (getActivity() == null) return view;
        RecyclerViewUtil.setupRecyclerView(rcvKhuyenMai, new KMAdapter(productList,getActivity()),getActivity());

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

        if (((ReOrderActivity)getActivity()).customer != null){
            step = 1;
            setKHSuccess();
        }
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
            setKHSuccess();
        }else if (step == 1){
            step ++;
            onCreateListPR();

            ThongTinGiaoHang.setVisibility(View.GONE);
            ThongTinKH.setVisibility(View.GONE);
            ThongTinSP.setVisibility(View.VISIBLE);
            TongTien.setVisibility(View.VISIBLE);
            btnNext.setText(R.string.thanhtoan);

        }else if (step == 2){
            ((ReOrderActivity)getActivity()).callFragment(new KQReOderFragment(),"Thông tin thanh toán");
        }
    }

    @OnClick(R.id.btnBack)
    public void BackClick(){
        if (step == 1){
            step --;
            setVisibleButtonScan(true);
            btnBack.setVisibility(View.GONE);
            btnNext.setText(R.string.nextbutton);
            ThongTinKH.setVisibility(View.VISIBLE);
            ThongTinGiaoHang.setVisibility(View.GONE);
        }else if (step == 2){
            step --;
            btnNext.setText(R.string.xacnhanSP);

            TongTien.setVisibility(View.GONE);
            ThongTinGiaoHang.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.txtvDate)
    public void DateClick(){
        initDateDialog().show();
    }

    @OnClick(R.id.txtvTime)
    public void TimeClick(){
        initTimeDialog().show();
    }

    @SuppressLint("SetTextI18n")
    private void onCreateListPR(){
        Product product = ((ReOrderActivity)getActivity()).productCurrent;
        txtvTongTien.setText(product.getDonGia() + " vnđ");
        txtvNamePR.setText(product.getNamePR());
        productList = (product.getListKM());

        if (getActivity() == null) return;
        KMAdapter adapter = new KMAdapter(productList, getActivity());
        rcvKhuyenMai.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        setVisibleButtonScan(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((ReOrderActivity)getActivity()).callFragment(new CheckFragment(),"Thông tin sản phẩm");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private DatePickerDialog initDateDialog(){
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(
                getActivity(), this, year, month, day);
    }

    private TimePickerDialog initTimeDialog(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        return new TimePickerDialog(
                getActivity(), this, hour,minute,true);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month += 1;
        txtvDate.setText(day + "/" + month + "/" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        txtvTime.setText(hour + ":" + minute);
    }

    /*set trạng thái cho button Scan trên toolbar*/
    private void setVisibleButtonScan(Boolean visible){
        ((ReOrderActivity)getActivity()).getToolbar().getMenu().findItem(R.id.action_scan).setVisible(visible);
    }

    /*Sau khi đã hoàn thành thông tin khách hàng*/
    private void setKHSuccess(){
        btnNext.setText(R.string.xacnhanSP);

        Customer customer = new Customer();
        customer.setName(edtTenKH.getText().toString());
        customer.setDiaChi(edtDiaChi.getText().toString());
        customer.setSDT(edtPhoneKH.getText().toString());
        ((ReOrderActivity)getActivity()).customer = customer;

        setVisibleButtonScan(false);
        ThongTinKH.setVisibility(View.GONE);
        ThongTinGiaoHang.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);
    }
}
