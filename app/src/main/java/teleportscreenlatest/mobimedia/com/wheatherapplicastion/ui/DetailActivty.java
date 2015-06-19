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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
    private Resources res;
    private RelativeLayout rLayout;
    private Drawable drawable;
    private String updatedOn;
    private String timeday;
    private String senddaytype;
    TextView txt_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);

        Intent inetent_cityname = getIntent();
        mcity = inetent_cityname.getStringExtra("ForcastCityDetail");
        mhandler = new Handler();

        SetUpUI();
       // txt_header.setText(mcity);

        updateWeatherData(mcity);
        mtxt_Next.setOnClickListener(this);
        res = getResources();


    }

    private void SetUpUI() {

        mtxt_Title = (TextView) findViewById(R.id.txt_Title);
        mtxt_Title.setText("Detail Infomartion");
        mtxt_Next = (TextView) findViewById(R.id.txt_Next);
        mtxt_Next.setText("NEXT");
        mtxt_Next.setTextSize(3,5);
        txt_header = (TextView) findViewById(R.id.txt_header);


        mcityField = (TextView) findViewById(R.id.city_field);
        mupdatedField = (TextView) findViewById(R.id.updated_field);
        mdetailsField = (TextView) findViewById(R.id.details_field);
        mcurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
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
            txt_header.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));
           // mcityField.setVisibility(View.INVISIBLE);


            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            mdetailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            senddaytype = details.getString("description").toUpperCase(Locale.US).toString().trim();

            setImage(details.getString("description").toUpperCase(Locale.US));

            mcurrentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp")) + " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            mupdatedField.setText("Last update: " + updatedOn);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
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
        } else if (description.equals("HAZE")) {
            drawable = res.getDrawable(R.drawable.haze);
            rLayout.setBackground(drawable);

        } else if (description.equals("THUNDERSTROM WITH HEAVY RAIN")) {
            drawable = res.getDrawable(R.drawable.thunderwithrain);
            rLayout.setBackground(drawable);

        } else if (description.equals("MIST")) {
            drawable = res.getDrawable(R.drawable.mist);
            rLayout.setBackground(drawable);

        } else {
            drawable = res.getDrawable(R.drawable.nonelse);
            rLayout.setBackground(drawable);

        }
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
                if ((mcity.length() < 0) || (senddaytype.length() < 0)) {
                } else {
                    Intent intent_forcast = new Intent(DetailActivty.this, ForcastActivity.class);
                    intent_forcast.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_forcast.putExtra("ForcastCityDetail", mcity);
                    intent_forcast.putExtra("Daytype", senddaytype);


                    startActivity(intent_forcast);
                    break;


                }
            default:
                break;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();
    }

}
