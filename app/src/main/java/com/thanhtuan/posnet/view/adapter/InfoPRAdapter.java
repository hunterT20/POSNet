package com.thanhtuan.posnet.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InfoPRAdapter extends RecyclerView.Adapter<InfoPRAdapter.InfoPRViewHolder> {
    private List<Product> mProduct;
    private LayoutInflater mLayoutInflater;
    private List<Product> listChon;

    public InfoPRAdapter(List<Product> mProduct, Context mContext) {
        this.mProduct = mProduct;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public InfoPRAdapter.InfoPRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_info_product,parent,false);
        return new InfoPRViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final InfoPRAdapter.InfoPRViewHolder holder, int position) {
        final Product product = mProduct.get(position);
        listChon = new ArrayList<>();

        holder.txtvNameKM.setText(product.getNamePR());
        holder.txtvDonGiaKM.setText(product.getDonGia() + "vnđ");
        holder.txtvSLKM.setText(product.getSL());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!product.getChon()){
                   setChon(product,holder);
                }else {
                    setBoChon(product,holder);
                }
            }
        });
    }

    private void setChon(Product product, InfoPRAdapter.InfoPRViewHolder holder){
        listChon.add(product);
        product.setChon(true);
        holder.itempr.setBackgroundResource(R.color.colorAccent);
    }

    private void setBoChon(Product product, InfoPRAdapter.InfoPRViewHolder holder){
        listChon.remove(product);
        product.setChon(false);
        holder.itempr.setBackgroundResource(R.color.cardview_light_background);
    }

    public List<Product> getProductChon(){
        return listChon;
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    class InfoPRViewHolder extends RecyclerView.ViewHolder {
        private TextView txtvNameKM, txtvDonGiaKM, txtvSLKM;
        private ConstraintLayout itempr;
        InfoPRViewHolder(View itemView) {
            super(itemView);
            txtvNameKM = itemView.findViewById(R.id.txtvNameKM);
            txtvDonGiaKM = itemView.findViewById(R.id.txtvDonGiaKM);
            txtvSLKM = itemView.findViewById(R.id.txtvSLKM);
            itempr = itemView.findViewById(R.id.itempr);
        }
    }
}
