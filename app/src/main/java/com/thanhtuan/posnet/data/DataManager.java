package com.thanhtuan.posnet.data;

import android.content.Context;

import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.data.remote.POSCenterService;
import com.thanhtuan.posnet.injection.component.DaggerDataManagerComponent;
import com.thanhtuan.posnet.injection.module.DataManagerModule;
import com.thanhtuan.posnet.model.StatusKhachHang;
import com.thanhtuan.posnet.model.StatusKho;
import com.thanhtuan.posnet.model.StatusProduct;
import com.thanhtuan.posnet.model.StatusQuay;
import com.thanhtuan.posnet.model.StatusSearch;
import com.thanhtuan.posnet.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class DataManager {
    @Inject
    POSCenterService posCenterService;
    @Inject
    Scheduler mSubscribeScheduler;

    public DataManager(Context context) {
        injectDependencies(context);
    }

    public DataManager(POSCenterService watchTowerService,
                       Scheduler subscribeScheduler) {
        posCenterService = watchTowerService;
        mSubscribeScheduler = subscribeScheduler;
    }

    private void injectDependencies(Context context){
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

    public Observable<StatusSearch> search(String Model, String SiteID){
        return posCenterService.search(Model, SiteID);
    }

    public Observable<StatusProduct> getProduct(String SiteID, String ItemID){
        return posCenterService.getItem(SiteID, ItemID);
    }

    public Observable<StatusKho> checkKho(String ItemID){
        return posCenterService.checkKho(ItemID);
    }

    public Observable<StatusQuay> checkQuay(){
        return posCenterService.checkQuay();
    }

    public Observable<StatusKhachHang> getKhachHang(String CustomerID, String PhoneNumber){
        return posCenterService.getKhachHang(CustomerID,PhoneNumber);
    }


}
