package beaconService.Frames;

import beaconService.Beacons.Beacons;

import java.util.ArrayList;

/**
 * Created by Kevin on 6/05/2017.
 */
public class Frames {
    private ArrayList<Frame> frames;
    private Beacons beacons;
    public static int frameCount;

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



    // Return the frames from between the start and end frames


}
