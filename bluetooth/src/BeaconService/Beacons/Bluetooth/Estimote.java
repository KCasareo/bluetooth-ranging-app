package BeaconService.Beacons.Bluetooth;

/**
 * Created by Kevin on 5/05/2017.
 */
public class Estimote extends Bluetooth
{
    public Estimote(String id) {
        super(id);
    }

    @Override
    public int signal_strength() {
        return 0;
    }

    @Override
    public void run() {
        super.run();
        while(true) {

        }

    }

    public void update() {

    }
}
