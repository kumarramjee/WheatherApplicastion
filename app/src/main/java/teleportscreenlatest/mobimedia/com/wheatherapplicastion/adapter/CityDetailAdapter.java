package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;

/**
 * Created by ram on 18/6/15.
 */
public class CityDetailAdapter extends BaseAdapter {

    List<Day> datalist = new ArrayList<Day>();
    Context context;

    public CityDetailAdapter(Context context, List<Day> mDayList) {
        this.context = context;
        this.datalist = mDayList;
    }

    @Override
    public int getCount() {
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


        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.detail_list, null);
            holder.day1 = (TextView) convertView.findViewById(R.id.dayname);
            holder.description = (TextView) convertView.findViewById(R.id.daytype);
            holder.daytempmin = (TextView) convertView.findViewById(R.id.daytempmin);
            holder.daytempmax = (TextView) convertView.findViewById(R.id.daytempmax);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Day mday = (Day) getItem(position);
        holder.day1.setText(mday.day);

        holder.description.setText((mday.weather));

        holder.daytempmax.setText(mday.max + "℃");

        holder.daytempmin.setText(mday.min + "℃");

        Log.i("City Detail Adapter", "all Rows values==" + mday.min + "," + mday.max + "," + mday.weather + "," + mday.day);

        return convertView;

    }

    /*private view holder class*/
    private class ViewHolder {
        TextView day1;
        TextView description;
        TextView daytempmin;
        TextView daytempmax;
    }

}
