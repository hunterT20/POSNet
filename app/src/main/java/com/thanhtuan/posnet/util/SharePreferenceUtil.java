package com.thanhtuan.posnet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceUtil {
    private static String NAME = "MyPre";

    private static String VALUE_USERNAME = "Username";
    private static String VALUE_PASSWORD = "Password";


    public static void saveUser(Context context, String username, String pass) {
        SharedPreferences MyPre =  context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPre.edit();
        editor.putString(VALUE_USERNAME, username);
        editor.putString(VALUE_PASSWORD, pass);
        editor.apply();
    }

    public static void loadUser(Context context,EditText edtUserName,EditText edtPassword) {
        SharedPreferences MyPre = context.getSharedPreferences(NAME, MODE_PRIVATE);
        String UsernameValue = MyPre.getString(VALUE_USERNAME, "");
        String PasswordValue = MyPre.getString(VALUE_PASSWORD, "");

        edtUserName.setText(UsernameValue);
        edtPassword.setText(PasswordValue);
    }
}
