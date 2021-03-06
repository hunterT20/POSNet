package com.thanhtuan.posnet.ui.login;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;
import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.data.NhanVien;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.ui.index.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtEmail)    EditText edtEmail;
    @BindView(R.id.edtPass)     EditText edtPass;
    @BindView(R.id.ckbSave)     CheckBox ckbSave;
    @BindView(R.id.btnLogin)    Button btnLogin;

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
        if (getEmail.length() == 0){
            Toast.makeText(this, "Tên đăng nhập không được để trống!", Toast.LENGTH_SHORT).show();
        }else if (getPass.length() == 0){
            Toast.makeText(this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
        }else {
            btnLogin.setText("Loading...");
            login(getEmail,getPass);
        }
    }

    private void login(final String username, final String pass){
        mSubscriptions.add(dataManager
                .login(username, pass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<NhanVien>(){
                    @Override
                    public void onNext(@NonNull NhanVien user) {
                        if (user.getUserName().equals(username)){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            if (ckbSave.isChecked()){
                                SharePreferenceUtil.saveUser(LoginActivity.this,username,pass);
                            }
                            SharePreferenceUtil.setValueSiteid(LoginActivity.this,user.getSiteID());
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        btnLogin.setText("Đăng nhập");
                    }
                }));
    }

    public void setFullScreen() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
