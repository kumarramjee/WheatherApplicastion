package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements.WeatherFragment;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;

import static java.lang.Integer.parseInt;

public class CityDetail extends Activity {
    TextView countryname;
    TextView dayType;
    String city;
    String daytype;
    private static String forecastDaysNum = "16";
    String lang = "en";
    ListView citylist;
    Context mContext;
    private Handler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        city = getIntent().getStringExtra("ForcastCityDetail");
        mhandler=new Handler();
     //   getFragmentManager().beginTransaction().add(R.id.container, new WeatherFragment()).commit();
        GetDetailWeatherDetail(mContext, city);

    }

    private void GetDetailWeatherDetail(final Context mContext, final String city) {


        if (city.length() == 0) {
            Toast.makeText(mContext, "Not able to find current location.Check ur connection", Toast.LENGTH_SHORT).show();

        } else {
            new Thread() {
                public void run() {
                    final JSONObject json = FetchForcastForDayJson.getJSON(mContext, city);
                    if (json == null) {
                        mhandler.post(new Runnable() {
                            public void run() {

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

        try {
            JSONObject jsondaycode=json.getJSONObject("cod");
            String city_code=jsondaycode.getString("cod");
            Log.i("City Code is", "City Code" + city_code);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
