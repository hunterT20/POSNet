package com.thanhtuan.posnet.ui.reorder.thongtinsanpham;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.data.PromotionProducts;
import com.thanhtuan.posnet.model.data.Product;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.SharePreferenceUtil;

import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "KhuyenMaiAdapter";
    private Context context;
    private LayoutInflater layoutInflater;
    private List<PromotionProducts> promotionProductsList;
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

    KhuyenMaiAdapter(Context context, List<PromotionProducts> promotionProductsList, Product product) {
        this.context = context;
        this.promotionProductsList = promotionProductsList;
        this.product = product;
        layoutInflater = LayoutInflater.from(context);
        setHasStableIds(true);

        if (promotionProductsList.size() != 0){
            if (context == null) return;
            TongGia = product.getSalesPrice();
            DonGia = product.getSalesPrice();
            for (PromotionProducts promotionProducts : promotionProductsList){
                if (promotionProducts.getTachGia() == 1){
                    if (!promotionProducts.getmChonGiamGia()){
                        DonGia += promotionProducts.getPromotionPrice();
                        TongGia = TongGia + promotionProducts.getPromotionPrice();
                    }
                }
            }
        }
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
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            PromotionProducts promotionProducts = promotionProductsList.get(position - 1);

            itemViewHolder.itempr.setBackgroundResource(promotionProducts.getmChonGiamGia() || promotionProducts.getmChonSanPham() ? R.color.colorAccent : R.color.cardview_light_background);

            itemViewHolder.txtvNameKM.setText(promotionProducts.getItemNameKM());
            itemViewHolder.txtvDonGiaKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(promotionProducts.getPromotionPrice())) + "đ");
            itemViewHolder.txtvSLKM.setText(String.valueOf(promotionProducts.getQuantity()));
            itemViewHolder.txtvKLKM.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(promotionProducts.getGiamGiaKLHKM())) + "đ");
            itemViewHolder.txtvQuyDinh.setText(promotionProducts.getQuiDinh());

            addEventItems(itemViewHolder, promotionProducts);
        }
    }

    private void addEventItems(final ItemViewHolder itemViewHolder, final PromotionProducts promotionProducts) {
        itemViewHolder.itempr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (promotionProducts.getGiamGiaKLHKM() != 0){
                    if (promotionProducts.getTachGia() == 1) DialogKM(itemViewHolder, promotionProducts);
                }else {
                    if (!promotionProducts.getmChonSanPham()){
                        promotionProducts.setmChonSanPham(true);
                        setColorItemSelected(itemViewHolder, promotionProducts);
                    }else {
                        promotionProducts.setmChonSanPham(false);
                        setColorItemNotSelected(itemViewHolder, promotionProducts);
                    }
                }
            }
        });
    }

    private void addEventHeaders(ImageView imgChitiet) {
        imgChitiet.setOnClickListener(new View.OnClickListener() {
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

    private void DialogKM(final ItemViewHolder itemViewHolder , final PromotionProducts promotionProducts){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chọn khuyến mãi");
        final String[] item = {"Lấy sản phẩm","Lấy giảm giá","Không lấy"};
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Boolean flag_chon = false;
                switch (i){
                    case 0:
                        if (promotionProducts.getmChonGiamGia()){
                            TongGia += promotionProducts.getGiamGiaKLHKM();
                            GiamGia -= promotionProducts.getGiamGiaKLHKM();
                        }
                        promotionProducts.setmChonSanPham(true);
                        promotionProducts.setmChonGiamGia(false);
                        flag_chon = true;
                        break;
                    case 1:
                        if (!promotionProducts.getmChonGiamGia()){
                            promotionProducts.setmChonSanPham(false);
                            promotionProducts.setmChonGiamGia(true);
                            TongGia -= promotionProducts.getGiamGiaKLHKM();
                            GiamGia += promotionProducts.getGiamGiaKLHKM();
                        }
                        flag_chon = true;
                        break;
                    case 2:
                        if (promotionProducts.getmChonGiamGia()){
                            TongGia += promotionProducts.getGiamGiaKLHKM();
                            GiamGia -= promotionProducts.getGiamGiaKLHKM();
                        }
                        promotionProducts.setmChonSanPham(false);
                        promotionProducts.setmChonGiamGia(false);
                        flag_chon = false;
                        break;

                }
                if (flag_chon){
                    setColorItemSelected(itemViewHolder, promotionProducts);
                }else {
                    setColorItemNotSelected(itemViewHolder, promotionProducts);
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setColorItemSelected(ItemViewHolder itemViewHolder,PromotionProducts promotionProducts){
        setTachGiaChon(promotionProducts);
        itemViewHolder.itempr.setBackgroundResource(R.color.colorAccent);
    }

    private void setColorItemNotSelected(ItemViewHolder itemViewHolder,PromotionProducts promotionProducts){
        setTachGiaKhongChon(promotionProducts);
        itemViewHolder.itempr.setBackgroundResource(R.color.cardview_light_background);
    }

    /**
     * setTachGiaKhongChon để set giá cho sản phẩm nếu sản phẩm khuyến mãi không chọn trong trường hợp
     * tách giá hoặc không
     * @param promotionProducts: sản phẩm khuyến mãi
     */
    private void setTachGiaKhongChon(PromotionProducts promotionProducts){
        if (promotionProducts.getTachGia() == 1){
            if (txtvTongGia == null) return;
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongGia)) + "đ");
            txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        }
    }

    /**
     * setTachGiaChon để set giá cho sản phẩm không tách giá
     */
    private void setTachGiaChon(PromotionProducts promotionProducts){
        if (promotionProducts.getTachGia() == 1){
            if (txtvGiam == null) return;
            txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongGia)) + "đ");
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return promotionProductsList.size() + 2;
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
        return position == promotionProductsList.size () + 1;
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
            if (product == null) return;
            txtvNamePR.setText(product.getItemName() + " (" + "Loại: " + product.getTypeItemID() + ")");

            if (promotionProductsList.size() != 0){
                txtvSoSPChon.setText("Khách hàng được chọn " +
                        promotionProductsList.get(0).getPermissonBuyItemAttach() + " sản phẩm!");

                txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(DonGia)) + "đ");
            }

            addEventHeaders(imgChitiet);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View itemView) {
            super (itemView);
            txtvTamTinh = itemView.findViewById(R.id.txtvTamTinh);
            txtvGiam = itemView.findViewById(R.id.txtvGiam);
            txtvTongGia = itemView.findViewById(R.id.txtvTongGia);

            txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(DonGia)) + "đ");
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongGia)) + "đ");
            txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        }
    }
}
