
package com.thanhtuan.posnet.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StatusKhachHang {

    @SerializedName("Data")
    private List<KhachHang> mData;
    @SerializedName("ErrorCode")
    private Long mErrorCode;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("SiteID")
    private String mSiteID;
    @SerializedName("Status")
    private Boolean mStatus;
    @SerializedName("UserName")
    private String mUserName;

    public List<KhachHang> getData() {
        return mData;
    }

    public void setData(List<KhachHang> Data) {
        mData = Data;
    }

    public Long getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(Long ErrorCode) {
        mErrorCode = ErrorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String Message) {
        mMessage = Message;
    }

    public String getSiteID() {
        return mSiteID;
    }

    public void setSiteID(String SiteID) {
        mSiteID = SiteID;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean Status) {
        mStatus = Status;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String UserName) {
        mUserName = UserName;
    }

}
