package com.kcasareo.location;
import com.kcasareo.location.ILocationService.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Kevin on 23/08/2017.
 */

public class LocationService extends Service {

    private final ILocationService.Stub mLocationServiceBinder = new ILocationService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
