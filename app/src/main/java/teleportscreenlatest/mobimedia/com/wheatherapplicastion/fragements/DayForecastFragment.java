package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.HttpClient.WeatherHttpClient;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherDetailImage;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Weather;

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
    WeatherDetailImage weatherdetail;
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
        descView.setText("Day:"+dayForecast.weather.currentCondition.getDescr());
        humidity.setText("Humidity:" + dayForecast.weather.currentCondition.getHumidity() + "%");
        pressure.setText("Pressure:" + dayForecast.weather.currentCondition.getPressure() + " hPa");

       /* descriptopon = descView.getText().toString().trim();
        weatherdetail = new WeatherDetailImage(getActivity());
        weatherdetail.setImage(descriptopon, swipelayout);
*/
        // Now we retrieve the weather icon

        JSONIconWeatherTask task = new JSONIconWeatherTask();
        task.execute(new String[]{dayForecast.weather.currentCondition.getIcon()});
        Log.i("DayForcast Fragement ", "get image is==" + dayForecast.weather.currentCondition.getIcon());
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
               img = BitmapFactory.decodeByteArray(data, 0, data.length);
                iconWeather.setImageBitmap(img);
            }

        }

    }
}

