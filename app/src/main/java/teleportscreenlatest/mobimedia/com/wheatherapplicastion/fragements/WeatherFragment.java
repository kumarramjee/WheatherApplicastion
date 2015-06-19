package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.WeatherForecastListAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.WeatherForecast;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    ListView mForecastListView;
    TextView mLocationNameTextView;
    TextView mCurrentTemperatureTextView;
    TextView mAttributionTextView;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        mLocationNameTextView = (TextView) rootView.findViewById(R.id.location_name);
        mCurrentTemperatureTextView = (TextView) rootView
                .findViewById(R.id.current_temperature);

        // Set up list view for weather forecasts.
        mForecastListView = (ListView) rootView.findViewById(R.id.weather_forecast_list);
        final WeatherForecastListAdapter adapter = new WeatherForecastListAdapter(
                new ArrayList<WeatherForecast>(), getActivity());
        mForecastListView.setAdapter(adapter);

        mAttributionTextView = (TextView) rootView.findViewById(R.id.attribution);
        mAttributionTextView.setVisibility(View.INVISIBLE);
        return rootView;
    }


}
