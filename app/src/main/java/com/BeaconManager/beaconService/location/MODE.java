package com.BeaconManager.beaconService.location;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 12/10/2017.
 */
public enum MODE implements Parcelable {
    TWO,
    THREE;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MODE> CREATOR = new Creator<MODE>() {
        @Override
        public MODE createFromParcel(Parcel in) {
            return MODE.values()[in.readInt()];
        }

        @Override
        public MODE[] newArray(int size) {
            return new MODE[size];
        }
    };
}