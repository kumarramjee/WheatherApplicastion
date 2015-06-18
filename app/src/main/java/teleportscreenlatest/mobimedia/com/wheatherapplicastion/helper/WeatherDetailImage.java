package teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;

/**
 * Created by ram on 15/6/15.
 */
public class WeatherDetailImage {
    Handler mhandler = new Handler();
    Context mContext;
    String CityName;
    TextView cityfield;
    TextView updatefield;
    TextView detailfield;
    String updatedOn;
    static RelativeLayout rLayout;
    static Resources res;// = get
    TextView currenttemp;
    static Drawable drawable;

    public WeatherDetailImage() {

    }

    public static void setImage(String description, RelativeLayout rLayout, Resources res, Drawable drawable) {
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


}
