package com.thanhtuan.posnet.ui.reorder.thongtingiaohang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Quay;

import java.util.List;

public class ItemQuayAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layout;
    private List<Quay> quayList;

    public ItemQuayAdapter(Context context, List<Quay> quayList) {
        this.context = context;
        this.quayList = quayList;
        this.layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return quayList.size();
    }

    @Override
    public Object getItem(int i) {
        return quayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layout.inflate(R.layout.item_quay, null);
            holder = new ViewHolder();
            holder.txtvIDQuay = view.findViewById(R.id.txtvIDQuay);
            holder.txtvNameQuay = view.findViewById(R.id.txtvTenQuay);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Quay quay = quayList.get(i);
        holder.txtvIDQuay.setText(quay.getStandId());
        holder.txtvNameQuay.setText(quay.getNote());
        return view;
    }

    private static class ViewHolder {
        TextView txtvNameQuay;
        TextView txtvIDQuay;
    }
}
