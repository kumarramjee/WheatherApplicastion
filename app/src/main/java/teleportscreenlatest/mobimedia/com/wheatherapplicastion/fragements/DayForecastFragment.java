package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.app.Fragment;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.HttpClient.WeatherHttpClient;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;

public class DayForecastFragment extends android.support.v4.app.Fragment {

    private DayForecast dayForecast;
    private ImageView iconWeather;

    public DayForecastFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dayforecast_fragment, container, false);

        TextView tempView = (TextView) v.findViewById(R.id.tempForecast);
        TextView descView = (TextView) v.findViewById(R.id.skydescForecast);
        TextView min_temp=(TextView)v.findViewById(R.id.mintempforcast);
        TextView humidity=(TextView)v.findViewById(R.id.humidity);
        TextView pressure=(TextView)v.findViewById(R.id.pressure);
        tempView.setText("Min temp:"+(int) (dayForecast.forecastTemp.min - 265.15));// + "-" + (int) (dayForecast.forecastTemp.max - 275.15));
        descView.setText(dayForecast.weather.currentCondition.getDescr().toUpperCase());
        min_temp.setText("Max temp:"+(int) (dayForecast.forecastTemp.max - 265.15));
        humidity.setText("Humidity:"+dayForecast.weather.currentCondition.getHumidity()+"%");
        pressure.setText("Pressure:"+dayForecast.weather.currentCondition.getPressure() + " hPa");
        iconWeather = (ImageView) v.findViewById(R.id.forCondIcon);

        // Now we retrieve the weather icon
        JSONIconWeatherTask task = new JSONIconWeatherTask();
        task.execute(new String[]{dayForecast.weather.currentCondition.getIcon()});

        return v;

    }

    public void setForecast(DayForecast dayForecast) {
        this.dayForecast = dayForecast;
    }


    private class JSONIconWeatherTask extends AsyncTask<String, Void, byte[]> {

            @Override
            protected byte[] doInBackground(String... params) {

                byte[] data = null;

                try {

                    // Let's retrieve the icon
                    data = ((new WeatherHttpClient()).getImage(params[0]));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return data;
            }


            @Override
            protected void onPostExecute(byte[] data) {
                super.onPostExecute(data);

                if (data != null) {
                    Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);
                    iconWeather.setImageBitmap(img);
                }
            }

        }
    }

