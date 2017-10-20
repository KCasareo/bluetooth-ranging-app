package com.BeaconManager.beaconService.beacons.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import com.BeaconManager.beaconService.beacons.Beacon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.stream.Collectors;

/**
 * Created by Kevin on 4/06/2017.
 */

public class SignalData implements Parcelable {
    protected HashMap<String, SignalDatum> signalData;

    protected SignalData(Parcel in) {
        int size = in.readInt();
        signalData = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String name = in.readString();
            SignalDatum datum = in.readParcelable(SignalDatum.class.getClassLoader());
            signalData.put(name, datum);
        }
        // Order by closest.
        this.sort();
    }

    public SignalData() {
        signalData = new HashMap<>();
    }

    public void add(SignalDatum datum) {
        signalData.put(datum.address(), datum);
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

    public ArrayList<SignalDatum> asArray() {
        ArrayList<SignalDatum> array = new ArrayList<>();
        sort();
        for(Map.Entry<String, SignalDatum> entry : signalData.entrySet()) {
            array.add(entry.getValue());
        }
        return array;
    }

    public int size() {
        return signalData.size();
    }

    public HashMap<String, SignalDatum> asMap() {
        return signalData;
    }

    public void sort () {
        Set<Map.Entry<String, SignalDatum>> entries = signalData.entrySet();
        List<Map.Entry<String, SignalDatum>> listEntries = new ArrayList<Map.Entry<String, SignalDatum>>(entries);
        Collections.sort(listEntries, new Comparator<Map.Entry<String, SignalDatum>>() {
            @Override
            public int compare(Map.Entry<String, SignalDatum> o1, Map.Entry<String, SignalDatum> o2) {
                // Utilises SignalDatum.compareTo()
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        //LinkedHashMap<String, SignalDatum> sorted = new LinkedHashMap<>(listEntries.size());

        signalData.clear();
        for(Map.Entry<String, SignalDatum> entry : listEntries) {
            signalData.put(entry.getKey(), entry.getValue());
        }
    };

    public String toString() {
        String s = "";
        for (HashMap.Entry<String, SignalDatum> entry : signalData.entrySet()) {
            s += entry.getValue().toString() + " ";
        }
        return s;
    }

    public void add(Beacon beacon) {
        signalData.put(beacon.address(), beacon.datum());
    }

    public SignalDatum get(String address) {
        return signalData.get(address);
    }

}
