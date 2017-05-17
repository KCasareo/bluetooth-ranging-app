package com.kcasareo.application;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.kcasareo.beaconService.BeaconService;
import com.kcasareo.beaconService.IBeaconService;
import com.kcasareo.beaconService.IBeaconServiceCallback;
import com.kcasareo.beaconService.frames.Snapshot;

/**
 * Created by Kevin on 30/04/2017.
 * Main Activity is the user interface that uses and displays results from the Navigation Service
 */
public class MainActivity extends Activity {
    private BeaconService beaconService;
    private IBeaconService mBeaconService = null;

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


    private IBeaconServiceCallback mCallback = new IBeaconServiceCallback.Stub() {

        @Override
        public void valueChanged(int value) throws RemoteException {

        }

        @Override
        public void handleResponse(Snapshot snapshot) throws RemoteException {
            
        }

    };



    private ServiceConnection beaconServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBeaconService = IBeaconService.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}