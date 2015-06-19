package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.HttpClient.WeatherHttpClient;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;

public class DayForecastFragment extends android.support.v4.app.Fragment {
    TextView tempView;
    TextView descView;
    TextView humidity;
    TextView pressure;
    String descriptopon;
    RelativeLayout swipelayout;
    private DayForecast dayForecast;
    private ImageView iconWeather;
    Resources res;
    Drawable drawable;
    Context context;
    Bitmap img;

    public DayForecastFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dayforecast_fragment, container, false);
        res = getResources();
        swipelayout = (RelativeLayout) v.findViewById(R.id.swipelayout);
        tempView = (TextView) v.findViewById(R.id.tempForecast);
        descView = (TextView) v.findViewById(R.id.skydescForecast);
        humidity = (TextView) v.findViewById(R.id.humidity);
        pressure = (TextView) v.findViewById(R.id.pressure);
        iconWeather = (ImageView) v.findViewById(R.id.forCondIcon);
        tempView.setText("Temp:" + (int) (dayForecast.forecastTemp.min - 265.15) + "/" + (int) (dayForecast.forecastTemp.max - 275.15) + "℃");
        descView.setText("Day:" + dayForecast.weather.currentCondition.getDescr());
        humidity.setText("Humidity:" + dayForecast.weather.currentCondition.getHumidity() + "%");
        pressure.setText("Pressure:" + dayForecast.weather.currentCondition.getPressure() + " hPa");

        // Now we retrieve the weather icon
        return v;

    }

    public void setForecast(DayForecast dayForecast) {
        this.dayForecast = dayForecast;
    }
}

