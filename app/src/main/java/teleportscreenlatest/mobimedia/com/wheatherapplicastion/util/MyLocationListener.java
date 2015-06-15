package teleportscreenlatest.mobimedia.com.wheatherapplicastion.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by ram on 15/6/15.
 */
public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}