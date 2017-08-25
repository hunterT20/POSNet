package com.thanhtuan.posnet.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.ItemSearch;
import com.thanhtuan.posnet.util.AnimationUtil;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.fragment.InfoProductFragment;

import java.util.List;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ItemSearchViewHolder> {
    private List<ItemSearch> listItems;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public ItemSearchAdapter(List<ItemSearch> listItems, Context context) {
        this.listItems = listItems;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ItemSearchAdapter.ItemSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_search,parent,false);
        return new ItemSearchAdapter.ItemSearchViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ItemSearchAdapter.ItemSearchViewHolder holder, int position) {
        final ItemSearch itemSearch = listItems.get(position);

        holder.txtvItemID.setText(itemSearch.getItemID());
        holder.txtvItemName.setText(itemSearch.getItemName());
        holder.txtvItemPrice.setText(NumberTextWatcherForThousand.getDecimalFormattedString(itemSearch.getSalesPrice().toString()) + "đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreferenceUtil.setValueItemid(context,itemSearch.getItemID());
                ((ReOrderActivity)context).callFragment(new InfoProductFragment(),"Thông tin");
            }
        });
        AnimationUtil.ScaleList(holder.itemView, context);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ItemSearchViewHolder extends RecyclerView.ViewHolder {
        private TextView txtvItemName,txtvItemID,txtvItemPrice;
        ItemSearchViewHolder(View itemView) {
            super(itemView);
            txtvItemID = itemView.findViewById(R.id.txtvItemID);
            txtvItemName = itemView.findViewById(R.id.txtvNameItem);
            txtvItemPrice = itemView.findViewById(R.id.txtvPriceItem);
        }
    }
}
