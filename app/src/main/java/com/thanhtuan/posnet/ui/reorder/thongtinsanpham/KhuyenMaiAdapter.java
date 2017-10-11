package com.thanhtuan.posnet.ui.reorder.thongtinsanpham;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;

import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "KhuyenMaiAdapter";
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ItemKM> itemKMList;
    private Product product;

    private TextView txtvTamTinh;
    private TextView txtvGiam;
    private TextView txtvTongGia;

    private TextView txtvDonGiaPR;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private long GiamGia = 0;
    private long TongGia = 0;
    private long DonGia = 0;

    KhuyenMaiAdapter(Context context, List<ItemKM> itemKMList, Product product) {
        this.context = context;
        this.itemKMList = itemKMList;
        this.product = product;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.header_listview_info_product, parent, false);
            return new HeaderViewHolder (v);
        } else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_info_product, parent, false);
            return new ItemViewHolder (v);
        } else if(viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.footer_listview_info_product, parent, false);
            return new FooterViewHolder (v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (product == null) return;
        if(holder instanceof HeaderViewHolder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            addEventHeaders(headerViewHolder);
            headerViewHolder.txtvNamePR.setText(product.getItemName() + " (" + "Loại: " + product.getTypeItemID() + ")");

            if (itemKMList.size() != 0){
                headerViewHolder.txtvSoSPChon.setText("Khách hàng được chọn " +
                        itemKMList.get(0).getPermissonBuyItemAttach() + " sản phẩm!");
                if (context == null) return;
                TongGia = product.getSalesPrice();
                DonGia = product.getSalesPrice();
                for (ItemKM itemKM : itemKMList){
                    if (itemKM.getTachGia() == 1){
                        if (!itemKM.getChon()){
                            DonGia += itemKM.getPromotionPrice();
                            GiamGia += itemKM.getGiamGiaKLHKM();
                            TongGia = TongGia + itemKM.getPromotionPrice() - itemKM.getGiamGiaKLHKM();
                        }
                    }
                }
                txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(DonGia)) + "đ");
            }
        }else if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ItemKM itemKM = itemKMList.get(position - 1);

            itemViewHolder.txtvNameKM.setText(itemKM.getItemNameKM());
            itemViewHolder.txtvDonGiaKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(itemKM.getPromotionPrice())) + "đ");
            itemViewHolder.txtvSLKM.setText(String.valueOf(itemKM.getQuantity()));
            itemViewHolder.txtvKLKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(itemKM.getGiamGiaKLHKM())) + "đ");
            itemViewHolder.txtvQuyDinh.setText(itemKM.getQuiDinh());

            addEventItems(itemViewHolder, itemKM);
        }else if (holder instanceof FooterViewHolder){
            txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(DonGia)) + "đ");
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongGia)) + "đ");
            txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        }
    }

    private void addEventItems(final ItemViewHolder itemViewHolder, final ItemKM itemKM) {
        itemViewHolder.itempr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!itemKM.getChon()){
                        itemKM.setChon(true);
                        setTachGiaChon(itemKM);
                        itemViewHolder.itempr.setBackgroundResource(R.color.colorAccent);
                    }else {
                        itemKM.setChon(false);
                        setTachGiaKhongChon(itemKM);
                        itemViewHolder.itempr.setBackgroundResource(R.color.cardview_light_background);
                    }
                }catch (Exception ex){
                    Log.e(TAG, "onItemClick: " + ex);
                }
            }
        });
    }

    private void addEventHeaders(HeaderViewHolder headerViewHolder) {
        headerViewHolder.imgChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ItemID = SharePreferenceUtil.getValueItemid(context);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Chi tiết sản phẩm");

                WebView wv = new WebView(context);
                wv.loadUrl("https://dienmaycholon.vn/default/product/thongsokythuat/sap/"+ItemID);
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

    /**
     * setTachGiaKhongChon để set giá cho sản phẩm nếu sản phẩm khuyến mãi không chọn trong trường hợp
     * tách giá hoặc không
     * @param itemKM: sản phẩm khuyến mãi
     */
    private void setTachGiaKhongChon(ItemKM itemKM){
        long tonggia = product.getSalesPrice();
        if (itemKM.getTachGia() == 1){
            txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia + itemKM.getPromotionPrice())) + "đ");
            tonggia = tonggia + itemKM.getPromotionPrice() - itemKM.getGiamGiaKLHKM();
            GiamGia += itemKM.getGiamGiaKLHKM();
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
            txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        }else {
            txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
            txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
            txtvGiam.setText("0đ");
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
        }
    }

    /**
     * setTachGiaChon để set giá cho sản phẩm không tách giá
     */
    private void setTachGiaChon(ItemKM itemKM){
        long tonggia = product.getSalesPrice();
        GiamGia -= itemKM.getGiamGiaKLHKM();
        txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
        txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
        txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
    }

    @Override
    public long getItemId(int position) {
        if (position == 0) return super.getItemId(position);
        if (position == itemKMList.size() + 1) return super.getItemId(position);
        return itemKMList.get(position-1).hashCode();
    }

    @Override
    public int getItemCount() {
        return itemKMList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader (position)) {
            return TYPE_HEADER;
        }else if (isPositionFooter(position)){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader (int position) {
        return position == 0;
    }

    private boolean isPositionFooter (int position) {
        return position == itemKMList.size () + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtvNameKM, txtvDonGiaKM, txtvSLKM, txtvKLKM, txtvQuyDinh;
        ConstraintLayout itempr;

        ItemViewHolder(View itemView) {
            super(itemView);
            txtvDonGiaKM = itemView.findViewById(R.id.txtvDonGiaKM);
            txtvNameKM = itemView.findViewById(R.id.txtvNameKM);
            txtvSLKM = itemView.findViewById(R.id.txtvSLKM);
            txtvKLKM = itemView.findViewById(R.id.txtvKLKM);
            txtvQuyDinh = itemView.findViewById(R.id.txtvQuyDinh);
            itempr = itemView.findViewById(R.id.itempr);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView txtvNamePR;
        private TextView txtvSoSPChon;
        private ImageView imgChitiet;
        HeaderViewHolder(View itemView) {
            super (itemView);
            txtvDonGiaPR = itemView.findViewById(R.id.txtvDonGiaPR);
            txtvNamePR = itemView.findViewById(R.id.txtvNamePR);
            txtvSoSPChon = itemView.findViewById(R.id.txtvSoSPChon);
            imgChitiet = itemView.findViewById(R.id.imgChitiet);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View itemView) {
            super (itemView);
            txtvTamTinh = itemView.findViewById(R.id.txtvTamTinh);
            txtvGiam = itemView.findViewById(R.id.txtvGiam);
            txtvTongGia = itemView.findViewById(R.id.txtvTongGia);
        }
    }
}
