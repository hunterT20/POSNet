package com.thanhtuan.posnet.ui.reorder.xacnhanmua;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.ui.reorder.thongtingiaohang.ReorderFragment;
import com.thanhtuan.posnet.ui.reorder.search.SearchFragment;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.ui.reorder.ReOrderActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class KQReOderFragment extends Fragment {
    private static final String TAG = "KQReOderFragment";
    @BindView(R.id.ThanhToan)       ConstraintLayout ThanhToan;
    @BindView(R.id.KQ)              ConstraintLayout KQ;
    @BindView(R.id.TraTruoc)        ConstraintLayout TraTruoc;
    @BindView(R.id.txtvTongTien)    TextView txtvTongTien;
    @BindView(R.id.btnXacNhan)      Button btnXacNhan;
    @BindView(R.id.radioGroup)      RadioGroup radioGroup;
    @BindView(R.id.edtTraTruoc)     EditText edtTraTruoc;

    /*Tham số biến step:
    * step == 0: màn hình xác nhận thanh toán
    * step == 1: màn hình xác nhận đã thành công*/
    private int step = 0;

    public KQReOderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kqre_oder, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        addViews();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void addViews() {
        int TongTien = ((ReOrderActivity)getActivity()).TongTien();
        txtvTongTien.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongTien)) + "đ");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                onRadioButtonChange(radioGroup, i);
            }
        });
        edtTraTruoc.addTextChangedListener(new NumberTextWatcherForThousand(edtTraTruoc));
    }

    @OnClick(R.id.btnXacNhan)
    public void XacNhanClick(){
        if (step ==0){
            step = 1;
            ((ReOrderActivity)getActivity()).productCurrent = null;
            showAlertDialogXacNhan();
            btnXacNhan.setText(R.string.success);
        }else {
            Gson gson = new Gson();
            String customer = gson.toJson(((ReOrderActivity)getActivity()).customer);
            String TTGH = gson.toJson(((ReOrderActivity)getActivity()).thongTinGiaoHang);
            String list = gson.toJson(((ReOrderActivity)getActivity()).listPRBuy);
            HashMap<String,Object> param = new HashMap<>();
            param.put("Customer",customer);
            param.put("TTGH",TTGH);
            param.put("list_sp",list);
            Log.d(TAG, "XacNhanClick: " + param);

            ((ReOrderActivity)getActivity()).customer = null;
            ((ReOrderActivity)getActivity()).thongTinGiaoHang = null;
            ((ReOrderActivity)getActivity()).listPRBuy.clear();
            ((ReOrderActivity)getActivity()).callFragment(new SearchFragment(),"Tìm kiếm");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (step == 0){
                    SweetDialogUtil.onWarning(getActivity(), "Bạn chỉnh sửa thông tin?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            ((ReOrderActivity)getActivity()).callFragment(new ReorderFragment(),"Thông tin Order");
                            sweetAlertDialog.dismiss();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onRadioButtonChange(RadioGroup radioGroup, int i) {
        int checkedRadioID = radioGroup.getCheckedRadioButtonId();

        switch (checkedRadioID){
            case R.id.rdbTraHet:
                TraTruoc.setVisibility(View.GONE);
                break;
            case R.id.rdbTraGop:
                TraTruoc.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void showAlertDialogXacNhan(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn tiếp tục order?");
        builder.setCancelable(false);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((ReOrderActivity)getActivity()).thongTinGiaoHang = null;
                ((ReOrderActivity)getActivity()).callFragment(new SearchFragment(),"Tìm kiếm");
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                KQ.setVisibility(View.VISIBLE);
                ThanhToan.setVisibility(View.GONE);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
