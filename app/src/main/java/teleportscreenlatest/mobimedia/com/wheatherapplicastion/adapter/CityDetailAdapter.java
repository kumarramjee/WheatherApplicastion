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
            holder.imageicon = (ImageView) convertView.findViewById(R.id.dayimagetype);
            holder.daytempmin = (TextView) convertView.findViewById(R.id.daytempmin);
            holder.daytempmax = (TextView) convertView.findViewById(R.id.daytempmax);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Day mday = (Day) getItem(position);
        holder.day1.setText(mday.day);


        holder.imageicon.setImageResource(R.drawable.skky);
        new DownloadImageTask(holder.imageicon).execute(mday.imageicon);

        holder.daytempmin.setText(mday.min + "℃");
        holder.daytempmax.setText(mday.max + "℃");


        return convertView;

    }

    /*private view holder class*/
    private class ViewHolder {
        TextView day1;
        TextView daytempmin;
        TextView daytempmax;
        ImageView imageicon;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        public ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {


            String urldisplay = urls[0];
            String imageurl = "http://openweathermap.org/img/w/" + urldisplay + ".png";


            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(imageurl).openStream();
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
