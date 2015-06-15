package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.CityAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.beans.CityResult;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.ParserCity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.MyLocationListener;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private AutoCompleteTextView city;
    private String mcityname;
    Context activity;
    ArrayList<CityResult> cityResultList;
    CityAdapter adpt;
    String msendcityname;
    int x;
    Context mContext = this;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    Location location;
    Double MyLat, MyLong;
    String CityName = "";
    String StateName = "";
    String CountryName = "";
    private TextView mdetailsField;
    private TextView mcurrentTemperatureField;
    private TextView mupdatedField;
    private TextView mweatherIcon;
    Handler mhandler;
    private TextView mtxt_Title;
    private ImageView mback_navigation;
    private TextView mtxt_Next;
    private TextView mcityField;
    String updatedOn;
    Resources res;
    RelativeLayout rLayout;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupToolbar();
        SetUpUI();

        CityName = getCurrentLOcationName();
        mhandler = new Handler();

        turnGPSOn(); // method to turn on the GPS if its in off state.

        Log.i("Crrent location is::", "Main aactivity==" + CityName);


        GetDetailWeatherDetail(CityName);


        //getFragmentManager().beginTransaction().add(R.id.container, new CurrentlocationFragement()).commit();
        submit.setOnClickListener(this);

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                msendcityname = city.getText().toString().trim();
                new Thread(new Runnable() {
                    public void run() {
                        ParserCity parscity = new ParserCity();
                        parscity.setText(msendcityname);

                        try {
                            cityResultList = parscity.getCityList();

                            Log.i("cityActivity", "==" + cityResultList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }

                    }


                }).start();

                adpt = new CityAdapter(getApplicationContext(), cityResultList);
                city.setAdapter(adpt);
                adpt.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable s) {

              /*  Timer timer = new Timer();
                final long DELAY = 500; // in ms

                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                         // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                    }

                }, DELAY);*/
                }
        });


        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x = position;


            }
        });
    }

    private void GetDetailWeatherDetail(String cityName) {


        new Thread() {
            public void run() {
                final JSONObject json = FetchJson.getJSON(mContext, CityName);
                Log.i("MAin Activity", "jsom==" + json);

                if (json == null) {
                    mhandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext,
                                    "NOT FIND",
                                    Toast.LENGTH_LONG).show();

                            //    drawable = res.getDrawable(R.drawable.skyclear);
                            //  rLayout.setBackground(drawable);
                        }
                    });
                } else {
                    mhandler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            mcityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            mdetailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            setImage(details.getString("description").toUpperCase(Locale.US));


            mcurrentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp")) + " ?");

            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            mupdatedField.setText("Last update: " + updatedOn);
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setImage(String description) {
        {


            if (description.equals("SKY IS CLEAR")) {
                drawable = res.getDrawable(R.drawable.skyclear);
                rLayout.setBackground(drawable);

            } else if (description.equals("OVERCAST CLOUDS")) {
                drawable = res.getDrawable(R.drawable.overcatclouds);
                rLayout.setBackground(drawable);

            } else if (description.equals("FEW CLOUDS")) {
                drawable = res.getDrawable(R.drawable.fewclouds);
                rLayout.setBackground(drawable);

            } else if (description.equals("MODERATE RAIN")) {
                drawable = res.getDrawable(R.drawable.moderaterain);
                rLayout.setBackground(drawable);

            } else if (description.equals("LIGHT RAIN")) {
                drawable = res.getDrawable(R.drawable.lihjtrain);
                rLayout.setBackground(drawable);

            } else if (description.equals("BROKEN CLOUDS")) {
                drawable = res.getDrawable(R.drawable.brokenclouds);
                rLayout.setBackground(drawable);

            } else if (description.equals("SCATTERED CLOUDS")) {
                drawable = res.getDrawable(R.drawable.scateredclouds);
                rLayout.setBackground(drawable);

            } else {
                drawable = res.getDrawable(R.drawable.nonelse);
                rLayout.setBackground(drawable);

            }
        }


    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = mContext.getString(R.string.weather_sunny);
            } else {
                icon = mContext.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = mContext.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = mContext.getString(R.string.weather_drizzle);

                    break;
                case 7:
                    icon = mContext.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = mContext.getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = mContext.getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = mContext.getString(R.string.weather_rainy);
                    break;
            }
        }
        //mweatherIcon.setText(icon);
    }


    private void turnGPSOn() {
        try {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } catch (Exception e) {

        }
    }

    private String getCurrentLOcationName() {


        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        } /*else {
            Location loc= getLastKnownLocation(this);
            if (loc != null) {
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
            }
        }
*/
        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to avoid battery drainage. If you want to get location at the periodic intervals call this method using pending intent.

        try


        {
// Getting address from found locations.
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
            StateName = addresses.get(0).getAdminArea();
            CityName = addresses.get(0).getLocality();
            CountryName = addresses.get(0).getCountryName();
            // you can get more details other than this . like country code, state code, etc.
            System.out.println(" StateName " + StateName);
            System.out.println(" CityName " + CityName);
            System.out.println(" CountryName " + CountryName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CityName;
    }

    private void SetUpUI() {
        submit = (Button) findViewById(R.id.submit);
        city = (AutoCompleteTextView) findViewById(R.id.edittextplace);


        rLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        mcityField = (TextView) findViewById(R.id.city_field1);
        mupdatedField = (TextView) findViewById(R.id.updated_field1);
        mdetailsField = (TextView) findViewById(R.id.details_field1);
        mcurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field1);
        mweatherIcon = (TextView) findViewById(R.id.weather_icon1);


    }

    private void SetupToolbar() {
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Title.setText("Weather Detail");
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setVisibility(View.INVISIBLE);
        mtoolbar = (Toolbar) findViewById(R.id.customActionbar);

        ImageView back_navigation = (ImageView) findViewById(R.id.back_navigation);
        //back_navigation.setImageResource(R.mipmap.weather);
        back_navigation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                mcityname = city.getText().toString();
                if ((mcityname.length() == 0) || (mcityname == null)) {

                    Toast.makeText(this, "Enter City Name", Toast.LENGTH_SHORT).show();
                } else {
                    //  GetDetailWeatherDetail(mcityname);
                    Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
                    intent_submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_submit.putExtra("CitytoDetail", mcityname);
                    startActivity(intent_submit);
                }
                break;
            default:
                break;
        }
    }


}