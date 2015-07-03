package teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Day;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.model.Hour;

/**
 * Created by ram on 2/7/15.
 */
public class HourForecastAdapter extends BaseAdapter {
    Context context;
    List<Hour> mhourlist = new ArrayList<Hour>();
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
                holder.icon = (ImageView) convertView.findViewById(R.id.imageset);
                holder.weather = (TextView) convertView.findViewById(R.id.weatherdetailinfo);
                holder.temperature = (TextView) convertView.findViewById(R.id.temperture);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Hour mhour = (Hour) getItem(position);
            holder.time.setText((mhour.time).subSequence(11, (mhour.time.length() - 3)));
            holder.icon.setImageResource(R.drawable.skky);
            new DownloadImageTask(holder.icon).execute(mhourlist.get(position).icon);

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
        ImageView icon;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}
