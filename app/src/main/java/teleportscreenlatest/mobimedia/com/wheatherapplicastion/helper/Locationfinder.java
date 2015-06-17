package teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui.MainActivity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.MyLocationListener;

/**
 * Created by ram on 15/6/15.
 */
public class Locationfinder {
    Context mContext;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    Location location;
    Double MyLat, MyLong;
    String CityName = "";
    String StateName = "";
    String CountryName = "";

    public String getCurrentLOcationName(Context mContext) {


        LocationManager locManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        //don't start listeners if no provider is enabled

        //if(!gps_enabled && !network_enabled)

        //return false;

        if (gps_enabled) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

        }


        if (gps_enabled) {
            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        if (network_enabled && location == null) {

            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        }
        if (network_enabled && location == null) {
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

        if (location != null) {

            MyLat = location.getLatitude();
            MyLong = location.getLongitude();
        } else {
            Location loc= getLastKnownLocation(this);
            if (loc != null) {
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
            }
        }
        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to avoid battery drainage. If you want to get location at the periodic intervals call this method using pending intent.

        try


        {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(mContext, Locale.getDefault());
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
            StateName = addresses.get(0).getAdminArea();
            CityName = addresses.get(0).getLocality();
            CountryName = addresses.get(0).getCountryName();
            Log.i("Location State", " StateName==" + StateName);
            Log.i("Location CityName", " CityName==" + CityName);
            Log.i("Location CountryName", " CountryName==" + CountryName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CityName;
    }

    private Location getLastKnownLocation(Locationfinder locationfinder) {



        return null;
    }
}
