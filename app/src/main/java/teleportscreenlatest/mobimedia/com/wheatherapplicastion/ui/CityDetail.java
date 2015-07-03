package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.CityDetailAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.HourForecastAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Hour;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.ParseForcastDay;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchForcastForDayJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.FetchHourForcastJson;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.util.HorizontalListView;

public class CityDetail extends Activity {

    private String city = "";
    private Context mContext = this;
    ListView lview;
    ParseForcastDay parse;
    TextView cityName;
    CityDetailAdapter cityDetailAdapter;
    List<Hour> mtimelist;
    HorizontalListView mhorizontal;
    HourForecastAdapter houradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        SetUpUI();
        parse = new ParseForcastDay();
        city = getIntent().getStringExtra("ForcastCityDetail").toUpperCase();
        cityName.setText(city);
        new AsyncTaskForForecastDayDetail().execute(city);
        new AsyncTaskForDaywiseDetail().execute(city);



    }

    private void SetUpUI() {
        lview = (ListView) findViewById(R.id.forcastdetail);
        cityName = (TextView) findViewById(R.id.cityName);
        mhorizontal = (HorizontalListView) findViewById(R.id.forcasthour);
    }


    public class AsyncTaskForDaywiseDetail extends AsyncTask<String, Void, List<Hour>> {

        @Override
        protected List<Hour> doInBackground(String... params) {
            List<Hour> hourlistAsync = new ArrayList<Hour>();
            String cityname = params[0];
            hourlistAsync = GetHourJson();
            return hourlistAsync;
        }

        @Override
        protected void onPostExecute(List<Hour> mHourList) {
            super.onPostExecute(mHourList);
            houradapter = new HourForecastAdapter(mContext, mHourList);
            mhorizontal.setAdapter(houradapter);
            houradapter.notifyDataSetChanged();
        }

        private List<Hour> GetHourJson() {
            List<Hour> getlistfromAsync = new ArrayList<Hour>();
            try {
                JSONObject json = FetchHourForcastJson.getJSON(CityDetail.this, city);
                getlistfromAsync = RenderWeatherHour(json);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return getlistfromAsync;
        }

        private List<Hour> RenderWeatherHour(JSONObject json) {

            {
                List<Hour> mHourList = new ArrayList<Hour>();
                try {
                    int length = json.getInt("cnt");
                    JSONArray ListArray = json.getJSONArray("list");
                    for (int i = 0; i < length; i++) {
                        Hour hourobject = new Hour();
                        JSONObject jobject = ListArray.getJSONObject(i);
                        JSONObject temp = jobject.getJSONObject("main");
                        hourobject.temperature = temp.getString("temp");
                        JSONArray weather = jobject.getJSONArray("weather");
                        for (int j = 0; j < weather.length(); j++) {
                            JSONObject jweather = weather.getJSONObject(j);
                            hourobject.weather = jweather.getString("description").toString();
                            hourobject.time = jobject.getString("dt_txt");
                            mHourList.add(hourobject);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return mHourList;
            }
        }
    }
    public class AsyncTaskForForecastDayDetail extends AsyncTask<String, Void, List<Day>> {
        private final ProgressDialog dialog = new ProgressDialog(CityDetail.this);

        @Override
        protected List<Day> doInBackground(String... params) {
            List<Day> dayListAsync = new ArrayList<Day>();
            String s = params[0];
            dayListAsync = getJSON(s);
            return dayListAsync;
        }

        @Override
        protected void onPostExecute(List<Day> mDayList) {
            super.onPostExecute(mDayList);
            dialog.cancel();
            cityDetailAdapter = new CityDetailAdapter(mContext, mDayList);
            lview.setAdapter(cityDetailAdapter);
            cityDetailAdapter.notifyDataSetChanged();
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Loading Please w8...");
            this.dialog.show();
        }

        private List<Day> getJSON(String city) {
            List<Day> listfromAsync = new ArrayList<Day>();
            try {
                JSONObject json = FetchForcastForDayJson.getJSON(CityDetail.this, city);
                listfromAsync = RenderWeatherDay(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listfromAsync;
        }

        private List<Day> RenderWeatherDay(JSONObject json) {
            List<Day> mdayList = new ArrayList<Day>();
            try {
                int length = json.getInt("cnt");
                JSONArray ListArray = json.getJSONArray("list");
                for (int i = 0; i < length; i++) {
                    Day dayobject = new Day();
                    JSONObject jobject = ListArray.getJSONObject(i);
                    int date1 = jobject.getInt("dt");
                    Date date = new Date(date1 * 1000);
                    SimpleDateFormat format = new SimpleDateFormat("E");
                    dayobject.day = format.format(date).toString();
                    JSONObject temp = jobject.getJSONObject("temp");
                    dayobject.min = temp.getString("min");
                    dayobject.max = temp.getString("max");
                    JSONArray weather = jobject.getJSONArray("weather");
                    for (int j = 0; j < weather.length(); j++) {
                        JSONObject jweather = weather.getJSONObject(j);
                        dayobject.weather = jweather.getString("description").toString();
                        mdayList.add(dayobject);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mdayList;
        }
    }
}