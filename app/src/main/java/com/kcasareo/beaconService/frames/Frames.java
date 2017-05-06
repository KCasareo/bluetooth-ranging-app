package com.kcasareo.beaconService.frames;

import com.kcasareo.beaconService.Beacons.Beacons;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by Kevin on 6/05/2017.
 */
public class Frames implements Runnable {
    private ArrayList<Frame> frames;
    private Beacons beacons;
    public static int frameCount;
    public Semaphore semaphore;

    public Frames(Beacons beacons) {
        this.frames = new ArrayList<>();
        this.beacons = beacons;
        frameCount = 0;
    }

    // Add a new frame.
    public void update() {
        // Adds a new frame. beacons.signalStrength() is blocking for this function.
        this.frames.add(new Frame(beacons.signalStrength(), frameCount));
        frameCount++;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                update();
            } catch (InterruptedException e) {
                
            } finally {
                semaphore.release();
            }
        }
    }


    // Return the frames from between the start and end frames


}
