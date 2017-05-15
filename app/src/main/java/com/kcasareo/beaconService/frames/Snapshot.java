package com.kcasareo.beaconService.frames;

import android.os.Parcel;
import android.os.Parcelable;

import com.kcasareo.beaconService.beacons.Beacons;
import java.util.Timer;

//import static android.content.ComponentName.readFromParcel;

/**
 * Created by Kevin on 6/05/2017.
 * Definition for a snapshot of frames for a period of time.
 */
public class Snapshot implements Parcelable {
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

    protected Snapshot(Parcel in) {
        readFromParcel(in);
        // We only want the frame object and nothing else.
        this.beacons = null;
        this.thread = null;
    }

    public void readFromParcel(Parcel in) {
        frames = (Frames) in.readValue(Frames.class.getClassLoader());
    }

    public static final Creator<Snapshot> CREATOR = new Creator<Snapshot>() {
        @Override
        public Snapshot createFromParcel(Parcel in) {
            return new Snapshot(in);
        }

        @Override
        public Snapshot[] newArray(int size) {
            return new Snapshot[size];
        }
    };

    public Frames frames() {
        return this.frames;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(frames);

    }
}
