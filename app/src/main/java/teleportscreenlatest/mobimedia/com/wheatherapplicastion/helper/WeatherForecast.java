package teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;


/**
 * Created by ram on 15/6/15.
 */
public class WeatherForecast {

    private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    public void addForecast(DayForecast forecast) {
        daysForecast.add(forecast);
        Log.i("Weather Forcast IMages","List=="+"Add forecast["+forecast+"]");

    }

    public DayForecast getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}
