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

/**
 * Created by ram on 26/6/15.
 */
public class NavigationDrawerAdapter extends BaseAdapter {
    Context context;
    List mDrawerlisteditem;

    public NavigationDrawerAdapter(Context mcontext, List mDraerlist) {
        this.context = mcontext;
        this.mDrawerlisteditem = mDraerlist;
    }

    @Override
    public int getCount() {
        Log.i("Size of item is",", Navigation adapter=="+mDrawerlisteditem.size());

        return mDrawerlisteditem.size();
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
            LayoutInflater minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            minflater.inflate(R.layout.drawer_list_item, null);
        }
        TextView draweritem = (TextView) convertView.findViewById(R.id.draweritem);
        for (int i = 0; i < mDrawerlisteditem.size(); i++) {
            draweritem.setText((Integer) mDrawerlisteditem.get(i));
        }


        return convertView;
    }
}
