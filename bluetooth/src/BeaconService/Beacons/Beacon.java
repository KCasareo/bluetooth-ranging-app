package BeaconService.Beacons;

import BeaconService.BeaconService;
import android.os.*;

import java.util.concurrent.Semaphore;

/**
 * Created by Kevin on 5/05/2017.
 * Interface class for Beacon state objects.
 * EXTEND THIS TO ADD YOUR BEACON TYPE
 */
public abstract class Beacon implements Runnable {
    public abstract int signal_strength();
    public abstract String id();
    public abstract void update();

    // Locks the write semaphore.
    public Semaphore semaphore;

    // Locks all other semaphores
    // public Semaphore writeSemaphore;

    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }


}
