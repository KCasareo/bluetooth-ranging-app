package com.kcasareo.beaconService.frames;

import com.kcasareo.beaconService.beacons.Beacons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by Kevin on 6/05/2017.
 * TimerTask so it doesn't
 */


public class Frames extends TimerTask {

    private ArrayList<Frame> frames;
    private Beacons beacons;
    private int frameCount;
    //public Semaphore semaphore;

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
        if (beacons != null) {
            this.frames.add(new Frame(beacons.signalStrength(), frameCount));
            frameCount++;
        }
    }

    public void sort() {
        Collections.sort(frames);
    }

    public List<Frame> getList() {
        return this.frames;
    }

    public Frame getLast() { return this.frames.get(this.frames.size() - 1); }
}


    // Return the frames from between the start and end frames

