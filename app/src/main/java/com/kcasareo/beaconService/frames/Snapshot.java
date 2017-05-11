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
    public static final int MAX_REFRESH_TIME = 500;


    public Snapshot(Beacons beacons) {
        this.beacons = beacons;
        this.frames = new Frames(beacons);
        long currentTime = System.currentTimeMillis();
        //
        Timer timer = new Timer();
        timer.schedule(frames, 0);
        while (System.currentTimeMillis() < currentTime + MAX_REFRESH_TIME) { ;}
        timer.cancel();
    }


    public Frames frames() {
        return this.frames;
    }
}
