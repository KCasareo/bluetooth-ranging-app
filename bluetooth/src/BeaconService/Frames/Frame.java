package BeaconService.Frames;

import BeaconService.Beacons.Beacons;

import java.util.Map;

/**
 * Created by Kevin on 6/05/2017.
 */
public class Frame {
    private int number;
    private Map<String, Integer> signal_strength;

    // Store the signal strength for each beacon
    public Frame(Map<String,Integer> signal_strength, int number) {
        this.signal_strength = signal_strength;
        this.number = number;
        // This sounds like a bad idea.
        /*
        number = Frames.frameCount;
        Frames.frameCount++;
        */
    }
}
