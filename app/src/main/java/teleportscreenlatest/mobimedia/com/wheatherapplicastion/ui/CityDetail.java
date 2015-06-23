package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Location;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;

public class CityDetail extends Activity {
    TextView countryname;
    TextView dayType;
    String city = "";
    private static String forecastDaysNum = "16";
    String lang = "en";
    ListView citylist;
    Context mContext = this;
    private Handler mhandler;
    TextView dayname;
    TextView daytype, daytemp, daytempmax;
    String day = "", min = "", max = "", night = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        city = getIntent().getStringExtra("ForcastCityDetail");
        mhandler = new Handler();
        GetDetailWeatherDetail(city);
        SetUPUI();
    }

    private void SetUPUI() {
        dayname = (TextView) findViewById(R.id.dayname);
        daytype = (TextView) findViewById(R.id.daytype);
        daytemp = (TextView) findViewById(R.id.daytemp);
        daytempmax = (TextView) findViewById(R.id.daytempmax);
    }

    private void GetDetailWeatherDetail(final String city) {

        if (city.length() == 0) {
            Toast.makeText(mContext, "Not able to find current location.Check ur connection", Toast.LENGTH_SHORT).show();

        } else {
            new Thread() {
                public void run() {
                    final JSONObject json = FetchForcastForDayJson.getJSON(mContext, city);
                    Log.i("City Detail ", "Json Values:==" + json);

                    if (json == null) {
                        mhandler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(CityDetail.this, "Not Getting information.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mhandler.post(new Runnable() {
                            public void run() {


                                RenderWeatherDay(json);
                            }
                        });
                    }
                }
            }.start();
        }
    }

    private void RenderWeatherDay(JSONObject json) {
        Log.i("CityDetail Activtiy", "Detail Form is:==" + json);
        try {

            JSONObject City = json.getJSONObject("city");
            String Cityname = City.getString("name");

            String CountryName = City.getString("country");
            Log.i("City name is", "City name==" + Cityname);

            JSONArray ListArray = json.getJSONArray("list");
            Log.i("City Detail Activty", "==" + ListArray);



            for (int i = 0; i < ListArray.length(); i++) {
                JSONObject jobject = ListArray.getJSONObject(i);

                Log.i("CityDetailActivity","Result=="+jobject);


                String date=jobject.getString("dt");
                Log.i("CityDetail","Activty=="+date);

                JSONObject temp=jobject.getJSONObject("temp");


            //    String jdate = jobject.getJSONObject("dt");


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
