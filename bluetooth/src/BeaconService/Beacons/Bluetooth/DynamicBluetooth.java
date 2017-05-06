package BeaconService.Beacons.Bluetooth;

/**
 * Created by Kevin on 5/05/2017.
 */
public class DynamicBluetooth extends Bluetooth {
    public DynamicBluetooth(String id) {
        super(id);
    }

    public int signal_strength() {
        return 0;
    }

    @Override
    public void run() {
        super.run();
    }

    public void update() {

    }
}
