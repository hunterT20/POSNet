
package com.thanhtuan.posnet.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StatusProduct {

    @SerializedName("Data")
    private List<Product> mData;
    @SerializedName("ErrorCode")
    private Long mErrorCode;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("SiteID")
    private String mSiteID;
    @SerializedName("Status")
    private Long mStatus;
    @SerializedName("UserName")
    private String mUserName;

    public List<Product> getData() {
        return mData;
    }

    public void setData(List<Product> Data) {
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

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long Status) {
        mStatus = Status;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String UserName) {
        mUserName = UserName;
    }

}
