package com.thanhtuan.posnet.injection.module;

import com.thanhtuan.posnet.data.remote.POSCenterService;
import com.thanhtuan.posnet.data.remote.RetrofitHelper;
import com.thanhtuan.posnet.injection.scope.PerDataManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * DataManagerModule là đăng kí khởi tạo PosCenterService khi khởi động ứng dụng
 */
@Module
public class DataManagerModule {
    public DataManagerModule() {
    }

    @Provides
    @PerDataManager
    POSCenterService providePOSCenterService() {
        return new RetrofitHelper().newPOSCenterService();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler() {
        return Schedulers.io();
    }
}
