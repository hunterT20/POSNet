package com.thanhtuan.posnet.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;

import java.util.List;

/**
 * Created by Nusib on 6/19/2017.
 */

public class InfoPRAdapter extends RecyclerView.Adapter<InfoPRAdapter.InfoPRViewHolder> {
    private List<Product> mProduct;
    private LayoutInflater mLayoutInflater;

    public InfoPRAdapter(List<Product> mProduct, Context mContext) {
        this.mProduct = mProduct;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public InfoPRAdapter.InfoPRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_info_product,parent,false);
        return new InfoPRViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InfoPRAdapter.InfoPRViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class InfoPRViewHolder extends RecyclerView.ViewHolder {
        public InfoPRViewHolder(View itemView) {
            super(itemView);
        }
    }
}
