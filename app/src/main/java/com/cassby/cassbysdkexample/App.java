package com.cassby.cassbysdkexample;

import android.app.Application;

import com.cassby.cassbysdk.CassbySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CassbySDK.getInstance().launch(this, "WyIxbFFpNFp2NGlvNEZ5T2x3bHJXOVQ4QWxwVE1FeUV0RCIsICIxMCIsICIwIl0=");
    }
}
