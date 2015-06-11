package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragement extends Fragment {


    public DayFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_day_fragement, container, false);

        return v;
    }


}
