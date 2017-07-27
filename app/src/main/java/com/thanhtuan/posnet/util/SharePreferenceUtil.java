package com.thanhtuan.posnet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceUtil {
    private static String NAME = "MyPre";

    private static String VALUE_USERNAME = "Username";
    private static String VALUE_PASSWORD = "Password";
    private static String VALUE_SITEID = "SiteID";
    private static String VALUE_ITEMID = "ItemID";

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

    public static void setValueSiteid(Context context, String SiteID){
        SharedPreferences MyPre =  context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPre.edit();
        editor.putString(VALUE_SITEID, SiteID);
        editor.apply();
    }

    public static String getValueSiteid(Context context){
        SharedPreferences MyPre = context.getSharedPreferences(NAME, MODE_PRIVATE);
        return MyPre.getString(VALUE_SITEID, "");
    }

    public static void setValueItemid(Context context, String ItemID){
        SharedPreferences MyPre =  context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPre.edit();
        editor.putString(VALUE_ITEMID, ItemID);
        editor.apply();
    }

    public static String getValueItemid(Context context){
        SharedPreferences MyPre = context.getSharedPreferences(NAME, MODE_PRIVATE);
        return MyPre.getString(VALUE_ITEMID, "");
    }
}
