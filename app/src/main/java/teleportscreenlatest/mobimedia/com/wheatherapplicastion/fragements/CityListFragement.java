package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragement extends Fragment {


    public CityListFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_list_fragement, container, false);

        ListView citylist = (ListView) v.findViewById(R.id.citylist);


        return v;
    }


}
