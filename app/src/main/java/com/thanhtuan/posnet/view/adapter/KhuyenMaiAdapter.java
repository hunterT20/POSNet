package com.thanhtuan.posnet.view.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<ItemKM> listChon = new ArrayList<>();

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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!itemKM.getChon()){
                    setChon(itemKM,holder);
                }else {
                    setBoChon(itemKM,holder);
                }
            }
        });
        return view;
    }

    private void setChon(ItemKM product, ViewHolder holder){
        listChon.add(product);
        product.setChon(true);
        holder.itempr.setBackgroundResource(R.color.colorAccent);
    }

    private void setBoChon(ItemKM product, ViewHolder holder){
        listChon.remove(product);
        product.setChon(false);
        holder.itempr.setBackgroundResource(R.color.cardview_light_background);
    }

    private static class ViewHolder{
        TextView txtvNameKM, txtvDonGiaKM, txtvSLKM;
        ConstraintLayout itempr;
    }
}
