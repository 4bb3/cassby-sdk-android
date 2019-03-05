package com.cassby.cassbysdkexample;

import android.app.Application;

import com.cassby.cassbysdk.CassbySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

            CassbySDK.getInstance().launch(this, "WyJmRDZmTEpxWTVuUE9QQ0J5M3l0V3RTVkdneVdqajRPUiIsICIxMSJd  ");
    }
}
