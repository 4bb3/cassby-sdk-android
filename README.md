# cassby-sdk-android

<b>Installation</b>

Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add the dependency

```
dependencies {
	 implementation 'com.github.4bb3:cassby-sdk-android:1.0.0'
}
```

<b>Usage</b>

1. In your ```Application class```

```

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CassbySDK.getInstance().launch(this, YOUR_TOKEN);
    }
}

```

2. When you want to submit a sale

```
CassbySDK.getInstance().initCheck(BRANCH_ID);
CassbySDK.getInstance().addToCheck(POSITION_NAME, PRICE_IN_KOPS, QTY);
CassbySDK.getInstance().addToCheck(SECOND_POSITION_NAME, PRICE_IN_KOPS, QTY);
CassbySDK.getInstance().commit();
```
