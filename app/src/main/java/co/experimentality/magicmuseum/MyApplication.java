package co.experimentality.magicmuseum;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.enableDebugLogging(true);
    }
}
