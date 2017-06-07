package com.kcasareo.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import com.kcasareo.beaconService.beacons.Beacon;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalData implements Parcelable {
    private HashMap<String, SignalDatum> signalData;

    protected SignalData(Parcel in) {
        int size = in.readInt();
        signalData = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String name = in.readString();
            SignalDatum datum = in.readParcelable(SignalDatum.class.getClassLoader());
            signalData.put(name, datum);
        }
    }

    public SignalData() {
        signalData = new HashMap<>();
    }

    public void add(SignalDatum datum) {
        signalData.put(datum.name(), datum);
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
        int size = signalData.size();
        dest.writeInt(size);
        for ( HashMap.Entry<String, SignalDatum> entry : signalData.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), 0);
        }
    }

    public HashMap<String, SignalDatum> asMap() {
        return signalData;
    }

    public void add(Beacon beacon) {
        signalData.put(beacon.address(), beacon.datum());
    }

}
