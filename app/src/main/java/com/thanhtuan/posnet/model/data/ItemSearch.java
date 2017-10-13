
package com.thanhtuan.posnet.model.data;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ItemSearch {

    @SerializedName("Bonus")
    private Long mBonus;
    @SerializedName("DVT")
    private String mDVT;
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
    private List<Object> mListItemkm;
    @SerializedName("Mota")
    private Object mMota;
    @SerializedName("QuantityCan")
    private Long mQuantityCan;
    @SerializedName("SalesPrice")
    private Long mSalesPrice;
    @SerializedName("SiteID")
    private Object mSiteID;
    @SerializedName("SoLuongTon")
    private Long mSoLuongTon;
    @SerializedName("TypeItemID")
    private String mTypeItemID;

    public Long getBonus() {
        return mBonus;
    }

    public void setBonus(Long Bonus) {
        mBonus = Bonus;
    }

    public String getDVT() {
        return mDVT;
    }

    public void setDVT(String DVT) {
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

    public List<Object> getListItemkm() {
        return mListItemkm;
    }

    public void setListItemkm(List<Object> ListItemkm) {
        mListItemkm = ListItemkm;
    }

    public Object getMota() {
        return mMota;
    }

    public void setMota(Object Mota) {
        mMota = Mota;
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

    public Object getSiteID() {
        return mSiteID;
    }

    public void setSiteID(Object SiteID) {
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
