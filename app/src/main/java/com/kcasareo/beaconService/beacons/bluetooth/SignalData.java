package com.kcasareo.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalData implements Parcelable {
    private HashMap<String, SignalDatum> signalData;


    protected SignalData(Parcel in) {
        signalData = new HashMap<>();
        signalData = (HashMap<String, SignalDatum>) in.readSerializable();
    }

    public static final Creator<SignalData> CREATOR = new Creator<SignalData>() {
        @Override
        public SignalData createFromParcel(Parcel in) {
            return new SignalData(in);
        }

        @Override
        public SignalData[] newArray(int size) {
            return new SignalData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(signalData);
    }

    
}
