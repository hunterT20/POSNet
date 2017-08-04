
package com.thanhtuan.posnet.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Kho {

    @SerializedName("SiteName")
    private String mSiteName;
    @SerializedName("SiteId")
    private String mSiteId;

    public String getmSiteName() {
        return mSiteName;
    }

    public void setmSiteName(String SiteName) {
        mSiteName = SiteName;
    }

    public String getSiteId() {
        return mSiteId;
    }

    public void setSiteId(String SiteId) {
        mSiteId = SiteId;
    }
}
