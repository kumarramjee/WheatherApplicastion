package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.WeatherForecastListAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.WeatherForecast;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui.MainActivity;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchJson;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    ListView mForecastListView;
    TextView mLocationNameTextView;
    TextView mCurrentTemperatureTextView;
    TextView mAttributionTextView;
    private String mCityname;
    Context mContext;
    private Handler mhandler;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        mhandler=new Handler();

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
        GetDetailWeatherDetail(getActivity(), mCityname);


        return rootView;
    }

    private void GetDetailWeatherDetail(Activity activity, final String mCityname) {

        if (mCityname.length() == 0) {
            Toast.makeText(getActivity(), "Not able to find current location.Check ur connection", Toast.LENGTH_SHORT).show();

        } else {
            new Thread() {
                public void run() {
                    final JSONObject json = FetchForcastForDayJson.getJSON(mContext, mCityname);
                    if (json == null) {
                        mhandler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(mContext,
                                        "Try after some timee.",
                                        Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        mhandler.post(new Runnable() {
                            public void run() {
                                RenderWeatherDay(json);
                            }
                        });
                    }
                }
            }.start();
        }


    }

    private void RenderWeatherDay(JSONObject json) {


        try {
            JSONObject jsondaycode=json.getJSONObject("cod");
            String city_code=jsondaycode.getString("cod");
            Log.i("City Code is","City Code"+city_code);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
