package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
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
            convertView = inflater.inflate(R.layout.hourhorizontallist, null);

        } else {
            time=(TextView)convertView.findViewById(R.id.time);
            daytypehour=(TextView)convertView.findViewById(R.id.daytypehour);
            temperture=(TextView)convertView.findViewById(R.id.temperture);



        }


        return null;
    }
}
