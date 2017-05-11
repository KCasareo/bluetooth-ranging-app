package com.kcasareo.beaconService.frames;


import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Kevin on 6/05/2017.
 */
public class Frame implements Comparable<Frame> {
    private Integer frameNumber;
    // ID, signalStrength
    private Map<String, Integer> signalStrength;

    // Store the signal strength for each beacon
    public Frame(Map<String,Integer> signalStrength, int frameNumber) {
        this.signalStrength = signalStrength;
        this.frameNumber = frameNumber;
    }

    public int getFrameNumber() { return frameNumber; }

    // To allow sorting.
    @Override
    public int compareTo(@NonNull Frame o) {
        return frameNumber.compareTo(o.getFrameNumber());
    }
}
