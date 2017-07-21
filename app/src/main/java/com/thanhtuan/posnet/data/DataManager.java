package com.thanhtuan.posnet.data;

import android.content.Context;

import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.data.remote.POSCenterService;
import com.thanhtuan.posnet.injection.component.DaggerDataManagerComponent;
import com.thanhtuan.posnet.injection.module.DataManagerModule;
import com.thanhtuan.posnet.model.User;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class DataManager {
    @Inject protected POSCenterService posCenterService;
    @Inject protected Scheduler mSubscribeScheduler;

    public DataManager(Context context) {
        injectDependencies(context);
    }

    public DataManager(POSCenterService watchTowerService,
                       Scheduler subscribeScheduler) {
        posCenterService = watchTowerService;
        mSubscribeScheduler = subscribeScheduler;
    }

    protected void injectDependencies(Context context){
        DaggerDataManagerComponent.builder()
                .applicationComponent(POSCenterApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    public Scheduler getScheduler() {
        return mSubscribeScheduler;
    }

    public Observable<User> login(String username, String pass){
        return posCenterService.login(username, pass);
    }
}
