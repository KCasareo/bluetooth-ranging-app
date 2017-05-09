package beaconService.Frames;

import java.util.Map;

/**
 * Created by Kevin on 6/05/2017.
 */
public class Frame {
    private int number;
    // ID, signalStrength
    private Map<String, Integer> signalStrength;

    // Store the signal strength for each beacon
    public Frame(Map<String,Integer> signalStrength, int number) {
        this.signalStrength = signalStrength;
        this.number = number;
    }
}
