package BeaconService.Beacons.Bluetooth;

import BeaconService.Beacons.Beacon;
import android.bluetooth.BluetoothDevice;

/**
 * Created by Kevin on 5/05/2017.
 */
public abstract class Bluetooth extends Beacon {
    protected String id;
    protected int signalStrength;

    public Bluetooth(String id) {
        this.id = id;
    }
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String id() {
        return null;
    }

}
