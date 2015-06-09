package teleportscreenlatest.mobimedia.com.wheatherapplicastion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class DetailActivty extends Activity {

    TextView cityField;

    String city="";

    Context mContext = this;

    TextView detailsField;

    TextView currentTemperatureField;

    TextView updatedField;

    TextView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);


        Intent i=getIntent();
        city=i.getStringExtra("city1");
        //getCity();
        Log.i("Detail Activty", "City Name :" + city);


        updateWeatherData(city);

    }

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = FetchJson.getJSON(mContext, city);
                if(json == null){

                    Log.i("Detail Activty","Json OBject :"+json);

                            Toast.makeText(mContext,
                                    mContext.getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }

                 else {

                            renderWeather(json);
                        }

                }

        }.start();
    }


    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = mContext.getString(R.string.weather_sunny);
            } else {
                icon = mContext.getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = mContext.getString(R.string.weather_thunder);
                    break;
                case 3 : icon = mContext.getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = mContext.getString(R.string.weather_foggy);
                    break;
                case 8 : icon = mContext.getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = mContext.getString(R.string.weather_snowy);
                    break;
                case 5 : icon = mContext.getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    public void getCity(){

        Intent i=getIntent();
        city=i.getStringExtra("city");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
