package com.thanhtuan.posnet.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.rey.material.widget.CheckBox;
import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.util.SweetDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtEmail)    EditText edtEmail;
    @BindView(R.id.edtPass) EditText edtPass;
    @BindView(R.id.ckbSave)    CheckBox ckbSave;

    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharePreferenceUtil.loadUser(LoginActivity.this,edtEmail,edtPass);
        dataManager = POSCenterApplication.get(getApplication()).getComponent().dataManager();
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    @OnClick(R.id.btnLogin)
    public void LoginClick(){
        String username = "thanhtuan";
        String pass = "123456";

        if (checkPass(username,pass)){
            SweetDialogUtil.onSuccess(this, "Đăng nhập thành công!", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    sweetAlertDialog.dismiss();
                }
            });
        }
    }

    private Boolean checkPass(String username, String pass){
        String getEmail = edtEmail.getText().toString();
        String getPass  = edtPass.getText().toString();
        if (edtEmail.getText().toString().equals(username)){
            if (edtPass.getText().toString().equals(pass)){
                if (ckbSave.isChecked()){
                    SharePreferenceUtil.saveUser(LoginActivity.this,getEmail,getPass);
                }
                return true;
            }
            else {
                SweetDialogUtil.onError(this,"Sai Password");
                return false;
            }
        }else {
            SweetDialogUtil.onError(this,"Email không tồn tại!");
            return false;
        }
    }

    private void login(String username, String pass){
        mSubscriptions.add(dataManager
                .login(username, pass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe());
    }

    public void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
