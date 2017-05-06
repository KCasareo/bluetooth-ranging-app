package BeaconService.Beacons;
import BeaconService.Beacons.Bluetooth.*;

/**
 * Created by Kevin on 6/05/2017.
 *  Public Description Type to be used when creating beacon state objects.
 */
public class BeaconCreateDescription {
    private BLUETOOTH_TYPE bluetooth_type;
    private BEACON_TYPE beacon_type;

    private String id;

    public BeaconCreateDescription (BEACON_TYPE beacon_type, BLUETOOTH_TYPE bluetooth_type, String id){
        this.beacon_type = beacon_type;
        this.bluetooth_type = bluetooth_type;
        this.id = id;
    }

    public BLUETOOTH_TYPE bluetooth_type() {
        return bluetooth_type;
    }

    public BEACON_TYPE beacon_type() {
        return beacon_type;
    }

    public String id() { return this.id; }
}

