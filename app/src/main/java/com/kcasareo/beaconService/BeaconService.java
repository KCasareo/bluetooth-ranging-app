package com.kcasareo.beaconService;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.widget.Toast;
import com.kcasareo.beaconService.beacons.BeaconCreateDescription;
import com.kcasareo.beaconService.beacons.Beacons;
import com.kcasareo.beaconService.frames.Snapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.kcasareo.beaconService.frames.Snapshot.MAX_REFRESH_TIME;


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
    // Use a thread safe list;
    private List<Snapshot> snapshots = Collections.synchronizedList(new ArrayList<Snapshot>());
    private BluetoothAdapter adapter;
    private Timer snapshotScheduler;
    private final int MAX_SNAPSHOTS_HELD = 10;

    /* Messages for the service handler
    *
    * */
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

    }

    /* Message handler for BeaconService
    *
    * */
    private final class ServiceHandler extends Handler {
        /* Manual message handling?
        *  Don't know if I want this yet.
        *
        * */
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
    }

    public class BeaconServiceBinder extends Binder {
        public BeaconService getService () {
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

        snapshotScheduler = new Timer();
        // Every 500ms create a new position snapshot.
        snapshotScheduler.scheduleAtFixedRate(new TimerTask () {
            @Override
            public void run() {
                // Will block the task for 500 ms
                snapshots.add(new Snapshot(beacons));
                // Remove the earliest snapshot added.
                while(snapshots.size() > MAX_SNAPSHOTS_HELD) {
                    snapshots.remove(0);
                }
            }
        }, 0, MAX_REFRESH_TIME);


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
        snapshotScheduler.cancel();

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

    // Get the latest snapshot
    public Snapshot lastSnapshot() { return snapshots.remove(snapshots.size() - 1);}

    // Initiate a discovery command to get the signal strength and determine
    public void ping() {
        adapter.startDiscovery();
    }

    public void cancel() { adapter.cancelDiscovery(); }


    /* Broadcast discovery handler
     * Must exist in an activity class. 11-05-17
     */
    private class BluetoothReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Discovery

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If device not found.
                if (beacons.findBeacon(device.getUuids().toString()) == null) {
                    // Get a bluetooth device and create an object to handle it.
                    beacons.add(new BeaconCreateDescription(device));
                }

            }
            // Broadcast Action detected.
            if(BluetoothDevice.ACTION_UUID.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.ACTION_UUID);

                // Set the RSSI
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                beacons.findBeacon(device.getUuids().toString()).setSignalStrength(rssi);
            }
        }
    };
}
