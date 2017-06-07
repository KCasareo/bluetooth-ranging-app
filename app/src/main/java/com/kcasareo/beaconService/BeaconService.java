package com.kcasareo.beaconService;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.*;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
//import com.kcasareo.beaconService.beacons.BeaconCreateDescription;
//import com.kcasareo.beaconService.beacons.Beacons;
//import com.kcasareo.beaconService.frames.Frame;
//import com.kcasareo.beaconService.frames.Snapshot;
//import com.kcasareo.beaconService.IBeaconServiceCallback;

import com.kcasareo.beaconService.beacons.Beacons;
import com.kcasareo.beaconService.beacons.bluetooth.Bluetooth;
import com.kcasareo.beaconService.beacons.bluetooth.GattCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import static com.kcasareo.beaconService.frames.Snapshot.MAX_REFRESH_TIME;


/**
 * Created by Kevin on 5/05/2017.
 * Test code using the Android Developer guide
 */
public class BeaconService extends Service {
    private final String TAG = "Beacon Service";
    private Beacons beacons = new Beacons();
    //private final IBinder mBeaconServiceBinder = new BeaconServiceBinder();
    //private Looper mServiceLooper;
    //private ServiceHandler mServiceHandler;
    //private BluetoothReceiver mReceiver;
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();
    private BluetoothManager mBluetoothManager;
    //private BluetoothGatt gatt;
    // Use a thread safe list;
    //private List<Snapshot> snapshots = Collections.synchronizedList(new ArrayList<Snapshot>());
    //private List<Frame> frames = Collections.synchronizedList(new ArrayList<Frame>());
    private BluetoothAdapter mBluetoothAdapter;
    //private Timer snapshotScheduler;
    //private final int MAX_SNAPSHOTS_HELD = 10;
    private IntentFilter mReceiverFilter;
    private Handler mServiceHandler;
    //private BluetoothAdapter mBluetoothAdapter;

    /* Messages for the service handler
    *
    *
    public enum BEACON_MSG {
        REGISTER(0),
        UNREGISTER(1),
        SET_VALUE(2),
        SNAPSHOT(3);

        private final int value;
        BEACON_MSG(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }

    }//*/

    /* Message handler for BeaconService
    *
    * */
    /*
    private final class ServiceHandler extends Handler {
        /* Manual message handling?
        *  Don't know if I want this yet.
        *
        *
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        // Non AIDL messenging.
        @Override
        public void handleMessage(Message msg) {
            // Put time-dependent code here
            // Convert msg.what int into a human readable enum message.
            BEACON_MSG beaconMsg = BEACON_MSG.values()[msg.what];
            switch(beaconMsg) {
                case REGISTER:
                    break;
                case UNREGISTER:
                    break;
                case SET_VALUE:
                    break;
                case SNAPSHOT:
                    Messenger messenger = msg.replyTo;
                    Message reply = new Message();
                    reply.obj = lastSnapshot();
                    try {
                        messenger.send(reply);
                    } catch (RemoteException e) {
                        // Handle when client no longer exists.
                    }
                    break;
            }
            //stopSelf(msg.arg1);
        }
    }//*/

    /**
     * Not needed anymore since I have an AIDL binder.
     * public class BeaconServiceBinder extends Binder {
     * public BeaconService getService () {
     * return BeaconService.this;
     * }
     * }
     */

    @Override
    public IBinder onBind(Intent intent) {

        return mBeaconServiceBinder;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        //mServiceLooper = thread.getLooper();
        mServiceHandler = new Handler();
        //mReceiver = new BluetoothReceiver();
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        // Enable Bluetooth Adapter
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();

        // Begin discovery loop.
        startScan();

    }
    /* Code for BluetoothLE Connections
    *
    *
    *
     */
    /*
    *  LE Scan Callback
    *
    * */
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        // Increase if period is too low
        /*
        * Callback that fires upon device discovery
        *
        * */
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            final String TAG = "LE Scan Callback";
            Log.i(TAG, "New LE Device: " + device.getName() + " @ " + rssi);
            // Will request a static factory next time.
            Bluetooth bluetooth = new Bluetooth(device);
            BluetoothGattCallback callback = new GattCallback(bluetooth);
            BluetoothGatt gatt = device.connectGatt(BeaconService.this, true, callback);
            beacons.add(bluetooth, gatt);
        }

    };

    /*
    * Starts and restarts scan.
    *
    * */
    private final long PULSE_HALF_PERIOD = 500;

    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan();
        }
    };

    private Runnable mStartRunnable = new Runnable () {
        @Override
        public void run() {
            startScan();
        }
    };
    // Initiate a discovery command to get the signal strength and determine

    private void startScan() {
        mBluetoothAdapter.startDiscovery();
        // Stop after 2500 ms
        mServiceHandler.postDelayed(mStopRunnable, PULSE_HALF_PERIOD);
    }

    private void stopScan() {
        mBluetoothAdapter.cancelDiscovery();

        // Start after 2500ms
        mServiceHandler.postDelayed(mStartRunnable, PULSE_HALF_PERIOD);
    }



    // All Gatt Communications functionality defined here.
    // Maybe this should be a public class
    /*
    * This will be unnecessary since you have one callback per device.
    *
    * */
    /*
    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
        }
        // Code when the
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {

        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {

        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            // Put RSSI update code here
            super.onReadRemoteRssi(gatt, rssi, status);

        }

    }; //*/

    /* Returned on bind
    *  These methods are exposed when the service is bound.
    */
    private final IBeaconService.Stub mBeaconServiceBinder = new IBeaconService.Stub() {
        private HashMap<String, IBeaconServiceCallback> callbacks = new HashMap<>();

        @Override
        public void lastSnap(IBeaconServiceCallback callback) throws RemoteException {
        }

        @Override
        public void signalsStrength(IBeaconServiceCallback callback) throws RemoteException {
            // Redesign beacon to take a bluetooth device and connect to gatt
            callback.signalsResponse(beacons.getSignalData());
        }

        @Override
        public void registerCallback(IBeaconServiceCallback callback) throws RemoteException {
            callbacks.put(Integer.toString(callback.hashCode()), callback);
        }

        @Override
        public void unregisterCallback(IBeaconServiceCallback callback) throws RemoteException {
            callbacks.remove(Integer.toString(callback.hashCode()));
        }
    };

    // Create a private implementation to gattcallback then pass a new copy of for each



    @Override
    public void onDestroy() {
        mBluetoothAdapter.cancelDiscovery();
        //Toast.makeText(this, "Beacon Service Done", Toast.LENGTH_SHORT).show();
    }
    /* Private methods
    *
    * */

    // Create a snapshot to use
    /*
    private void createSnapshot() {
        snapshots.add(new Snapshot(beacons));
    }

    // Remove the oldest snapshot
    private void purgeSnapshot() {
        snapshots.remove(0);
    }


    /* Client methods
    * Methods that should be accessible by from the service.
    * */
/*
    // Get snapshot within the given time
    public Snapshot snapshot(int thresholdUpper, int thresholdLower) {
        return null;
    }

    // Get the latest snapshot
    public Snapshot lastSnapshot() {
        if (snapshots != null && snapshots.size() > 0) {
            return snapshots.remove(snapshots.size() - 1);
        }
        return null;
    }

*/



    /* Broadcast discovery handler
     * Must exist in an activity class. 11-05-17
     * Deprecated 01-06-17
     */
    /* Use Gatt receiver instead.
    private class BluetoothReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Discovery

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If device not found.
                if (device != null) {
                    // Check if the device exists
                    if (beacons.findBeacon(device) != null) {
                        beacons.findBeacon(device).setSignalStrength(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                    } else {
                        beacons.add(new BeaconCreateDescription(device));
                    }

                }
            }
            // Broadcast Action detected.
            if(BluetoothDevice.ACTION_UUID.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.ACTION_UUID);

                // Set the RSSI
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                beacons.findBeacon(device.getAddress().toString()).setSignalStrength(rssi);
            }
        }
    };


}
*/
}