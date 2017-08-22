
package com.thanhtuan.posnet.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Kho {

    @SerializedName("ItemId")
    private String mItemId;
    @SerializedName("SiteId")
    private String mSiteId;
    @SerializedName("TonKho")
    private Long mTonKho;

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String ItemId) {
        mItemId = ItemId;
    }

    public String getSiteId() {
        return mSiteId;
    }

    public void setSiteId(String SiteId) {
        mSiteId = SiteId;
    }

    public Long getTonKho() {
        return mTonKho;
    }

    public void setTonKho(Long TonKho) {
        mTonKho = TonKho;
    }

}
