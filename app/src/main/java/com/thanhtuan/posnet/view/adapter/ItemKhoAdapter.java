package com.thanhtuan.posnet.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Kho;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ItemKhoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layout;
    private List<Kho> khoList;

    public ItemKhoAdapter(Context context, List<Kho> khoList) {
        this.context = context;
        this.khoList = khoList;
        layout = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layout.inflate(R.layout.item_kho, null);
            holder = new ViewHolder();
            holder.txtvNameKho = view.findViewById(R.id.txtvNameKho);
            holder.txtvSLTon = view.findViewById(R.id.txtvSLTon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Kho kho = khoList.get(i);
        Log.e(TAG, "getView: " + String.valueOf(kho.getSiteId()));
        if (kho.getSiteId().equals("")){
            khoList.remove(i);
            return view;
        }
        holder.txtvNameKho.setText(kho.getSiteId());
        holder.txtvSLTon.setText(String.valueOf(kho.getTonKho()));
        return view;
    }

    @Override
    public int getCount() {
        return khoList.size();
    }

    @Override
    public Object getItem(int i) {
        return khoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        TextView txtvNameKho;
        TextView txtvSLTon;
    }
}
