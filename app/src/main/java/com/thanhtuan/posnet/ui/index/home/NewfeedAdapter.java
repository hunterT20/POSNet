package com.thanhtuan.posnet.ui.index.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.data.NewFeeds;

import java.util.List;


public class NewfeedAdapter extends RecyclerView.Adapter<NewfeedAdapter.NewfeedViewHolder> {
    private List<NewFeeds> mNewfeed;
    private LayoutInflater mLayoutInflater;

    public NewfeedAdapter(List<NewFeeds> mNewfeed, Context mContext) {
        this.mNewfeed = mNewfeed;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NewfeedAdapter.NewfeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_newfeeds,parent,false);
        return new NewfeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewfeedAdapter.NewfeedViewHolder holder, int position) {
        final NewFeeds newFeeds = mNewfeed.get(position);

        holder.txtvType.setText(newFeeds.getType());
        holder.txtvTitle.setText(newFeeds.getTitle());
        holder.txtvContent.setText(newFeeds.getContent());
    }

    @Override
    public int getItemCount() {
        return mNewfeed.size();
    }

    class NewfeedViewHolder extends RecyclerView.ViewHolder {
        private TextView txtvType, txtvTitle, txtvContent;
        NewfeedViewHolder(View itemView) {
            super(itemView);
            txtvType = itemView.findViewById(R.id.txtvType);
            txtvTitle = itemView.findViewById(R.id.txtvTitle);
            txtvContent = itemView.findViewById(R.id.txtvContent);
        }
    }
}
