package com.thanhtuan.posnet.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public POSCenterService newPOSCenterService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POSCenterService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(POSCenterService.class);
    }

}
