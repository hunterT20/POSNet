package com.thanhtuan.posnet.injection.component;

import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.injection.module.DataManagerModule;
import com.thanhtuan.posnet.injection.scope.PerDataManager;

import dagger.Component;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
