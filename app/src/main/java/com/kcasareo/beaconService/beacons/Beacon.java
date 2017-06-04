package com.kcasareo.beaconService.beacons;

import android.bluetooth.BluetoothGattCallback;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kevin on 4/06/2017.
 * An extensible beacon class for connecting to a source.
 */

public abstract class Beacon {
    protected long signalStrength;
    protected String id;
    public abstract long signalStrength();
    public abstract void setSignalStrength(long rssi);
    public abstract String id();

    /*
    * Returns an exportable parcel.
    *
    * */
    public Data exportData() {
        return new Data(signalStrength);
    }

    public Beacon() { signalStrength = 0; }


    public class Data implements Parcelable {
        private long rssi;
        protected Data(Parcel in) {
            rssi = in.readLong();
        }


        public Data(long rssi) {
            this.rssi = rssi;
        }

        public final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.rssi);
        }
    }
}
