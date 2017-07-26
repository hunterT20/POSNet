
package com.thanhtuan.posnet.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Product {

    @SerializedName("DVT")
    private Object mDVT;
    @SerializedName("FlagPromotion")
    private Long mFlagPromotion;
    @SerializedName("GiamThem")
    private Long mGiamThem;
    @SerializedName("GiamThemMC")
    private Long mGiamThemMC;
    @SerializedName("IDHeader")
    private String mIDHeader;
    @SerializedName("ItemID")
    private String mItemID;
    @SerializedName("ItemName")
    private String mItemName;
    @SerializedName("List_Itemkm")
    private List<ItemKM> mListItemkm;
    @SerializedName("QuantityCan")
    private Long mQuantityCan;
    @SerializedName("SalesPrice")
    private Long mSalesPrice;
    @SerializedName("SiteID")
    private String mSiteID;
    @SerializedName("SoLuongTon")
    private Long mSoLuongTon;
    @SerializedName("TypeItemID")
    private String mTypeItemID;

    public Object getDVT() {
        return mDVT;
    }

    public void setDVT(Object DVT) {
        mDVT = DVT;
    }

    public Long getFlagPromotion() {
        return mFlagPromotion;
    }

    public void setFlagPromotion(Long FlagPromotion) {
        mFlagPromotion = FlagPromotion;
    }

    public Long getGiamThem() {
        return mGiamThem;
    }

    public void setGiamThem(Long GiamThem) {
        mGiamThem = GiamThem;
    }

    public Long getGiamThemMC() {
        return mGiamThemMC;
    }

    public void setGiamThemMC(Long GiamThemMC) {
        mGiamThemMC = GiamThemMC;
    }

    public String getIDHeader() {
        return mIDHeader;
    }

    public void setIDHeader(String IDHeader) {
        mIDHeader = IDHeader;
    }

    public String getItemID() {
        return mItemID;
    }

    public void setItemID(String ItemID) {
        mItemID = ItemID;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String ItemName) {
        mItemName = ItemName;
    }

    public List<ItemKM> getListItemkm() {
        return mListItemkm;
    }

    public void setListItemkm(List<ItemKM> ListItemkm) {
        mListItemkm = ListItemkm;
    }

    public Long getQuantityCan() {
        return mQuantityCan;
    }

    public void setQuantityCan(Long QuantityCan) {
        mQuantityCan = QuantityCan;
    }

    public Long getSalesPrice() {
        return mSalesPrice;
    }

    public void setSalesPrice(Long SalesPrice) {
        mSalesPrice = SalesPrice;
    }

    public String getSiteID() {
        return mSiteID;
    }

    public void setSiteID(String SiteID) {
        mSiteID = SiteID;
    }

    public Long getSoLuongTon() {
        return mSoLuongTon;
    }

    public void setSoLuongTon(Long SoLuongTon) {
        mSoLuongTon = SoLuongTon;
    }

    public String getTypeItemID() {
        return mTypeItemID;
    }

    public void setTypeItemID(String TypeItemID) {
        mTypeItemID = TypeItemID;
    }

}
