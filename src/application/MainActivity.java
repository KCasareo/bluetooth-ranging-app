package application;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import beaconService.BeaconService;
import beaconService.BeaconService.BeaconServiceBinder;

/**
 * Created by Kevin on 30/04/2017.
 * Main Activity is the user interface that uses and displays results from the Navigation Service
 */
public class MainActivity extends Activity {
    private BeaconService beaconService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BeaconService.class);
        bindService(intent, beaconServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private ServiceConnection beaconServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BeaconServiceBinder binder = (BeaconServiceBinder) iBinder;
            beaconService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}