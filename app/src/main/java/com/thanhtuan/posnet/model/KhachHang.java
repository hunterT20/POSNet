
package com.thanhtuan.posnet.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class KhachHang {

    @SerializedName("Address")
    private String mAddress;
    @SerializedName("CardName")
    private String mCardName;
    @SerializedName("CardTypeID")
    private String mCardTypeID;
    @SerializedName("CustomerId")
    private String mCustomerId;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("MobilePhone")
    private String mMobilePhone;
    @SerializedName("SiteID")
    private String mSiteID;
    @SerializedName("TotalAmount")
    private Long mTotalAmount;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String Address) {
        mAddress = Address;
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String CardName) {
        mCardName = CardName;
    }

    public String getCardTypeID() {
        return mCardTypeID;
    }

    public void setCardTypeID(String CardTypeID) {
        mCardTypeID = CardTypeID;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String CustomerId) {
        mCustomerId = CustomerId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String CustomerName) {
        mCustomerName = CustomerName;
    }

    public String getMobilePhone() {
        return mMobilePhone;
    }

    public void setMobilePhone(String MobilePhone) {
        mMobilePhone = MobilePhone;
    }

    public String getSiteID() {
        return mSiteID;
    }

    public void setSiteID(String SiteID) {
        mSiteID = SiteID;
    }

    public Long getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(Long TotalAmount) {
        mTotalAmount = TotalAmount;
    }

}
