package BeaconService.Beacons;

import BeaconService.Beacons.Bluetooth.BluetoothFactory;
import android.hardware.Sensor;

/**
 * Created by Kevin on 5/05/2017.
 */



public class BeaconFactory {
    // Private constructor, ensure only static factory methods.
    private BeaconFactory(BluetoothFactory bluetoothFactory) {
        //this.bluetoothFactory = bluetoothFactory;
    }

    public static Beacon create(BeaconCreateDescription description) {
        switch (description.beacon_type()) {
            case BEACON_WIFI:
                return null;
            case BEACON_BLUETOOTH:
                return BluetoothFactory.create(description);
            default:
                return null;
        }
    }
}
