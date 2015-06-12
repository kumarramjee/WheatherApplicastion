package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastJson;


public class ForcastActivity extends Activity implements View.OnClickListener {
    private TextView mcityField;
    final private String mcity = "";
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
    String cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

        SetUpUI();
        Intent integtforcast = getIntent();
        cityname = integtforcast.getStringExtra("ForcastCityDetail");
        TimeForcastData(cityname);
        DayForcastData(cityname);

    }

    private void DayForcastData(final String cityname) {

        new Thread() {
            public void run() {
                final JSONObject jsonday = FetchForcastForDayJson.getJSON(mContext, cityname);
                if (jsonday == null) {
                    mhandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext,
                                    mContext.getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    getJsonResponseDayForcast(jsonday);
                }
            }
        }.start();


    }

    private void getJsonResponseDayForcast(JSONObject json) {
        Log.i("Forcast detail", "Information for current citydetail" + json);


    }


    private void TimeForcastData(final String cityname) {

        new Thread() {
            public void run() {
                final JSONObject json = FetchForcastJson.getJSON(mContext, cityname);
                if (json == null) {
                    mhandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext,
                                    mContext.getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    getJsonResponseForcast(json);
                }
            }
        }.start();


    }

    private void getJsonResponseForcast(JSONObject json) {

        Log.i("Forcast detail", "Information for current citydetail" + json);


    }


    private void SetUpUI() {

        mtxt_Title = (TextView) findViewById(R.id.txt_Title);
        mtxt_Title.setText("Forcast Infomartion");
        mtxt_Next = (TextView) findViewById(R.id.txt_Next);
        mtxt_Next.setVisibility(View.INVISIBLE);
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_navigation:
                Intent intent_back = new Intent(ForcastActivity.this, DetailActivty.class);
                intent_back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent_back);
                break;

            default:
                break;
        }
    }
}
