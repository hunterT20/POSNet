package com.thanhtuan.posnet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thanhtuan.posnet.model.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceUtil {
    private static String NAME = "MyPre";

    private static String VALUE_USERNAME = "Username";
    private static String VALUE_PASSWORD = "Password";
    private static String VALUE_LISTPRODUCT = "ListProduct";
    private static String VALUE_PRODUCT = "Product";
    private static String VALUE_CHANGE = "PRChange";
    private static String VALUE_POSITION = "Position";


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

    public static void setProduct(Context context, Product product) {
        Gson gson = new Gson();
        SharedPreferences mPrefs = context.getSharedPreferences(NAME,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        String json = gson.toJson(product);
        prefsEditor.putString(VALUE_PRODUCT, json);
        prefsEditor.apply();
    }

    public static Product getProduct(Context context){
        Gson gson = new Gson();
        SharedPreferences mPrefs = context.getSharedPreferences(NAME,MODE_PRIVATE);
        String json = mPrefs.getString(VALUE_PRODUCT, "");
        return gson.fromJson(json, Product.class);
    }

    public static void setListProduct(Context context, List<Product> list){
        Gson gson = new Gson();
        SharedPreferences mPrefs = context.getSharedPreferences(NAME,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        String json = gson.toJson(list);
        prefsEditor.putString(VALUE_LISTPRODUCT, json);
        prefsEditor.apply();
    }

    public static List<Product> getListProduct(Context context){
        Gson gson = new Gson();
        SharedPreferences mPrefs = context.getSharedPreferences(NAME,MODE_PRIVATE);
        String json = mPrefs.getString(VALUE_LISTPRODUCT, "");
        Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static void setProductChange(Context context, Boolean change){
        SharedPreferences MyPre =  context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPre.edit();
        editor.putBoolean(VALUE_CHANGE, change);
        editor.apply();
    }

    public static Boolean getProductChange(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(NAME,MODE_PRIVATE);
        return mPrefs.getBoolean(VALUE_CHANGE, false);
    }

    public static void setPosition(Context context, int position){
        SharedPreferences MyPre =  context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = MyPre.edit();
        editor.putInt(VALUE_POSITION, position);
        editor.apply();
    }

    public static int getPosition(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences(NAME,MODE_PRIVATE);
        return mPrefs.getInt(VALUE_POSITION, -1);
    }
}
