package beaconService.Beacons.Bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Kevin on 5/05/2017.
 * This class may become just a data structure to store changes in RSSI
 */
public class DynamicBluetooth extends Bluetooth {
    private BluetoothDevice device;
    public DynamicBluetooth(BluetoothDevice device) {
        super(device.getAddress());
        this.device = device;

    }

    public int signalStrength() {
        return this.signalStrength;
    }
    /*
    // Not sure if I need this anymore.
    @Override
    public void run() {
        super.run();
        while(true) {
            // Lock while signal strength is being updated.
            try {
                semaphore.acquire();
                //this.update();
                semaphore.release();
            }
            catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
*/





}
