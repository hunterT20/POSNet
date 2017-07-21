package com.thanhtuan.posnet.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;
import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.User;
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
    @BindView(R.id.edtPass)     EditText edtPass;
    @BindView(R.id.ckbSave)     CheckBox ckbSave;

    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharePreferenceUtil.loadUser(LoginActivity.this,edtEmail,edtPass);
        dataManager = POSCenterApplication.get(this).getComponent().dataManager();
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    @OnClick(R.id.btnLogin)
    public void LoginClick(){
        String getEmail = edtEmail.getText().toString();
        String getPass  = edtPass.getText().toString();

        login(getEmail,getPass);
    }

    private void login(final String username, final String pass){
        mSubscriptions.add(dataManager
                .login(username, pass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<User>(){
                    @Override
                    public void onNext(@NonNull User user) {
                        if (user.getUserName().equals(username)){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            if (ckbSave.isChecked()){
                                SharePreferenceUtil.saveUser(LoginActivity.this,username,pass);
                            }
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
