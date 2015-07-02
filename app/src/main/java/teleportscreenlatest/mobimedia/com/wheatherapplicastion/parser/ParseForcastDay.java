package teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;

/**
 * Created by ram on 24/6/15.
 */
public class ParseForcastDay {
    Context context;
    List<Day> datalist = new ArrayList<Day>();
    Day dayremain = new Day();
    List<Day> daylist = new ArrayList<Day>();

    public List<Day> RenderWeatherDay(JSONObject json) {
        if (json == null) {
            Toast.makeText(context, "NUll ", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            try {
                Day dayobject = new Day();
                JSONObject City = json.getJSONObject("city");
                String Cityname = City.getString("name");
                String CountryName = City.getString("country");
                Log.i("City name is", "City name==" + Cityname);
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
                    daylist.add((Day) datalist);
                    Log.i("Parse Forcast Day ", "different value ==" + dayobject.day + "," + dayobject.min + "," + dayobject.max + "," + dayobject.weather);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return daylist;
    }
}

