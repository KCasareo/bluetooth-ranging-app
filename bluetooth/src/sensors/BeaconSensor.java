package sensors;

/**
 * Created by Kevin on 30/04/2017.
 */
public interface BeaconSensor {
    /**
    *  These methods are to be implemented by concrete, non abstract classes. This would ensure that all types of sensors must have these methods, not just Bluetooth and not just Wifi
    * */
    public void send();
    public void receive();
    public double signalStrength();
}
