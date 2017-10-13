package com.thanhtuan.posnet.ui.reorder.thongtinsanpham;

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
import com.thanhtuan.posnet.model.data.PromotionProducts;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;

import java.util.ArrayList;
import java.util.List;

import static org.greenrobot.eventbus.EventBus.TAG;


public class KMAdapter extends RecyclerView.Adapter<KMAdapter.InfoPRViewHolder> {
    private Context context;
    private List<PromotionProducts> mProduct;
    private LayoutInflater mLayoutInflater;
    private List<PromotionProducts> listChon = new ArrayList<>();

    public KMAdapter(List<PromotionProducts> mProduct, Context mContext) {
        this.context = mContext;
        this.mProduct = mProduct;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public KMAdapter.InfoPRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_info_product,parent,false);
        return new InfoPRViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final KMAdapter.InfoPRViewHolder holder, int position) {
        final PromotionProducts product = mProduct.get(position);

        holder.txtvNameKM.setText(product.getItemNameKM());
        holder.txtvSLKM.setText(String.valueOf(product.getQuantity()));
        holder.txtvDonGiaKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(product.getPromotionPrice().toString()) + "đ");
        if (product.getmChonGiamGia())
        {
            holder.itempr.setBackgroundResource(R.color.colorAccent);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: hahahahah");
                if (!product.getmChonGiamGia()){
                   setChon(product,holder);
                }else {
                    setBoChon(product,holder);
                }
            }
        });
    }

    private void setChon(PromotionProducts product, KMAdapter.InfoPRViewHolder holder){
        listChon.add(product);
        product.setmChonGiamGia(true);
        holder.itempr.setBackgroundResource(R.color.colorAccent);
    }

    private void setBoChon(PromotionProducts product, KMAdapter.InfoPRViewHolder holder){
        listChon.remove(product);
        product.setmChonGiamGia(false);
        holder.itempr.setBackgroundResource(R.color.cardview_light_background);
    }

    public List<PromotionProducts> getProductChon(){
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
