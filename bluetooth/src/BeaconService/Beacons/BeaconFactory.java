package BeaconService.Beacons;

import BeaconService.Beacons.Bluetooth.BluetoothFactory;
import android.hardware.Sensor;

/**
 * Created by Kevin on 5/05/2017.
 */
public class BeaconFactory {
    private BluetoothFactory bluetoothFactory;

    public BeaconFactory(BluetoothFactory bluetoothFactory) {
        this.bluetoothFactory = bluetoothFactory;
    }

    public Sensor create(SensorCreateDescription description) {
        return null;
    }
}
