package com.kcasareo.location;
import com.kcasareo.location.ILocationService.*;
import com.kcasareo.location.Position.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Kevin on 23/08/2017.
 */

public class LocationService extends Service {
    private Nodes nodes = new Nodes();
    private Position position;

    private final ILocationService.Stub mLocationServiceBinder = new ILocationService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public double distance(String UUID) {
            return nodes.get(UUID).position().range(position);
        }

        @Override
        public void add(double pos_x, double pos_y, String UUID) {
            nodes.add(new Node(new Position(pos_x, pos_y), UUID));
        }

        @Override
        public Position position() {
            return position;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
