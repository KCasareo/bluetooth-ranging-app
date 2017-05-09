package com.kcasareo.beaconService.Beacons;

import android.content.BroadcastReceiver;

import java.util.concurrent.Semaphore;

/**
 * Created by Kevin on 5/05/2017.
 * Interface class for Beacon state objects.
 * EXTEND THIS TO ADD YOUR BEACON TYPE
 */
public abstract class Beacon {
    //public abstract int signalStrength();
    public abstract String id();
    private static final int MAX_PERMIT = 1;
    protected BroadcastReceiver receiver;

    public int signalStrength;
    //protected abstract void update();

    // Locks the semaphore so neither read nor write occur at the same time.
    // If the reading thread happens to attempt acquisition, it will not miss a chance to read.
    public final Semaphore semaphore = new Semaphore(MAX_PERMIT, true);


    /* Called with super to turn all runnable beacon threads to background processes.
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }

    //*/

}
