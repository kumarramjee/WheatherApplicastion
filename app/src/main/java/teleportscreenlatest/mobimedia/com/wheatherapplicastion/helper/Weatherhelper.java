package teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ram on 15/6/15.
 */
public class Weatherhelper {
    Handler mhandler = new Handler();
    Context mContext;
    String CityName;
    TextView cityfield;
    TextView updatefield;
    TextView detailfield;
    String updatedOn;
    RelativeLayout rLayout;
    Resources res = Resources.getSystem();
    TextView currenttemp;
    Drawable drawable;
    WeatherDetailImage mweatherdetail;
/*
    public void GetDetailWeatherDetail(String cityName) {


        new Thread() {
            public void run() {
                final JSONObject json = FetchJson.getJSON(mContext, CityName);
                Log.i("MAin Activity", "jsom==" + json);

                if (json == null) {
                    mhandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext,
                                    "Json Not Find",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                } else {
                    mhandler.post(new Runnable() {
                        public void run() {
                            mweatherdetail.renderWeather(json, cityfield, updatefield, detailfield, currenttemp);
                        }
                    });
                }
            }
        }.start();
    }*/


}


