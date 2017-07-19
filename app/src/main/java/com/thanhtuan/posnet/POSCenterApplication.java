package com.thanhtuan.posnet;

import android.app.Application;
import android.content.Context;

import com.thanhtuan.posnet.injection.component.ApplicationComponent;

public class POSCenterApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static POSCenterApplication get(Context context) {
        return (POSCenterApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
