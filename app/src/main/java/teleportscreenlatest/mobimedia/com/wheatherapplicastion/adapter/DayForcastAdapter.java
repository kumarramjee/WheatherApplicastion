package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;

/**
 * Created by ram on 12/6/15.
 */
public class DayForcastAdapter extends BaseAdapter {
    Context context;
    TextView day;
    TextView date;
    ImageView imageicon;
    TextView mintemp;
    TextView maxtemp;
    DayForcastAdapter()
    {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dayforcast, parent, false);
        }
        day = (TextView) convertView.findViewById(R.id.day);
        date = (TextView) convertView.findViewById(R.id.date);
        imageicon = (ImageView) convertView.findViewById(R.id.icon);
        mintemp = (TextView) convertView.findViewById(R.id.mintemp);
        maxtemp = (TextView) convertView.findViewById(R.id.maxtemp);


        return convertView;
    }
}
