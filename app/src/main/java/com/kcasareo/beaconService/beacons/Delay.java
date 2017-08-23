package com.kcasareo.beaconService.beacons;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.time.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kevin on 13/08/2017.
 */

public class Delay implements Parcelable {
    private long start;
    private long end;
    //private Calendar calendar = Calendar.getInstance();
    public Delay() {
        startOffset();
    }

    protected Delay(Parcel in) {
        start = in.readLong();
        end = in.readLong();
    }

    public Delay(long start, long end) {
        this.start = start;
        this.end = end;
    }


    public static final Creator<Delay> CREATOR = new Creator<Delay>() {
        @Override
        public Delay createFromParcel(Parcel in) {
            return new Delay(in);
        }

        @Override
        public Delay[] newArray(int size) {
            return new Delay[size];
        }
    };

    public void startOffset() {
        start = TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

    public void endOffset() {
        end = TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

    public long period() {
        return end - start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(start);
        dest.writeLong(end);
    }

}
