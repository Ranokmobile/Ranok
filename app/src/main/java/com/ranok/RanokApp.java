package com.ranok;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.orhanobut.hawk.Hawk;
import com.ranok.network.NetApi;
import com.ranok.network.NetService;
import com.ranok.utils.Consts;

import io.fabric.sdk.android.Fabric;

public class RanokApp extends Application {

    private NetApi netApi;
    private static RanokApp app;
    private String version = "";


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        app = this;
        netApi = NetService.getNetApi(version);
        Hawk.init(this).build();
    }

    public static RanokApp getApp() {
        return app;
    }

    public NetApi getNetApi() {
        return netApi;
    }

    public boolean isLoggedIn() {
        return Hawk.contains(Consts.TOKEN);
    }

    public void setLoggedOut() {
        Hawk.delete(Consts.TOKEN);
    }
}
