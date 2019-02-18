package com.cassby.cassbysdkexample;

import android.app.Application;

import com.cassby.cassbysdk.CassbySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CassbySDK.getInstance().launch(this, "1lQi4Zv4io4FyOlwlrW9T8AlpTMEyEtD");

        CassbySDK.getInstance().initCheck();
        CassbySDK.getInstance().addToCheck("Картошка",5000,1.0);
        CassbySDK.getInstance().commit();
    }
}
