package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.beans.CityResult;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.ParserCity;

/**
 * Created by ram on 12/6/15.
 */
public class CityAdapter extends ArrayAdapter<CityResult> implements Filterable {

    private Context ctx;
    private List<CityResult> cityList = new ArrayList<CityResult>();

    public CityAdapter(Context ctx, ArrayList<CityResult> cityList) {
        super(ctx, R.layout.cityresult_layout, cityList);
        this.cityList = cityList;
        this.ctx = ctx;
    }


    @Override
    public CityResult getItem(int position) {
        if (cityList != null)
            return cityList.get(position);

        return null;
    }

    @Override
    public int getCount() {
        if (cityList != null)
            return cityList.size();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inf.inflate(R.layout.cityresult_layout, parent, false);
        }
        TextView tv = (TextView) result.findViewById(R.id.txtCityName);
        tv.setText(cityList.get(position).getCityName() + "," + cityList.get(position).getCountry());
        return result;
    }

    @Override
    public long getItemId(int position) {
        if (cityList != null)
            return cityList.get(position).hashCode();

        return 0;
    }

    @Override
    public Filter getFilter() {
        Filter cityFilter = new Filter() {

            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() < 2)
                    return results;

                ParserCity parscity = new ParserCity();

                ArrayList<CityResult> cityResultList = null;
                try {
                    cityResultList = parscity.getCityList();


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                results.values = cityResultList;
                results.count = cityResultList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cityList = (List) results.values;
                notifyDataSetChanged();
            }
        };

        return cityFilter;
    }

}
