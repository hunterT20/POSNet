package com.thanhtuan.posnet.data.remote;

import com.thanhtuan.posnet.model.StatusKho;
import com.thanhtuan.posnet.model.StatusProduct;
import com.thanhtuan.posnet.model.StatusSearch;
import com.thanhtuan.posnet.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface POSCenterService {
    String URL = "http://192.168.1.79:81/svGetGiaBan.svc/";

    @GET("mLogin")
    Observable<User> login(@Query("strUserName") String Username, @Query("strPassword") String Password );

    @GET("SearchItemList")
    Observable<StatusSearch> search(@Query("strModel") String Model, @Query("strSiteID") String SiteID);

    @GET("GetGiaBan")
    Observable<StatusProduct> getItem(@Query("Siteid") String SiteID, @Query("Itemid") String ItemID);

    @GET("GetTonKhoAllSite")
    Observable<StatusKho> checkKho(@Query("ItemID") String ItemID);
}
