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
public class DayForcastAdapter extends BaseAdapter{
    Context context;
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
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dayforcast, parent, false);

        }
        TextView day=(TextView)convertView.findViewById(R.id.day);
        TextView date=(TextView)convertView.findViewById(R.id.date);
        ImageView imageicon=(ImageView)convertView.findViewById(R.id.icon);
        TextView mintemp=(TextView)convertView.findViewById(R.id.mintemp);
        TextView maxtemp=(TextView)convertView.findViewById(R.id.maxtemp);






        return convertView;
    }
}
