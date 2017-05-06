package BeaconService;

import BeaconService.Beacons.Beacons;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.widget.Toast;

/**
 * Created by Kevin on 5/05/2017.
 * Test code using the Android Developer guide
 */
public class BeaconService extends Service {
    private Beacons beacons;
    private final IBinder mBeaconServiceBinder = new BeaconServiceBinder();
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Put time-dependent code here

            stopSelf(msg.arg1);
        }
    }

    public class BeaconServiceBinder extends Binder {
        BeaconService getService () {
            return BeaconService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBeaconServiceBinder;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Beacon Service Starting", Toast.LENGTH_SHORT).show();

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Beacon Service Done", Toast.LENGTH_SHORT).show();
    }

    /* Client methods
    *
    * */




}
