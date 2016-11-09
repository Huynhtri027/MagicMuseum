package co.experimentality.magicmuseum.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.estimote.BeaconID;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.List;
import java.util.UUID;

import co.experimentality.magicmuseum.R;
import co.experimentality.magicmuseum.model.Piece;
import co.experimentality.magicmuseum.service.ThingspeakService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private Region region;

    private String scanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(this);
        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener() {
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {
                    Log.e("TELEMETRY", "beaconID: " + tlm.deviceId +
                            ", temperature: " + tlm.temperature + " °C" +
                            ", ligth " + tlm.ambientLight + " lux");
                    ThingspeakService tsService = ThingspeakService.retrofit.create(ThingspeakService.class);
                    final Call<Piece> call =
                            tsService.sendInfo("MIPG1K6YWOQW9DI2", tlm.temperature + "");

                    call.enqueue(new Callback<Piece>() {
                        @Override
                        public void onResponse(Call<Piece> call, Response<Piece> response) {
                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Piece> call, Throwable t) {
                            //Toast.makeText(MainActivity.this, "Error :c", Toast.LENGTH_SHORT).show();
                        }
                    });
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
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);
        beaconManager.stopTelemetryDiscovery(scanId);
        super.onPause();
    }


}
