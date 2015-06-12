package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;


public class DetailActivty extends Activity implements View.OnClickListener {

    private TextView mcityField;
    private String mcity = "";
    private Context mContext = this;
    private TextView mdetailsField;
    private TextView mcurrentTemperatureField;
    private TextView mupdatedField;
    private TextView mweatherIcon;
    private Handler mhandler;
    private TextView mtxt_Title;
    private ImageView mback_navigation;
    private TextView mtxt_Next;
    private ImageView mcurrentweather;
    Resources res;
    RelativeLayout rLayout;
    Drawable drawable;
    String updatedOn;
    String timeday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);

        Intent inetent_cityname = getIntent();
        mcity = inetent_cityname.getStringExtra("CitytoDetail");
        mhandler = new Handler();
        SetUpUI();
        updateWeatherData(mcity);
        mtxt_Next.setOnClickListener(this);
        res = getResources();

    }

    private void SetUpUI() {

        mtxt_Title = (TextView) findViewById(R.id.txt_Title);
        mtxt_Title.setText("Detail Infomartion");
        mtxt_Next = (TextView) findViewById(R.id.txt_Next);
        mtxt_Next.setText("Forcast");
        mcityField = (TextView) findViewById(R.id.city_field);
        mupdatedField = (TextView) findViewById(R.id.updated_field);
        mdetailsField = (TextView) findViewById(R.id.details_field);
        mcurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        mweatherIcon = (TextView) findViewById(R.id.weather_icon);
        mback_navigation = (ImageView) findViewById(R.id.back_navigation);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mback_navigation.setBackground(upArrow);
        mback_navigation.setOnClickListener(this);
        rLayout = (RelativeLayout) findViewById(R.id.rLayout);


    }

    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = FetchJson.getJSON(mContext, mcity);
                if (json == null) {
                    mhandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext,
                                    mContext.getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();

                            drawable = res.getDrawable(R.drawable.skyclear);
                            rLayout.setBackground(drawable);
                        }
                    });
                } else {
                    mhandler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }


    private void renderWeather(JSONObject json) {
        try {
            mcityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            mdetailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");


            setImage(details.getString("description").toUpperCase(Locale.US));

            mcurrentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp")) + " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            mupdatedField.setText("Last update: " + updatedOn);
            // GetCurrentTime();
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = mContext.getString(R.string.weather_sunny);
            } else {
                icon = mContext.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = mContext.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = mContext.getString(R.string.weather_drizzle);

                    break;
                case 7:
                    icon = mContext.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = mContext.getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = mContext.getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = mContext.getString(R.string.weather_rainy);
                    break;
            }
        }
        //  mweatherIcon.setText(icon);
    }


    private void setImage(String description) {


        if (description.equals("SKY IS CLEAR")) {
            drawable = res.getDrawable(R.drawable.skyclear);
            rLayout.setBackground(drawable);

        } else if (description.equals("OVERCAST CLOUDS")) {
            drawable = res.getDrawable(R.drawable.overcatclouds);
            rLayout.setBackground(drawable);

        } else if (description.equals("FEW CLOUDS")) {
            drawable = res.getDrawable(R.drawable.fewclouds);
            rLayout.setBackground(drawable);

        } else if (description.equals("MODERATE RAIN")) {
            drawable = res.getDrawable(R.drawable.moderaterain);
            rLayout.setBackground(drawable);

        } else if (description.equals("LIGHT RAIN")) {
            drawable = res.getDrawable(R.drawable.lihjtrain);
            rLayout.setBackground(drawable);

        } else if (description.equals("BROKEN CLOUDS")) {
            drawable = res.getDrawable(R.drawable.brokenclouds);
            rLayout.setBackground(drawable);

        } else if (description.equals("SCATTERED CLOUDS")) {
            drawable = res.getDrawable(R.drawable.scateredclouds);
            rLayout.setBackground(drawable);

        } else {
            drawable = res.getDrawable(R.drawable.nonelse);
            rLayout.setBackground(drawable);

        }
    }

    private String GetCurrentTime() {
        String time = updatedOn;
        time = time.substring(12, updatedOn.length());
        return time;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_navigation:
                Intent intent_back = new Intent(DetailActivty.this, MainActivity.class);
                intent_back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent_back);
                break;
            case R.id.txt_Next:
                Intent intent_forcast = new Intent(DetailActivty.this, ForcastActivity.class);
                intent_forcast.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent_forcast.putExtra("ForcastCityDetail", mcity);

                startActivity(intent_forcast);
                break;

            default:
                break;
        }

    }


}
