package navigation;

import sensors.BluetoothConnections;
import sensors.Estimote;


/**
 * Created by Kevin on 30/04/2017.
 */
public class Navigator {
    private BluetoothConnections connections;

    public Navigator(BluetoothConnections connections) {
        this.connections = connections;
    }
}
