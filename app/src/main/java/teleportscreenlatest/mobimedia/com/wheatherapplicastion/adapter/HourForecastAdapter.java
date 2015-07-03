package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Hour;

/**
 * Created by ram on 2/7/15.
 */
public class HourForecastAdapter extends BaseAdapter {
    Context context;
    List<Hour> mhourlist;
    TextView time;
    TextView daytypehour;
    TextView temperture;

    public HourForecastAdapter(Context context, List<Hour> mtimelist) {
        this.context = context;
        this.mhourlist = mtimelist;
    }

    @Override
    public int getCount() {
        return mhourlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mhourlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.hourhorizontallist, null);
                holder = new ViewHolder();
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.weather = (TextView) convertView.findViewById(R.id.weatherdetailinfo);
                holder.temperature = (TextView) convertView.findViewById(R.id.temperture);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Hour mhour = (Hour) getItem(position);
            holder.time.setText((mhour.time).subSequence(11,(mhour.time.length()-3)));
            holder.weather.setText((mhour.weather));
            holder.temperature.setText(mhour.temperature + "℃");
            Log.i("City Detail Adapter", "all Rows values==" + mhour.time + "," + mhour.weather + "," + mhour.temperature);
            return convertView;
        }
    }

    private class ViewHolder {
        TextView time;
        TextView temperature;
        TextView weather;
    }
}
