package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherForecastForcastActivity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;

/**
 * Created by ram on 18/6/15.
 */
public class CityListAdapter extends BaseAdapter {
    DayForecast dayForecast;
    private int numDays;
    private android.support.v4.app.FragmentManager fm;
    private WeatherForecastForcastActivity forecast;
    Context context;
    String getCurrentday;
    TextView dayname;
    TextView daytype;
    TextView daytemp;
    TextView dayhumidity;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");

    public CityListAdapter(int numDays, WeatherForecastForcastActivity weatherForecast)

    {
        super();
        this.numDays = numDays;
        this.forecast = weatherForecast;


    }


    @Override
    public int getCount() {
        return numDays;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        // getCurrentday=getDay();


        // Lookup view for data population
        dayname = (TextView) convertView.findViewById(R.id.dayname);
        daytype = (TextView) convertView.findViewById(R.id.daytype);
        daytemp = (TextView) convertView.findViewById(R.id.daytemp);
        dayhumidity = (TextView) convertView.findViewById(R.id.dayhumidity);


        // Populate the data into the listview view using the data object

        dayname.setText("Sunday");
        daytype.setText(dayForecast.weather.currentCondition.getDescr());
        daytemp.setText((int) (dayForecast.forecastTemp.min - 265.15) + "/" + (int) (dayForecast.forecastTemp.max - 265.15) + "℃");
        dayhumidity.setText(dayForecast.weather.currentCondition.getHumidity() + "%");
        // Return the completed view to render on screen


        return convertView;


    }
}
