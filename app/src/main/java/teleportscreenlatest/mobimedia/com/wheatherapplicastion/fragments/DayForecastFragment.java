package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;

/**
 * Created by ram on 11/6/15.
 */
public class DayForecastFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dayforecast_fragment, container, false);

        return v;
    }

}
