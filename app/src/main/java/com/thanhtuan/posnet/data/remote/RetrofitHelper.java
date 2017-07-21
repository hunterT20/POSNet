package com.thanhtuan.posnet.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public POSCenterService newPOSCenterService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POSCenterService.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(POSCenterService.class);
    }

}
