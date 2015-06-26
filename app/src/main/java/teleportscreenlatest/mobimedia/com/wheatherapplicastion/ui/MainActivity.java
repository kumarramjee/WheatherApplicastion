package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.GooglePlacesAutocompleteAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements.NavigationDrawerFragement;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.GpsLocation;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.Locationfinder;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    Toolbar mtoolbar;
    private TextView txt_Title;
    private TextView txt_Next;
    private AutoCompleteTextView city;
    private String mcityname = "";
    private int x;
    private RelativeLayout rootlayot;
    private Context mContext = this;
    private String CityName = "";
    private TextView mdetailsField;
    private TextView mcurrentTemperatureField;
    private TextView mupdatedField;
    private Handler mhandler;
    private TextView mcityField;
    private String updatedOn;
    private Resources res;
    private RelativeLayout rLayout;
    private Drawable drawable;
    private Locationfinder mlocationfinder;
    TextView txt_header;
    String senddaytype;
    DrawerLayout mDrawerlayout;
    NavigationDrawerFragement mnavigationdrawerfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlocationfinder = new Locationfinder();
        CityName = mlocationfinder.getCurrentLOcationName(mContext);
        SetupToolbar();
        SetUpUI();
        mhandler = new Handler();
        res = getResources();
        GpsLocation getgpslocation = new GpsLocation();
        getgpslocation.turnGPSOn();
        GetDetailWeatherDetail(CityName);
        mnavigationdrawerfragment = (NavigationDrawerFragement) getSupportFragmentManager()
                .findFragmentById(R.id.navigatiodrawerfragement);
        DrawerLayout mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mnavigationdrawerfragment.SetUP(R.id.navigatiodrawerfragement, (DrawerLayout) findViewById(R.id.drawerlayout), mtoolbar);
        if (mnavigationdrawerfragment != null && mnavigationdrawerfragment.isDrawerOpen())
            mnavigationdrawerfragment.closeDrawer();
        txt_Next.setOnClickListener(this);
        rLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (rootlayot.VISIBLE == View.VISIBLE) {
                    rootlayot.setVisibility(View.GONE);
                }
                city.setText("");
                return true;
            }
        });
        city.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x = position;
            }
        });

        city.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                getCityDetail();
                return false;
            }
        });
    }

    private void GetDetailWeatherDetail(String cityName) {
        if (cityName.length() == 0) {
            Toast.makeText(MainActivity.this, "Not able to find current location.Check ur connection", Toast.LENGTH_SHORT).show();

        } else {
            new Thread() {
                public void run() {
                    final JSONObject json = FetchJson.getJSON(mContext, CityName);
                    if (json == null) {
                        mhandler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(mContext,
                                        "Try after some timee.",
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
    }

    private void renderWeather(JSONObject json) {
        try {
            txt_header.setText(json.getString("name") +
                    ", " +
                    json.getJSONObject("sys").getString("country"));
            // mcityField.setText("");
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");

            senddaytype = details.getString("description");

            mdetailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");


            setImage(details.getString("description").toUpperCase(Locale.US));
            mcurrentTemperatureField.setText(
                    String.format(main.getInt("temp") + " ℃"));
            mcurrentTemperatureField.setTextSize(5, 20);
            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt") * 1000));
            mupdatedField.setText("Last update: " + updatedOn);
            // mupdatedField.setText("Last update: " + updatedOn);
            mupdatedField.setText("");
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
            drawable = res.getDrawable(R.drawable.moderate);
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

    private void SetUpUI() {
        city = (AutoCompleteTextView) findViewById(R.id.edittextplace);
        rootlayot = (RelativeLayout) findViewById(R.id.rootlayot);
        rootlayot.setVisibility(View.INVISIBLE);
        rLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        mcityField = (TextView) findViewById(R.id.city_field1);
        mupdatedField = (TextView) findViewById(R.id.updated_field1);
        mdetailsField = (TextView) findViewById(R.id.details_field1);
        mcurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field1);

    }

    private void SetupToolbar() {
        txt_header = (TextView) findViewById(R.id.txt_header);
        // txt_header.setText(CityName);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Title.setVisibility(View.INVISIBLE);
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setText("+");
        txt_Next.setTextSize(35);
        mtoolbar = (Toolbar) findViewById(R.id.customActionbar);
        ImageView back_navigation = (ImageView) findViewById(R.id.back_navigation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_Next:

                rootlayot.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
    }

    private void getCityDetail() {
        mcityname = city.getText().toString();
        if ((mcityname.length() == 0) || (mcityname == null)) {

            Toast.makeText(this, "Enter City Name", Toast.LENGTH_SHORT).show();
        } else {
            //    GetDetailWeatherDetail(mcityname);
            Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
            intent_submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent_submit.putExtra("ForcastCityDetail", mcityname);
            // intent_submit.putExtra("Daytype", senddaytype);

            startActivity(intent_submit);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.gc();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


}
