package com.thanhtuan.posnet.view.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.ItemKM;

import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ItemKM> itemKMList;
    ViewHolder viewHolder;

    public KhuyenMaiAdapter(Context context, List<ItemKM> itemKMList) {
        this.context = context;
        this.itemKMList = itemKMList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemKMList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemKMList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_info_product, null);
            holder = new ViewHolder();
            holder.txtvDonGiaKM = view.findViewById(R.id.txtvDonGiaKM);
            holder.txtvNameKM = view.findViewById(R.id.txtvNameKM);
            holder.txtvSLKM = view.findViewById(R.id.txtvSLKM);
            holder.itempr = view.findViewById(R.id.itempr);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ItemKM itemKM = itemKMList.get(i);

        holder.txtvNameKM.setText(itemKM.getItemNameKM());
        holder.txtvDonGiaKM.setText(String.valueOf(itemKM.getPromotionPrice()));
        holder.txtvSLKM.setText(String.valueOf(itemKM.getQuantity()));
        return view;
    }

    private static class ViewHolder{
        TextView txtvNameKM, txtvDonGiaKM, txtvSLKM;
        ConstraintLayout itempr;
    }
}
