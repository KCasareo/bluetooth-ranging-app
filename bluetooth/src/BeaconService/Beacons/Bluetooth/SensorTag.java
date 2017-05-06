package BeaconService.Beacons.Bluetooth;

/**
 * Created by Kevin on 5/05/2017.
 */
public class SensorTag extends Bluetooth {

    public SensorTag (String id) {
        super(id);
    }

    @Override
    public int signal_strength() {
        return 0;
    }

    @Override
    public String id() { return null; }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void update() {

    }
}
