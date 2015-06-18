package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.HttpClient.WeatherHttpClient;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.CityListAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.DailyForecastPageAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements.CityListFragement;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherForecast;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Weather;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.JSONWeatherParser;

import static java.lang.Integer.parseInt;

public class CityDetail extends Activity {
    TextView countryname;
    TextView dayType;
    String city;
    String daytype;
    private static String forecastDaysNum = "16";
    String lang = "en";
    ListView citylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        city = getIntent().getStringExtra("ForcastCityDetail");
        daytype = getIntent().getStringExtra("Daytype");
        SetUpUI();
        countryname.setText(city);
        dayType.setText(daytype);


        //getFragmentManager().beginTransaction().add(R.id.citylist, new CityListFragement()).commit();
        JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        task1.execute(new String[]{city, lang, forecastDaysNum});

    }

    private void SetUpUI() {

        countryname = (TextView) findViewById(R.id.countryname);
        dayType = (TextView) findViewById(R.id.daytype);
        citylist=(ListView)findViewById(R.id.citylist);
    }


    private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {
            Weather weather = new Weather();

            String data = ((new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
            WeatherForecast forecast = new WeatherForecast();
            try {
                forecast = JSONWeatherParser.getForecastWeather(data);
                System.out.println("Weather [" + forecast + "]");

                // Let's retrieve the icon
                weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;

        }


        @Override
        protected void onPostExecute(WeatherForecast forecastWeather) {
            super.onPostExecute(forecastWeather);

            //set aDAPTER HERE
            CityListAdapter citylistadapter=new CityListAdapter(parseInt(forecastDaysNum),forecastWeather);
            citylist.setAdapter(citylistadapter);
        }
    }
}
