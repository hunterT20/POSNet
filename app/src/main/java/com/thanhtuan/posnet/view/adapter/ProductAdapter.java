package com.thanhtuan.posnet.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.CheckActivity;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> mProduct;
    private LayoutInflater mLayoutInflater;

    public ProductAdapter(List<Product> mProduct, Context mContext) {
        this.context = mContext;
        this.mProduct = mProduct;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_info_product,parent,false);
        return new ProductViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ProductAdapter.ProductViewHolder holder, int position) {
        final Product product = mProduct.get(position);

        holder.txtvNameKM.setText(product.getNamePR());
        holder.txtvDonGiaKM.setText(product.getDonGia() + "vnđ");
        holder.txtvSLKM.setText(product.getSL());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreferenceUtil.setProduct(context,product);
                SharePreferenceUtil.setProductChange(context,true);
                mProduct.remove(holder.getAdapterPosition());
                SharePreferenceUtil.setListProduct(context,mProduct);
                SharePreferenceUtil.setPosition(context,holder.getAdapterPosition());

                Intent intent = new Intent(context, CheckActivity.class);
                context.startActivity(intent);
                ((AppCompatActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView txtvNameKM, txtvDonGiaKM, txtvSLKM;
        ProductViewHolder(View itemView) {
            super(itemView);
            txtvNameKM = itemView.findViewById(R.id.txtvNameKM);
            txtvDonGiaKM = itemView.findViewById(R.id.txtvDonGiaKM);
            txtvSLKM = itemView.findViewById(R.id.txtvSLKM);
        }
    }
}
