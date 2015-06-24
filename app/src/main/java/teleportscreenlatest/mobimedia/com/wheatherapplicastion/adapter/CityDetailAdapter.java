package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.helper.WeatherForecastForcastActivity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;

/**
 * Created by ram on 18/6/15.
 */
public class CityDetailAdapter extends BaseAdapter {
    DayForecast dayForecast;
    private int numDays;
    private android.support.v4.app.FragmentManager fm;
    private WeatherForecastForcastActivity forecast;
    private Context mContext;
    private String getCurrentday;
    private TextView day;
    private TextView description;
    private TextView daytempmin;
    private TextView daytempmax;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");
    List<Day> datalist = new ArrayList<Day>();
    Context context;

    public CityDetailAdapter(Context mContext, List<Day> datalist)

    {
        super();
        this.numDays = numDays;
        this.datalist = datalist;

    }


    @Override
    public int getCount() {

        Log.i("Datalist Size is", "==" + datalist.size());
        return datalist.size();

    }

    @Override
    public Day getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.detail_list, parent, false);

        }
        day = (TextView) convertView.findViewById(R.id.dayname);
        description = (TextView) convertView.findViewById(R.id.description);
        daytempmin = (TextView) convertView.findViewById(R.id.daytempmin);
        daytempmax = (TextView) convertView.findViewById(R.id.daytempmax);

        Day mday = getItem(position);

        day.setText(mday.day);
        description.setText(mday.weather);
        daytempmin.setText(mday.min);
        daytempmax.setText(mday.max);

        return convertView;
    }


}
