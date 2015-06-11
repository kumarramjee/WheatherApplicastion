package teleportscreenlatest.mobimedia.com.wheatherapplicastion.util;

import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragments.DayForecast;

/**
 * Created by ram on 11/6/15.
 */
public class WeatherForecast {
    private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    public void addForecast(DayForecast forecast) {
        daysForecast.add(forecast);
        System.out.println("Add forecast ["+forecast+"]");
    }

    public DayForecast getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}
