package com.thanhtuan.posnet.ui.reorder.thongtinsanpham;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;

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
            holder.txtvKLKM = view.findViewById(R.id.txtvKLKM);
            holder.txtvQuyDinh = view.findViewById(R.id.txtvQuyDinh);
            holder.itempr = view.findViewById(R.id.itempr);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ItemKM itemKM = itemKMList.get(i);

        holder.txtvNameKM.setText(itemKM.getItemNameKM());
        holder.txtvDonGiaKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(itemKM.getPromotionPrice())) + "đ");
        holder.txtvSLKM.setText(String.valueOf(itemKM.getQuantity()));
        holder.txtvKLKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(itemKM.getGiamGiaKLHKM())) + "đ");
        holder.txtvQuyDinh.setText(itemKM.getQuiDinh());
        return view;
    }

    private static class ViewHolder{
        TextView txtvNameKM, txtvDonGiaKM, txtvSLKM, txtvKLKM, txtvQuyDinh;
        ConstraintLayout itempr;
    }
}
