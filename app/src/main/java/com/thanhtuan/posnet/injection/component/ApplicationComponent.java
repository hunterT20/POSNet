package com.thanhtuan.posnet.injection.component;

import android.app.Application;

import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.injection.module.ApplicationModule;
import com.thanhtuan.posnet.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(LoginActivity Login);

    Application application();
    DataManager dataManager();
}
