package com.thanhtuan.posnet.view.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.Customer;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.model.KhachHang;
import com.thanhtuan.posnet.model.Kho;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.model.Quay;
import com.thanhtuan.posnet.model.StatusKhachHang;
import com.thanhtuan.posnet.model.StatusKho;
import com.thanhtuan.posnet.model.StatusQuay;
import com.thanhtuan.posnet.model.ThongTinGiaoHang;
import com.thanhtuan.posnet.util.AnimationUtil;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.ItemKhoAdapter;
import com.thanhtuan.posnet.view.adapter.ItemQuayAdapter;
import com.thanhtuan.posnet.view.adapter.KMAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReorderFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "ReOderFragment";
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
    @BindView(R.id.txtvLayHang)         TextView txtvLayHang;
    @BindView(R.id.txtvGiaoHang)        TextView txtvGiaHang;
    @BindView(R.id.txtvDCGiaoHang)      TextView txtvDCGiaoHang;
    @BindView(R.id.txtvQuay)            TextView txtvQuay;
    @BindView(R.id.edtTenKH)            EditText edtTenKH;
    @BindView(R.id.edtPhoneKH)          EditText edtPhoneKH;
    @BindView(R.id.edtDiaChi)           EditText edtDiaChi;

    /*Trạng thái của step:
    * step == 0: xác nhận thông tin khách hàng
    * step == 1: xác nhận thông tin giao hàng
    * step == 2: xác nhận danh sách sản phẩm*/
    private int step = 0;

    private List<ItemKM> productList;
    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;


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

        dataManager = POSCenterApplication.get(getActivity()).getComponent().dataManager();
        mSubscriptions = new CompositeDisposable();
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
            setThongTinGiaoHang();
        }else if (step == 2){
            ((ReOrderActivity)getActivity()).callFragment(new KQReOderFragment(),"Thông tin thanh toán");
        }
    }

    @OnClick(R.id.btnBack)
    public void BackClick(){
        if (step == 1){
            step --;
            setVisibleSearch();
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
            ThongTinSP.setVisibility(View.GONE);
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

    @OnClick(R.id.txtvCheckLayHang)
    public void CheckKho(){
        String ItemID = SharePreferenceUtil.getValueItemid(getActivity());
        mSubscriptions.add(dataManager
                .checkKho(ItemID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusKho>() {
                    @Override
                    public void onNext(@NonNull StatusKho statusKho) {
                        if (statusKho.getData() == null) return;
                        DialogCheckKho(statusKho.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @OnClick(R.id.txtvGetQuay)
    public void checkQuay(){
        mSubscriptions.add(dataManager
        .checkQuay()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(dataManager.getScheduler())
        .subscribeWith(new DisposableObserver<StatusQuay>() {
            @Override
            public void onNext(@NonNull StatusQuay statusQuay) {
                if (statusQuay.getData() == null) return;
                DialogCheckQuay(statusQuay.getData());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @SuppressLint("SetTextI18n")
    private void onCreateListPR(){
        Product product = ((ReOrderActivity)getActivity()).productCurrent;
        txtvTongTien.setText(NumberTextWatcherForThousand.getDecimalFormattedString(product.getSalesPrice().toString()) + "đ");
        txtvNamePR.setText(product.getItemName());
        productList = (product.getListItemkm());

        if (getActivity() == null) return;
        KMAdapter adapter = new KMAdapter(productList, getActivity());
        rcvKhuyenMai.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        setVisibleSearch();

        ImageView btnQRcode = ((ReOrderActivity)getActivity()).getQR();
        final EditText edtSearch = ((ReOrderActivity)getActivity()).getSearch();
        final TextView txtvLogo = ((ReOrderActivity)getActivity()).getLogo();

        txtvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.setVisibility(View.VISIBLE);
                txtvLogo.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    }
                    if (getActivity() == null) return false;
                    String SearchText = edtSearch.getText().toString();
                    if (SearchText.length() > 8){
                        if (SearchText.substring(0,1).endsWith("1")){
                            getInfoUser(SearchText,null);
                        }else {
                            getInfoUser(null,SearchText);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        btnQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //scan
            }
        });
    }

    private void getInfoUser(String CustomerID, String PhoneNumber){
        mSubscriptions.add(dataManager
                .getKhachHang(CustomerID, PhoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusKhachHang>() {
                    @Override
                    public void onNext(StatusKhachHang statusKhachHang) {
                        if (statusKhachHang.getData() != null){
                            KhachHang khachHang = statusKhachHang.getData().get(0);

                            edtTenKH.setText(khachHang.getCustomerName());
                            edtDiaChi.setText(khachHang.getAddress());
                            edtPhoneKH.setText(khachHang.getMobilePhone());
                        }else {
                            Toast.makeText(getActivity(), "Không tìm thấy thông tin khách hàng!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((ReOrderActivity)getActivity()).callFragment(new InfoProductFragment(),"Thông tin sản phẩm");
                setVisibleSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DialogCheckKho(List<Kho> list){
        final Dialog dialog = new Dialog(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_listkho, null);
        final ListView lv = view.findViewById(R.id.lvKho);
        ItemKhoAdapter adapter = new ItemKhoAdapter(getActivity(), list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kho kho = (Kho) lv.getItemAtPosition(i);
                txtvLayHang.setText(kho.getSiteId());
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.setTitle("Chọn kho");

        dialog.show();
    }

    private void DialogCheckQuay(List<Quay> list){
        final Dialog dialog = new Dialog(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_listkho, null);
        final ListView lv = view.findViewById(R.id.lvKho);
        ItemQuayAdapter adapter = new ItemQuayAdapter(getActivity(), list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Quay quay = (Quay) lv.getItemAtPosition(i);
                txtvQuay.setText(quay.getNote());
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.setTitle("Chọn quầy");

        dialog.show();
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
    private void setVisibleSearch(){
        ((ReOrderActivity)getActivity()).getCardViewSearch().setVisibility(View.VISIBLE);
    }

    private void setGoneSearch(){
        ((ReOrderActivity)getActivity()).getCardViewSearch().setVisibility(View.GONE);
    }

    /*Sau khi đã hoàn thành thông tin khách hàng*/
    private void setKHSuccess(){
        btnNext.setText(R.string.xacnhanSP);
        Customer customer = ((ReOrderActivity)getActivity()).customer;
        if (customer == null){
            customer = new Customer();
            customer.setName(edtTenKH.getText().toString());
            customer.setDiaChi(edtDiaChi.getText().toString());
            customer.setSDT(edtPhoneKH.getText().toString());
            ((ReOrderActivity)getActivity()).customer = customer;
        }else {
            edtTenKH.setText(customer.getName());
            edtDiaChi.setText(customer.getDiaChi());
            edtPhoneKH.setText(customer.getSDT());
        }

        
        setGoneSearch();
        AnimationUtil.SlideUP(ThongTinKH,getActivity());
        ThongTinKH.setVisibility(View.GONE);
        ThongTinGiaoHang.setVisibility(View.VISIBLE);
        AnimationUtil.SlideDown(ThongTinGiaoHang,getActivity());
        btnBack.setVisibility(View.VISIBLE);
    }

    private void setThongTinGiaoHang(){
        ThongTinGiaoHang.setVisibility(View.GONE);
        ThongTinKH.setVisibility(View.GONE);
        ThongTinSP.setVisibility(View.VISIBLE);
        TongTien.setVisibility(View.VISIBLE);
        btnNext.setText(R.string.thanhtoan);

        com.thanhtuan.posnet.model.ThongTinGiaoHang thongTinGiaoHang = ((ReOrderActivity)getActivity()).thongTinGiaoHang;
        if (thongTinGiaoHang == null){
            thongTinGiaoHang = new ThongTinGiaoHang();
            thongTinGiaoHang.setDiaChi(txtvDCGiaoHang.getText().toString());
            thongTinGiaoHang.setGiaoHang(txtvGiaHang.getText().toString());
            thongTinGiaoHang.setLayHang(txtvLayHang.getText().toString());
            thongTinGiaoHang.setNgayGiao(txtvDate.getText().toString());
            thongTinGiaoHang.setThoiGian(txtvTime.getText().toString());
            thongTinGiaoHang.setQuay(txtvQuay.getText().toString());
            ((ReOrderActivity)getActivity()).thongTinGiaoHang = thongTinGiaoHang;
        }
    }
}
