package BeaconService.Beacons.Bluetooth;

/**
 * Created by Kevin on 5/05/2017.
 */
public class DynamicBluetooth extends Bluetooth {
    public DynamicBluetooth(String id) {
        super(id);
    }

    public int signal_strength() {
        return signalStrength;
    }


    @Override
    public void run() {
        super.run();
        while(true) {
            // Lock while signal strength is being updated.
            try {
                semaphore.acquire();
                this.update();
                semaphore.release();
            }
            catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    @Override
    protected void update() {
        // Change code later
        signalStrength++;
    }
}
