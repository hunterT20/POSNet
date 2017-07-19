package com.thanhtuan.posnet.data.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface POSCenterService {
    String URL = "http://192.168.1.79:81/";

    @GET("svGetGiaBan.svc/mLogin?strUserName={Username}&strPassword={Password}")
    Observable<String> login(@Path("Username") String Username,@Path("Password") String Password );
}
