package com.thanhtuan.posnet.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.util.SweetDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPass) EditText edtPass;
    @BindView(R.id.txtvForgetPass) TextView txtvForgetPass;
    @BindView(R.id.ckbSave)
    CheckBox ckbSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharePreferenceUtil.loadUser(LoginActivity.this,edtEmail,edtPass);
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
                }
            });
        }
    }

    private Boolean checkPass(String username, String pass){
        String getEmail = edtEmail.getText().toString();
        String getPass = edtPass.getText().toString();
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
}
