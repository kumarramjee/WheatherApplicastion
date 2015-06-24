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
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;

/**
 * Created by ram on 24/6/15.
 */
public class ParseForcastDay {
    List<Day> daylist = new ArrayList<Day>();
    List<Day> datalist = new ArrayList<Day>();
    JSONObject json;


    public List<Day> GetListFromJson() {
        Log.i("ForCityDetail", "List Vaslue==" + datalist);
        return null;
    }
    public void RenderWeatherDay(JSONObject json) {
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
                daylist.add(dayobject);
                datalist = daylist;

            }
            Log.i("ParseForcast", "list values==" + datalist.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Day> Renderlist(JSONObject json) {
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
                daylist.add(dayobject);

                datalist = daylist;
                Log.i("ParseForcast", "list values==" + datalist);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datalist;
    }


}

