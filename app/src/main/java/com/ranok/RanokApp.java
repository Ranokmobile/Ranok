package com.ranok;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;

import io.fabric.sdk.android.Fabric;
import com.ranok.network.NetApi;
import com.ranok.network.NetService;

public class RanokApp extends Application {

    private NetApi netApi;
    private static RanokApp app;
    private boolean loggedIn = false;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        app = this;
        netApi = NetService.getNetApi();
    }

    public static RanokApp getApp() {
        return app;
    }

    public NetApi getNetApi() {
        return netApi;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn() {
        this.loggedIn = true;
    }
    public void setLoggedOut() {
        this.loggedIn = false;
    }
}
