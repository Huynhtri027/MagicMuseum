package co.experimentality.magicmuseum;

import android.app.Application;
import android.util.Log;

import com.amazonaws.mobile.AWSMobileClient;
import com.estimote.sdk.EstimoteSDK;


public class MyApplication extends Application {

    private final static String LOG_TAG = Application.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        initializeApplication();
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");
        EstimoteSDK.enableDebugLogging(true);
    }

    private void initializeApplication() {
        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
    }
}
