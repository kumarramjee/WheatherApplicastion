package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.content.Intent;
import android.content.res.Resources;
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

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.HttpClient.WeatherHttpClient;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.DailyForecastPageAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Weather;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherForecastForcastActivity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.JSONWeatherParser;

import static java.lang.Integer.parseInt;

public class ForcastActivity extends FragmentActivity implements View.OnClickListener {
    private TextView cityText;
    private TextView condDescr;
    private TextView press;
    private TextView unitTemp;
    private TextView txt_Title;
    private LinearLayout detailmainlayout;
    private TextView hum;
    private ImageView imgView;
    private Drawable drawable;
    private Resources res;
    private static String forecastDaysNum = "16";
    private ViewPager pager;
    private ImageView mback_navigation;
    private String city;
    private TextView txt_Next;
    private String typeofday;
    private RelativeLayout relativimage;
    private TextView current_temperature_field;
    private TextView details_field;
    private TextView daytype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);
        city = getIntent().getStringExtra("ForcastCityDetail");
        String lang = "en";
        SetUpUI();
        cityText.setText(city);
        res = getResources();
        drawable = res.getDrawable(R.drawable.skyclear);
        typeofday = getIntent().getStringExtra("Daytype");
        daytype.setText(typeofday.toLowerCase());
        setImage(typeofday);


      /*  JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city, lang});
       */ JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        task1.execute(new String[]{city, lang, forecastDaysNum});
    }

    private void SetUpUI() {
        daytype = (TextView) findViewById(R.id.daytype);
        current_temperature_field = (TextView) findViewById(R.id.current_temperature_field);
        details_field = (TextView) findViewById(R.id.details_field);
        relativimage = (RelativeLayout) findViewById(R.id.textimage);
        cityText = (TextView) findViewById(R.id.cityText);
        daytype = (TextView) findViewById(R.id.daytype);
        mback_navigation = (ImageView) findViewById(R.id.back_navigation);
        mback_navigation.setVisibility(View.INVISIBLE);
        pager = (ViewPager) findViewById(R.id.pager);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setVisibility(View.INVISIBLE);
        txt_Title.setText("Forecast Information");
        detailmainlayout = (LinearLayout) findViewById(R.id.detailmainlayout);


    }

    private void setImage(String description) {


        if (description.equals("SKY IS CLEAR")) {
            drawable = res.getDrawable(R.drawable.skyclear);
            relativimage.setBackground(drawable);

        } else if (description.equals("OVERCAST CLOUDS")) {
            drawable = res.getDrawable(R.drawable.overcatclouds);
            relativimage.setBackground(drawable);

        } else if (description.equals("FEW CLOUDS")) {
            drawable = res.getDrawable(R.drawable.fewclouds);
            relativimage.setBackground(drawable);

        } else if (description.equals("MODERATE RAIN")) {
            drawable = res.getDrawable(R.drawable.moderaterain);
            relativimage.setBackground(drawable);

        } else if (description.equals("LIGHT RAIN")) {
            drawable = res.getDrawable(R.drawable.lihjtrain);
            relativimage.setBackground(drawable);

        } else if (description.equals("BROKEN CLOUDS")) {
            drawable = res.getDrawable(R.drawable.brokenclouds);
            relativimage.setBackground(drawable);

        } else if (description.equals("SCATTERED CLOUDS")) {
            drawable = res.getDrawable(R.drawable.scateredclouds);
            relativimage.setBackground(drawable);
        } else if (description.equals("HAZE")) {
            drawable = res.getDrawable(R.drawable.haze);
            relativimage.setBackground(drawable);

        } else if (description.equals("THUNDERSTROM WITH HEAVY RAIN")) {
            drawable = res.getDrawable(R.drawable.thunderwithrain);
            relativimage.setBackground(drawable);

        } else if (description.equals("MIST")) {
            drawable = res.getDrawable(R.drawable.mist);
            relativimage.setBackground(drawable);

        } else {
            drawable = res.getDrawable(R.drawable.nonelse);
            relativimage.setBackground(drawable);

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

    private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecastForcastActivity> {

        @Override
        protected WeatherForecastForcastActivity doInBackground(String... params) {
            Weather weather = new Weather();

            String data = ((new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
            WeatherForecastForcastActivity forecast = new WeatherForecastForcastActivity();
            try {
                forecast = JSONWeatherParser.getForecastWeather(data);
                System.out.println("Weather [" + forecast + "]");

                // Let's retrieve the icon
                //   weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;

        }


        @Override
        protected void onPostExecute(WeatherForecastForcastActivity forecastWeather) {
            super.onPostExecute(forecastWeather);

            DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);

            pager.setAdapter(adapter);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();
    }

}
