package com.thanhtuan.posnet.data;

import android.content.Context;

import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.data.remote.POSCenterService;
import com.thanhtuan.posnet.injection.component.DaggerDataManagerComponent;
import com.thanhtuan.posnet.injection.module.DataManagerModule;

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

    /*public Observable<String> login(String username,String pass){
        return Observable.from(username,pass)
                .concatMap(new Func1<Long, Observable<Post>>() {
                    @Override
                    public Observable<Post> call(Long aLong) {
                        return mHackerNewsService.getStoryItem(String.valueOf(aLong));
                    }
                }).flatMap(new Func1<Post, Observable<Post>>() {
                    @Override
                    public Observable<Post> call(Post post) {
                        return post.title != null ? Observable.just(post) : Observable.<Post>empty();
                    }
                });
    }*/
}
