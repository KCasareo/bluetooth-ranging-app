package application;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import navigation.Navigator;

/**
 * Created by Kevin on 30/04/2017.
 */
public class NavigationService extends Service {
    private Navigator navigator;

    public NavigationService(Navigator navigator) {
        this.navigator = navigator;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
