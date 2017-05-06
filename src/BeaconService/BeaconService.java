package beaconService;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.widget.Toast;
import beaconService.Beacons.BeaconCreateDescription;
import beaconService.Beacons.Beacons;
import beaconService.Frames.Snapshot;

import java.util.List;


/**
 * Created by Kevin on 5/05/2017.
 * Test code using the Android Developer guide
 */
public class BeaconService extends Service {
    private Beacons beacons;
    private final IBinder mBeaconServiceBinder = new BeaconServiceBinder();
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private BluetoothReceiver mReceiver;
    private List<Snapshot> snapshots;
    private BluetoothAdapter adapter;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Put time-dependent code here

            stopSelf(msg.arg1);
        }
    }

    public class BeaconServiceBinder extends Binder {
        BeaconService getService () {
            return BeaconService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBeaconServiceBinder;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        mReceiver = new BluetoothReceiver();

        // Set the private receiver object
        // Consider setting IntentFilter param when code is more properly organised.
        registerReceiver(mReceiver, null, null, mServiceHandler);
        adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.startDiscovery();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Beacon Service Starting", Toast.LENGTH_SHORT).show();

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        adapter.cancelDiscovery();
        //Toast.makeText(this, "Beacon Service Done", Toast.LENGTH_SHORT).show();

    }

    /* Private methods
    *
    * */

    // Create a snapshot to use
    private void createSnapshot() {
        snapshots.add(new Snapshot(beacons));
    }


    /* Client methods
    * Methods that should be accessible by from the service.
    * */

    // Get snapshot within the given time
    public Snapshot snapshot(int thresholdUpper, int thresholdLower) {
        return null;
    }

    // Initiate a discovery command to get the signal strength and determine
    public void ping() {
        adapter.startDiscovery();
    }

    /* Broadcast discovery handler

     */
    private class BluetoothReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If device not found.
                if (beacons.findBeacon(device.getAddress()) == null) {
                    // Get a bluetooth device and create an object to handle it.
                    beacons.add(new BeaconCreateDescription(device));
                }

                // Set the RSSI
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                beacons.findBeacon(device.getAddress()).signalStrength = rssi;
            }
        }
    };
}
