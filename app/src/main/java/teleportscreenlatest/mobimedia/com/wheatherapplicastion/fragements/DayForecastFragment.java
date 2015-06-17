package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


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
import android.support.v4.app.Fragment;

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
        tempView.setText("Temp:" + (int) (dayForecast.forecastTemp.min - 265.15) + "/" + (int) (dayForecast.forecastTemp.max - 275.15) + "℃");
        descView.setText(dayForecast.weather.currentCondition.getDescr().toUpperCase());
        humidity.setText("Humidity:" + dayForecast.weather.currentCondition.getHumidity() + "%");
        pressure.setText("Pressure:" + dayForecast.weather.currentCondition.getPressure() + " hPa");
        iconWeather = (ImageView) v.findViewById(R.id.forCondIcon);

        descriptopon = descView.getText().toString();
       // setImage(descriptopon);


        // Now we retrieve the weather icon
        JSONIconWeatherTask task = new JSONIconWeatherTask();
        task.execute(new String[]{dayForecast.weather.currentCondition.getIcon()});

        return v;

    }/*

    private void setImage(String description) {

        if (description.equals("SKY IS CLEAR")) {
            drawable = res.getDrawable(R.drawable.skyclear);
            iconWeather.setBackground(drawable);

        } else if (description.equals("OVERCAST CLOUDS")) {
            drawable = res.getDrawable(R.drawable.overcatclouds);
            iconWeather.setBackground(drawable);

        } else if (description.equals("FEW CLOUDS")) {
            drawable = res.getDrawable(R.drawable.fewclouds);
            iconWeather.setBackground(drawable);

        } else if (description.equals("MODERATE RAIN")) {
            drawable = res.getDrawable(R.drawable.moderaterain);
            iconWeather.setBackground(drawable);

        } else if (description.equals("LIGHT RAIN")) {
            drawable = res.getDrawable(R.drawable.lihjtrain);
            iconWeather.setBackground(drawable);

        } else if (description.equals("BROKEN CLOUDS")) {
            drawable = res.getDrawable(R.drawable.brokenclouds);
            iconWeather.setBackground(drawable);

        } else if (description.equals("SCATTERED CLOUDS")) {
            drawable = res.getDrawable(R.drawable.scateredclouds);
            swipelayout.setBackground(drawable);
        } else if (description.equals("HAZE")) {
            drawable = res.getDrawable(R.drawable.haze);
            iconWeather.setBackground(drawable);

        } else if (description.equals("THUNDERSTROM WITH HEAVY RAIN")) {
            drawable = res.getDrawable(R.drawable.thunderwithrain);
            iconWeather.setBackground(drawable);

        } else if (description.equals("MIST")) {
            drawable = res.getDrawable(R.drawable.mist);
            iconWeather.setBackground(drawable);

        } else {
            drawable = res.getDrawable(R.drawable.nonelse);
            iconWeather.setBackground(drawable);

        }

    }
*/
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

