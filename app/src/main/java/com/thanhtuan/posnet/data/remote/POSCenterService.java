package com.thanhtuan.posnet.data.remote;

import com.thanhtuan.posnet.model.StatusProduct;
import com.thanhtuan.posnet.model.StatusSearch;
import com.thanhtuan.posnet.model.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface POSCenterService {
    String URL = "http://192.168.1.79:81/";

    @GET("svGetGiaBan.svc/mLogin")
    Observable<User> login(@Query("strUserName") String Username, @Query("strPassword") String Password );

    @GET("svGetGiaBan.svc/SearchItemList")
    Observable<StatusSearch> search(@Query("strModel") String Model, @Query("strSiteID") String SiteID);

    @GET("svGetGiaBan.svc/GetGiaBan?=H001&=132372")
    Observable<StatusProduct> getItem(@Query("Siteid") String SiteID, @Query("Itemid") String ItemID);
}
