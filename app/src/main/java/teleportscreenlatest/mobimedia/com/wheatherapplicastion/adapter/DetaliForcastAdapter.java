package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.DayForecast;

/**
 * Created by ram on 23/6/15.
 */
public class DetaliForcastAdapter  extends BaseAdapter{
    Context context;
    ArrayList<DayForecast> dayList;
    public DetaliForcastAdapter(Context context,ArrayList<DayForecast> dayList)
    {
        this.context=context;
        this.dayList=dayList;
    }

    @Override
    public int getCount() {
        return dayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
      //      LayoutInflater inflater=LayoutInflater.from()
        }
        else
        {

        }


        return null;
    }
}
