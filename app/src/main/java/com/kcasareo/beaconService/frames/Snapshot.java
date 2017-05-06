package com.kcasareo.beaconService.frames;

import com.kcasareo.beaconService.Beacons.Beacons;

import java.util.Date;

/**
 * Created by Kevin on 6/05/2017.
 * Definition for a snapshot of frames for a period of time.
 */
public class Snapshot implements Runnable {
    private Frames frames;
    private Beacons beacons;
    private Date start;
    private static final int MAX_TIME = 500;


    public Snapshot(Beacons beacons) {
        this.beacons = beacons;
        this.frames = new Frames(beacons);

        // Get the time that this snapshot begins
        this.start = new Date();
    }
    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        // Call this object while 500s have not passed.
        while ( new Date().getTime() < start.getTime() + MAX_TIME ) {
            frames.update();
        }
    }
}
