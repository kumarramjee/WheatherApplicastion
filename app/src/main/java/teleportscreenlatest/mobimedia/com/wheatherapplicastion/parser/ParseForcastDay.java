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
    List<Day> datalist = new ArrayList<Day>();
    Day dayremain=new Day();
    public List<Day> RenderWeatherDay(JSONObject json) {
        List<Day> daylist = new ArrayList<Day>();
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
                Log.i("Parse Forcast Day ", "different value ==" + dayobject.day + "," + dayobject.min + "," + dayobject.max + "," + dayobject.weather);

/*
                Log.i("Parse json", "for City Detail==" + datalist);
                for (int j = 0; j < daylist.size(); j++) {
                    {
                        //dayobject=daylist.get(i);

                    }

                }
*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*for (int j = 0; j < daylist.size(); j++) {
            Log.i("Parse to jsoin",":==:"+daylist.get(j));
            String max=daylist.get(j).max;
            String min=daylist.get(j).min;
            String day=daylist.get(j).day;
            String weather=daylist.get(j).weather;
            Log.i("parse to json","Activity reesuklt==="+max+","+min+","+day+","+weather);
        }*/
        return daylist;
    }
    public List<Day> getRenderWeatherDayListValues(JSONObject json) {
        List<Day> daylist = new ArrayList<Day>();
        try {
            JSONObject City = json.getJSONObject("city");
            String Cityname = City.getString("name");
            String CountryName = City.getString("country");
            Log.i("City name is", "City name==" + Cityname);
            JSONArray ListArray = json.getJSONArray("list");
            for (int i = 0; i < ListArray.length(); i++) {
                JSONObject jobject = ListArray.getJSONObject(i);
                Day dayobject = new Day();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return daylist;
    }
}

