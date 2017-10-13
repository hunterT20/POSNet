
package com.thanhtuan.posnet.model.data;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PromotionProducts {

    @SerializedName("GiamGiaKLHKM")
    private Long mGiamGiaKLHKM;
    @SerializedName("IDHeader")
    private String mIDHeader;
    @SerializedName("ItemIDCan")
    private String mItemIDCan;
    @SerializedName("ItemIDKM")
    private String mItemIDKM;
    @SerializedName("ItemNameKM")
    private String mItemNameKM;
    @SerializedName("Note")
    private String mNote;
    @SerializedName("PermissonBuyItemAttach")
    private Long mPermissonBuyItemAttach;
    @SerializedName("PromotionPrice")
    private Long mPromotionPrice;
    @SerializedName("Quantity")
    private Long mQuantity;
    @SerializedName("QuiDinh")
    private String mQuiDinh;
    @SerializedName("TachGia")
    private Long mTachGia;
    private Boolean mChonGiamGia = false;
    private Boolean mChonSanPham = false;

    public Boolean getmChonSanPham() {
        return mChonSanPham;
    }

    public void setmChonSanPham(Boolean mChonSanPham) {
        this.mChonSanPham = mChonSanPham;
    }

    public Boolean getmChonGiamGia() {
        return mChonGiamGia;
    }

    public void setmChonGiamGia(Boolean mChonGiamGia) {
        this.mChonGiamGia = mChonGiamGia;
    }


    public Long getGiamGiaKLHKM() {
        return mGiamGiaKLHKM;
    }

    public void setGiamGiaKLHKM(Long GiamGiaKLHKM) {
        mGiamGiaKLHKM = GiamGiaKLHKM;
    }

    public String getIDHeader() {
        return mIDHeader;
    }

    public void setIDHeader(String IDHeader) {
        mIDHeader = IDHeader;
    }

    public String getItemIDCan() {
        return mItemIDCan;
    }

    public void setItemIDCan(String ItemIDCan) {
        mItemIDCan = ItemIDCan;
    }

    public String getItemIDKM() {
        return mItemIDKM;
    }

    public void setItemIDKM(String ItemIDKM) {
        mItemIDKM = ItemIDKM;
    }

    public String getItemNameKM() {
        return mItemNameKM;
    }

    public void setItemNameKM(String ItemNameKM) {
        mItemNameKM = ItemNameKM;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String Note) {
        mNote = Note;
    }

    public Long getPermissonBuyItemAttach() {
        return mPermissonBuyItemAttach;
    }

    public void setPermissonBuyItemAttach(Long PermissonBuyItemAttach) {
        mPermissonBuyItemAttach = PermissonBuyItemAttach;
    }

    public Long getPromotionPrice() {
        return mPromotionPrice;
    }

    public void setPromotionPrice(Long PromotionPrice) {
        mPromotionPrice = PromotionPrice;
    }

    public Long getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Long Quantity) {
        mQuantity = Quantity;
    }

    public String getQuiDinh() {
        return mQuiDinh;
    }

    public void setQuiDinh(String QuiDinh) {
        mQuiDinh = QuiDinh;
    }

    public Long getTachGia() {
        return mTachGia;
    }

    public void setTachGia(Long TachGia) {
        mTachGia = TachGia;
    }
}
