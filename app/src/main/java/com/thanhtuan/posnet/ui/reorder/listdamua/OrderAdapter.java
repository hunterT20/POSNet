package com.thanhtuan.posnet.ui.reorder.listdamua;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;

import java.util.List;

public class OrderAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<Product> productList;

    public OrderAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getGroupCount() {
        return productList.isEmpty() ? 0 : productList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return productList.get(i).getListItemkm().size();
    }

    @Override
    public Object getGroup(int i) {
        return productList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return productList.get(i).getListItemkm().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Product product = productList.get(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_search, null);
        }

        TextView txtvNameItem = view.findViewById(R.id.txtvNameItem);
        TextView txtvItemID = view.findViewById(R.id.txtvItemID);
        TextView txtvPriceItem = view.findViewById(R.id.txtvPriceItem);

        txtvNameItem.setText(product.getItemName());
        txtvItemID.setText(product.getItemID());
        txtvPriceItem.setText(NumberTextWatcherForThousand.getDecimalFormattedString(product.getSalesPrice().toString()) + "đ");

        return view;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ItemKM itemKM = (ItemKM) getChild(i,i1);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater != null ? infalInflater.inflate(R.layout.item_search, null) : null;
        }else {
            TextView txtvNameItem = view.findViewById(R.id.txtvNameItem);
            TextView txtvItemID = view.findViewById(R.id.txtvItemID);
            TextView txtvPriceItem = view.findViewById(R.id.txtvPriceItem);

            txtvNameItem.setText(itemKM.getItemNameKM());
            txtvItemID.setText(itemKM.getItemIDKM());
            txtvPriceItem.setText(NumberTextWatcherForThousand.getDecimalFormattedString(itemKM.getPromotionPrice().toString()) + "đ");

        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
