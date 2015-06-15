package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toolbar;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.CityAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.beans.CityResult;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.GpsLocation;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherDetailImage;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.ParserCity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.Locationfinder;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private AutoCompleteTextView city;
    String mcityname = "";
    Context activity;
    ArrayList<CityResult> cityResultList;
    CityAdapter adpt;
    String msendcityname;
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
    private TextView mweatherIcon;
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
        rLayout.setOnClickListener(this);
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
        Log.i("Passing data to ", "Submit==City name is ==" + mcityname);

        new Thread() {
            public void run() {
                final JSONObject json = FetchJson.getJSON(mContext, CityName);
                Log.i("Passing data to ", "json data is ==" + json);

                if (json == null) {
                    mhandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext,
                                    "Current Location Not Found.Turn on Location or Check internet Connection.",
                                    Toast.LENGTH_LONG).show();
                            drawable = res.getDrawable(R.drawable.skyclear);
                            rLayout.setBackground(drawable);
                        }
                    });
                } else {
                    mhandler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                            // mdetailweather.renderWeather(json, mcityField, mdetailsField, mcurrentTemperatureField, mupdatedField);
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
                    String.format("%.2f", main.getDouble("temp")) + " ℃");
            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            mupdatedField.setText("Last update: " + updatedOn);
        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
        mweatherIcon = (TextView) findViewById(R.id.weather_icon1);

    }

    private void SetupToolbar() {
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Title.setText("Weather Detail");
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setText("+");
        txt_Next.setTextSize(25);
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
                    //   GetDetailWeatherDetail(mcityname);
                    Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
                    intent_submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_submit.putExtra("CitytoDetail", mcityname);
                    startActivity(intent_submit);
                }
                break;
            case R.id.txt_Next:
                rootlayot.setVisibility(View.VISIBLE);
                //ShowDialogForPlace();
                break;
            case R.id.rLayout:
                rootlayot.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void ShowDialogForPlace() {
        View view = getLayoutInflater().inflate(R.layout.enterlocation, null);
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        dialog.setView(view);

        dialog.show();


    }


}