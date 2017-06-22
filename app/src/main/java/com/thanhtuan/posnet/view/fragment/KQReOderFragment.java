package com.thanhtuan.posnet.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.util.SweetDialogUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class KQReOderFragment extends Fragment {
    @BindView(R.id.ThanhToan)
    ConstraintLayout ThanhToan;
    @BindView(R.id.KQ) ConstraintLayout KQ;

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
        return view;
    }

    @OnClick(R.id.btnXacNhan)
    public void XacNhanClick(){
        if (step ==0){
            step = 1;
            SweetDialogUtil.onSuccess(getActivity(), "Mua hàng thành công!", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    KQ.setVisibility(View.VISIBLE);
                    ThanhToan.setVisibility(View.GONE);
                    sweetAlertDialog.dismiss();
                }
            });
        }else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
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

}
