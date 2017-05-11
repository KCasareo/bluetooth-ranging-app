package com.kcasareo.beaconService.frames;

import com.kcasareo.beaconService.Beacons.Beacons;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 6/05/2017.
 * Definition for a snapshot of frames for a period of time.
 */
public class Snapshot {
    private Frames frames;
    private Thread thread;
    private Beacons beacons;
    public static final long MAX_REFRESH_TIME = 500;
    // Limit the number of frames to capture.
    private static final long FRAME_LIMIT = MAX_REFRESH_TIME / 10;


    public Snapshot(Beacons beacons) {
        this.beacons = beacons;
        this.frames = new Frames(beacons);
        long currentTime = System.currentTimeMillis();
        //
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(frames, 0, FRAME_LIMIT);
        while (System.currentTimeMillis() < currentTime + MAX_REFRESH_TIME) { ;}
        timer.cancel();
        // Make sure all the frames are sorted from least to first.
        frames.sort();
    }

    public Frames frames() {
        return this.frames;
    }
}
