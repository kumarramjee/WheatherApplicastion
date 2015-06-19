package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements.WeatherFragment;

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

        getFragmentManager().beginTransaction().add(R.id.container, new WeatherFragment()).commit();


    }


}
