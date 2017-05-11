package com.kcasareo.beaconService.frames;

import com.kcasareo.beaconService.Beacons.Beacons;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * Created by Kevin on 6/05/2017.
 * TimerTask so it doesn't
 */


public class Frames extends TimerTask {

    private ArrayList<Frame> frames;
    private Beacons beacons;
    private int frameCount;
    public Semaphore semaphore;

    public Frames(Beacons beacons) {
        this.frames = new ArrayList<>();
        this.beacons = beacons;
        frameCount = 0;
    }

    public int getFrameCount() {
        return frameCount;
    }

    // Add a new frame.
    @Override
    public void run() {
        while (true) {
            this.frames.add(new Frame(beacons.signalStrength(), frameCount));
            frameCount++;
        }
    }
}


    // Return the frames from between the start and end frames

