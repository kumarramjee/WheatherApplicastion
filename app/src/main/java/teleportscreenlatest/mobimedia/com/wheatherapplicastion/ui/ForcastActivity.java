package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.HttpClient.WeatherHttpClient;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.DailyForecastPageAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Weather;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherForecast;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.JSONWeatherParser;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Location;

import static java.lang.Integer.parseInt;

public class ForcastActivity extends FragmentActivity implements View.OnClickListener {
    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView unitTemp;
    TextView txt_Title;
    LinearLayout detailmainlayout;
    private TextView hum;
    private ImageView imgView;
    Drawable drawable;
    Resources res;
    private static String forecastDaysNum = "16";
    private ViewPager pager;
    ImageView mback_navigation;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);
        city = getIntent().getStringExtra("ForcastCityDetail");
        String lang = "en";
        SetUpUI();
        res = getResources();

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city, lang});

        // cityText.setText(city);

        JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        task1.execute(new String[]{city, lang, forecastDaysNum});

    }

    private void SetUpUI() {

        cityText = (TextView) findViewById(R.id.cityText);
        temp = (TextView) findViewById(R.id.temp);
        unitTemp = (TextView) findViewById(R.id.unittemp);
        unitTemp.setText("℃");
        unitTemp.setVisibility(View.INVISIBLE);
        temp.setVisibility(View.INVISIBLE);
        condDescr = (TextView) findViewById(R.id.skydesc);
        mback_navigation = (ImageView) findViewById(R.id.back_navigation);
        mback_navigation.setVisibility(View.INVISIBLE);
        pager = (ViewPager) findViewById(R.id.pager);
        imgView = (ImageView) findViewById(R.id.condIcon);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        TextView txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setVisibility(View.INVISIBLE);
        txt_Title.setText("Detail Information");
        detailmainlayout = (LinearLayout) findViewById(R.id.detailmainlayout);
        mback_navigation = (ImageView) findViewById(R.id.back_navigation);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mback_navigation.setBackground(upArrow);
        mback_navigation.setOnClickListener(this);



    }

    private void setImage(String description) {

        if (description.equals("Sky is Clear")) {
            drawable = res.getDrawable(R.drawable.skyclear);
            detailmainlayout.setBackground(drawable);

        } else if (description.equals("OVERCAST CLOUDS")) {
            drawable = res.getDrawable(R.drawable.overcatclouds);
            detailmainlayout.setBackground(drawable);

        } else if (description.equals("FEW CLOUDS")) {
            drawable = res.getDrawable(R.drawable.fewclouds);
            detailmainlayout.setBackground(drawable);

        } else if (description.equals("MODERATE RAIN")) {
            drawable = res.getDrawable(R.drawable.moderaterain);
            detailmainlayout.setBackground(drawable);

        } else if (description.equals("light rain")) {
            drawable = res.getDrawable(R.drawable.lihjtrain);
            detailmainlayout.setBackground(drawable);

        } else if (description.equals("BROKEN CLOUDS")) {
            drawable = res.getDrawable(R.drawable.brokenclouds);
            detailmainlayout.setBackground(drawable);

        } else if (description.equals("SCATTERED CLOUDS")) {
            drawable = res.getDrawable(R.drawable.scateredclouds);
            detailmainlayout.setBackground(drawable);

        } else {
            drawable = res.getDrawable(R.drawable.nonelse);
            detailmainlayout.setBackground(drawable);

        }

    }

    @Override
    public void onClick(View v) {
        {
            switch (v.getId()) {
                case R.id.back_navigation:
                    Intent intent_back = new Intent(ForcastActivity.this, DetailActivty.class);
                    startActivity(intent_back);
                    break;


                default:
                    break;
            }

        }
    }


    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
        Weather weather = new Weather();

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1]));

            try {
                weather = JSONWeatherParser.getWeather(data);
                System.out.println("Weather [" + weather + "]");
                // Let's retrieve the icon
                weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }


            cityText.setText((weather.location.getCity() + "," + weather.location.getCountry()).toUpperCase());
            temp.setText("" + Math.round((weather.temperature.getTemp() - 265.15)));
            //     condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            //      currentconditionname = (weather.currentCondition.getDescr());
            //   setImage(currentconditionname);
            /*

			temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)) + "�C");
			hum.setText("" + weather.currentCondition.getHumidity() + "%");
			press.setText("" + weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText("" + weather.wind.getSpeed() + " mps");
			windDeg.setText("" + weather.wind.getDeg() + "�");
			*/
        }


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

            DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);

            pager.setAdapter(adapter);
        }


    }
}
