package beaconService.Beacons;

import android.bluetooth.BluetoothDevice;
import beaconService.Beacons.Bluetooth.BEACON_TYPE;
import beaconService.Beacons.Bluetooth.BLUETOOTH_TYPE;

/**
 * Created by Kevin on 6/05/2017.
 *  Public Description Type to be used when creating beacon state objects.
 */
public class BeaconCreateDescription {
    private BLUETOOTH_TYPE bluetooth_type;
    private BEACON_TYPE beacon_type;
    private BluetoothDevice device;
    private String id;

    // Default creation message, needs all types defined.
    public BeaconCreateDescription (BEACON_TYPE beacon_type, BLUETOOTH_TYPE bluetooth_type, String id){
        this.beacon_type = beacon_type;
        this.bluetooth_type = bluetooth_type;
        this.id = id;
    }

    // Bluetooth creation.
    public BeaconCreateDescription(BluetoothDevice device) {
        // Handle type changes based on what is advertised.
        beacon_type = BEACON_TYPE.BEACON_BLUETOOTH;
        bluetooth_type = BLUETOOTH_TYPE.NONE;
        this.device = device;
        this.id = device.getAddress();
    }

    public BLUETOOTH_TYPE bluetooth_type() {
        return bluetooth_type;
    }

    public BEACON_TYPE beacon_type() {
        return beacon_type;
    }

    public BluetoothDevice device() { return device; }

    public String id() { return this.id; }
}

