package com.cassby.cassbysdkexample;

import android.app.Application;

import com.cassby.cassbysdk.CassbySDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CassbySDK.getInstance().launch(this, "WyJzMnFzQ0tJZDI4UWJyeG9uUTN3UU5yT0NyaWhla1FHNCIsICIyNSJd");
    }
}
