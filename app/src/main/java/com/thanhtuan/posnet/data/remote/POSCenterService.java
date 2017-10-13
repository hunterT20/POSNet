package com.thanhtuan.posnet.data.remote;

import com.thanhtuan.posnet.model.status.StatusKhachHang;
import com.thanhtuan.posnet.model.status.StatusKho;
import com.thanhtuan.posnet.model.status.StatusProduct;
import com.thanhtuan.posnet.model.status.StatusQuay;
import com.thanhtuan.posnet.model.status.StatusSearch;
import com.thanhtuan.posnet.model.data.NhanVien;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface POSCenterService {
    String URL = "http://192.168.1.79:81/svGetGiaBan.svc/";

    @GET("mLogin")
    Observable<NhanVien> login(@Query("strUserName") String Username, @Query("strPassword") String Password );

    @GET("SearchItemList")
    Observable<StatusSearch> search(@Query("strModel") String Model, @Query("strSiteID") String SiteID);

    @GET("GetGiaBan")
    Observable<StatusProduct> getItem(@Query("Siteid") String SiteID, @Query("Itemid") String ItemID);

    @GET("GetTonKhoAllSite")
    Observable<StatusKho> checkKho(@Query("ItemID") String ItemID);

    @GET("GetQuay")
    Observable<StatusQuay> checkQuay();

    @GET("GetInfoCustomer")
    Observable<StatusKhachHang> getKhachHang(@Query("CustomerId") String CustomerId, @Query("MobilePhone") String MobilePhone);
}
