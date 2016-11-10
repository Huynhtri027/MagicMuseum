package co.experimentality.magicmuseum.view;

import android.app.Application;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.EventClient;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.InitializationException;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.MobileAnalyticsManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.EventDO;
import com.estimote.BeaconID;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.experimentality.magicmuseum.R;
import co.experimentality.magicmuseum.model.Piece;
import co.experimentality.magicmuseum.service.ThingspeakService;
import co.experimentality.magicmuseum.service.UbidotsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private Region region;

    private String scanId;

    private final static String LOG_TAG = Application.class.getSimpleName();
    private static MobileAnalyticsManager analytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            analytics = MobileAnalyticsManager.getOrCreateInstance(
                    this.getApplicationContext(),
                    "bb693346af8246afa45cf720a5f2dd33", //Amazon Mobile Analytics App ID
                    "us-east-1:931d28d3-c1a3-45e6-95aa-ffe80e41e2d4" //Amazon Cognito Identity Pool ID
            );
        } catch (InitializationException ex) {
            Log.e(this.getClass().getName(), "Failed to initialize Amazon Mobile Analytics", ex);
        }
        beaconManager = new BeaconManager(this);
        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener() {
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {
                    Log.i("TELEMETRY", "beaconID: " + tlm.deviceId +
                            ", temperature: " + tlm.temperature + " °C" +
                            ", ligth " + tlm.ambientLight + " lux");
                    ThingspeakService tsService = ThingspeakService.retrofit.create(ThingspeakService.class);
                    final Call<Piece> call =
                            tsService.sendTemperature("MIPG1K6YWOQW9DI2", tlm.temperature + "");
                    call.enqueue(new Callback<Piece>() {
                        @Override
                        public void onResponse(Call<Piece> call, Response<Piece> response) {
                            //Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Piece> call, Throwable t) {
                            //Toast.makeText(MainActivity.this, "Error :c", Toast.LENGTH_SHORT).show();
                        }
                    });
                    final Call<Piece> call2 =
                            tsService.sendLight("MIPG1K6YWOQW9DI2", tlm.ambientLight + "");
                    call2.enqueue(new Callback<Piece>() {
                        @Override
                        public void onResponse(Call<Piece> call, Response<Piece> response) {
                            //Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Piece> call, Throwable t) {
                            //Toast.makeText(MainActivity.this, "Error :c", Toast.LENGTH_SHORT).show();
                        }
                    });
                    UbidotsService uService = UbidotsService.retrofit.create(UbidotsService.class);
                    final Call<Piece> call1 =
                            uService.sendInfo("5823815c76254272e6b1d28a", "gGvTqMFqctSq7c3rxPzBu0cZS7cwDt",
                                    tlm.ambientLight + "");
                    call1.enqueue(new Callback<Piece>() {
                        @Override
                        public void onResponse(Call<Piece> call, Response<Piece> response) {
                            //Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Piece> call, Throwable t) {
                            //Toast.makeText(MainActivity.this, "Error :c", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        //insertDataAWS(tlm);
                        //generateAnalyticEvent(tlm);

                    }
                }
            }
        });

        region = new Region("Ranged Region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    double distance = Utils.computeAccuracy(nearestBeacon);
                    double number = distance;
                    number = Math.round(number * 100);
                    number = number / 100;

                    BeaconID bid = BeaconID.fromBeacon(nearestBeacon);

                    String s = bid.toString();

                    Fragment fragment;

                    if (list.size() > 1) {
                        Beacon nearestBeacon2 = list.get(1);
                        BeaconID bid1 = BeaconID.fromBeacon(nearestBeacon2);
                        double distance2 = Utils.computeAccuracy(nearestBeacon2);
                        double number2 = distance2;
                        number2 = Math.round(number2 * 100);
                        number2 = number2 / 100;

                        String s1 = bid1.toString();
                        fragment = new ObjectFragment(s, number + " m", s1, number2 + " m");
                    } else {
                        fragment = new ObjectFragment(s, number + " m");
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            }
        });
    }

    private void generateAnalyticEvent(EstimoteTelemetry tlm) {
        Log.d(LOG_TAG, "Generating custom event...");

        final EventClient eventClient =
                AWSMobileClient.defaultMobileClient().getMobileAnalyticsManager().getEventClient();

        final AnalyticsEvent event = eventClient.createEvent("TemperatureEvent")
                // A music app use case might include attributes such as:
                // .withAttribute("Playlist", "Amazing Songs 2015")
                // .withAttribute("Artist", "Various")
                // .withMetric("Song playtime", playTime);
                .withAttribute("Piece", tlm.deviceId + "")
                .withMetric("Temperature", tlm.temperature);

        eventClient.recordEvent(event);
        eventClient.submitEvents();
    }

    private void insertDataAWS(EstimoteTelemetry tlm) {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final EventDO event = new EventDO(); // Initialize the Notes Object

        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:
        // AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID()

        event.setEventId((double) new Date().getTime());
        event.setUserId("juanjo");
        event.setPieceId(tlm.deviceId + "");
        event.setLight(tlm.ambientLight);
        event.setTemperature(tlm.temperature);

        AmazonClientException lastException = null;

        try {
            Log.e(LOG_TAG, "JUST HERE ");
            dynamoDBMapper.save(event);
        } catch (final AmazonClientException ex) {
            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            lastException = ex;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });
        // Obtain a reference to the mobile client.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // pause/resume Mobile Analytics collection
        awsMobileClient.handleOnResume();
        if(analytics != null) {
            analytics.getSessionClient().resumeSession();
        }
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);
        beaconManager.stopTelemetryDiscovery(scanId);
        super.onPause();

        // Obtain a reference to the mobile client.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // pause/resume Mobile Analytics collection
        awsMobileClient.handleOnPause();

        if(analytics != null) {
            analytics.getSessionClient().pauseSession();
            analytics.getEventClient().submitEvents();
        }
    }


}
