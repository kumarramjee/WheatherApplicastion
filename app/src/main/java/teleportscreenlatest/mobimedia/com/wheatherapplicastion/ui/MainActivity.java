package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.GooglePlacesAutocompleteAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.GpsLocation;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherDetailImage;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.Locationfinder;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private AutoCompleteTextView city;
    private String mcityname = "";
    Context activity;
    int x;
    RelativeLayout rootlayot;
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
    Handler mhandler;
    TextView mtxt_Title;
    ImageView mback_navigation;
    TextView mtxt_Next;
    TextView mcityField;
    String updatedOn;
    Resources res;
    RelativeLayout rLayout;
    Drawable drawable;
    WeatherDetailImage mdetailweatherimage;
    Locationfinder mlocationfinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SetupToolbar();
        SetUpUI();
        mlocationfinder = new Locationfinder();
        mdetailweatherimage = new WeatherDetailImage(mContext);
        CityName = mlocationfinder.getCurrentLOcationName(mContext);
        mhandler = new Handler();
        res = getResources();
        GpsLocation getgps = new GpsLocation();
        getgps.turnGPSOn();
        GetDetailWeatherDetail(CityName);

        submit.setOnClickListener(this);
        txt_Next.setOnClickListener(this);
        rLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (rootlayot.VISIBLE == View.VISIBLE) {
                    rootlayot.setVisibility(View.GONE);
                }
                //   else if()

                return true;
            }
        });

        city.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x = position;
            }
        });
    }

    private void GetDetailWeatherDetail(String cityName) {
        if (cityName.length() == 0) {
            Toast.makeText(MainActivity.this, "Not able to find current location.Chechk ur connection", Toast.LENGTH_SHORT).show();
        } else {


            new Thread() {
                public void run() {
                    final JSONObject json = FetchJson.getJSON(mContext, CityName);
                    if (json == null) {
                        mhandler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(mContext,
                                        "Try after some timee.",
                                        Toast.LENGTH_LONG).show();
                                drawable = res.getDrawable(R.drawable.skyclear);
                                rLayout.setBackground(drawable);
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
                    String.format("%.2f", main.getDouble("temp")) + " ℃");
            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            mupdatedField.setText("Last update: " + updatedOn);
        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setImage(String description) {
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
        } else if (description.equals("HAZE")) {
            drawable = res.getDrawable(R.drawable.haze);
            rLayout.setBackground(drawable);

        } else if (description.equals("THUNDERSTROM WITH HEAVY RAIN")) {
            drawable = res.getDrawable(R.drawable.thunderwithrain);
            rLayout.setBackground(drawable);

        } else if (description.equals("MIST")) {
            drawable = res.getDrawable(R.drawable.mist);
            rLayout.setBackground(drawable);

        } else {
            drawable = res.getDrawable(R.drawable.nonelse);
            rLayout.setBackground(drawable);

        }

    }

    private void SetUpUI() {
        submit = (Button) findViewById(R.id.submit);
        city = (AutoCompleteTextView) findViewById(R.id.edittextplace);
        rootlayot = (RelativeLayout) findViewById(R.id.rootlayot);
        rootlayot.setVisibility(View.INVISIBLE);
        rLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        mcityField = (TextView) findViewById(R.id.city_field1);
        mupdatedField = (TextView) findViewById(R.id.updated_field1);
        mdetailsField = (TextView) findViewById(R.id.details_field1);
        mcurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field1);

    }

    private void SetupToolbar() {
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Title.setText("Weather Detail");
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setText("+");
        txt_Next.setTextSize(30);
        mtoolbar = (Toolbar) findViewById(R.id.customActionbar);

        ImageView back_navigation = (ImageView) findViewById(R.id.back_navigation);
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
                    //GetDetailWeatherDetail(mcityname);
                    Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
                    intent_submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_submit.putExtra("CitytoDetail", mcityname);
                    startActivity(intent_submit);
                }
                break;
            case R.id.txt_Next:
                rootlayot.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


}