package com.thanhtuan.posnet.util;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialogUtil {
    public static void onSuccess(Context context, String alert, SweetAlertDialog.OnSweetClickListener onClick){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(alert)
                .setConfirmClickListener(onClick)
                .show();
    }

    public static void onError(Context context,String alert){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(alert)
                .setContentText("Vui lòng kiểm tra lại")
                .show();
    }
}
