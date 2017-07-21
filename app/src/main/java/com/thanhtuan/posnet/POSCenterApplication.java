package com.thanhtuan.posnet;

import android.app.Application;
import android.content.Context;

import com.thanhtuan.posnet.injection.component.ApplicationComponent;
import com.thanhtuan.posnet.injection.component.DaggerApplicationComponent;
import com.thanhtuan.posnet.injection.module.ApplicationModule;

public class POSCenterApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static POSCenterApplication get(Context context) {
        return (POSCenterApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
