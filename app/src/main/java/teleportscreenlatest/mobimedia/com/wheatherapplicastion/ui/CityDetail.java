package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.CityDetailAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.ParseForcastDay;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;

public class CityDetail extends Activity {
    private TextView countryname;
    private String city = "";
    private static String forecastDaysNum = "16";
    private String lang = "en";
    private Context mContext = this;
    private Handler mhandler;
    ListView forcastdetail;
    List<Day> datalist;
    ListView lview;
    List<Day> daylist;
    ParseForcastDay parse;
    JSONObject jsoncity;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        SetUpUI();
        parse = new ParseForcastDay();
        city = getIntent().getStringExtra("ForcastCityDetail");
        cityName.setText(city);
        daylist = new ArrayList<Day>();
        datalist = new ArrayList<Day>();
        mhandler = new Handler();
        //  GetDetailWeatherDetail(city);
        datalist = GetDetailWeatherDetailJson(city);
        CityDetailAdapter cityadapter = new CityDetailAdapter(mContext, datalist);
        lview.setAdapter(cityadapter);

    }

    private void SetUpUI() {
        lview = (ListView) findViewById(R.id.forcastdetail);
        cityName = (TextView) findViewById(R.id.cityName);


    }

    private List<Day> GetDetailWeatherDetailJson(String city) {
        List<Day> datalisted = new ArrayList<Day>();
        if (city.length() == 0) {
            Toast.makeText(mContext, "Not able to find your location.Check ur connection", Toast.LENGTH_SHORT).show();

        } else {
            JSONObject json = FetchForcastForDayJson.getJSON(mContext, city);

            Log.i("Json For city", "Values==" + json);

            if (json == null) {
                Toast.makeText(CityDetail.this, "Not Getting information.", Toast.LENGTH_SHORT).show();
            } else {

                datalisted = parse.getRenderWeatherDayListValues(json);

            }

        }

        return datalisted;
    }


    private void GetDetailWeatherDetail(final String city) {

        if (city.length() == 0) {
            Toast.makeText(mContext, "Not able to find current location.Check ur connection", Toast.LENGTH_SHORT).show();

        } else {
            new Thread() {
                public void run() {
                    final JSONObject json = FetchForcastForDayJson.getJSON(mContext, city);

                    if (json == null) {
                        mhandler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(CityDetail.this, "Not Getting information.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mhandler.post(new Runnable() {
                            public void run() {


                                parse.RenderWeatherDay(json);


                            }
                        });
                    }
                }
            }.start();
        }
    }
}
