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

    List<Day> daylist = new ArrayList<Day>();
    List<Day> datalisted = new ArrayList<Day>();

    ParseForcastDay parse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        parse = new ParseForcastDay();
        city = getIntent().getStringExtra("ForcastCityDetail");
        mhandler = new Handler();
        GetDetailWeatherDetail(city);
        datalist = new ArrayList<Day>();
        datalisted = parse.GetListFromJson();



        CityDetailAdapter cityadapter = new CityDetailAdapter(mContext, datalist);
        forcastdetail = (ListView) findViewById(R.id.forcastdetail);
        forcastdetail.setAdapter(cityadapter);
    }

    private List<Day> getListbyJsonParsing(String city) {
        Log.i("City Detail ", "City for json==" + city);


        final JSONObject getjson = FetchForcastForDayJson.getJSON(mContext, city);

        datalisted = getAllListitem(getjson);


        return datalisted;
    }

    private List<Day> getAllListitem(JSONObject json) {

        {
            try {
              /*  Day dayobject = new Day();


                JSONArray ListArray = json.getJSONArray("list");
                for (int i = 0; i < ListArray.length(); i++) {
                    JSONObject jobject = ListArray.getJSONObject(i);
                    String date = jobject.getString("dt");
                    JSONObject temp = jobject.getJSONObject("temp");
                    dayobject.day = temp.getString("day");
                    dayobject.min = temp.getString("min");
                    dayobject.max = temp.getString("max");
                    JSONArray weather = jobject.getJSONArray("weather");
                    for (int j = 0; j < weather.length(); j++) {
                        JSONObject jweather = weather.getJSONObject(j);
                        dayobject.weather = jweather.getString("description");
                    }
                    daylist.add(dayobject);

                }
                Log.i("ParseForcast", "list values==" + daylist.size());
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return daylist;
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
