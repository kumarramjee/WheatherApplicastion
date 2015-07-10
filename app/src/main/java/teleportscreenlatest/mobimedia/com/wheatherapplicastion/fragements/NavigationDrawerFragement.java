package teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragements;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragement extends Fragment {

    public static String PREF_NAME = "text";
    private static final String KEY_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    View containerView;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerlayout;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    TextView fetchedlist;
    String searchedcountryname;
    ListView lview;
    String navlist[];// = {"Current Location", "Get PLaces", "Edit Location", "Inbox", "SentItem", "DraftBox", "Settings", "Notifications", "Sign Out"};
    Context context;
    List mdrawerlist;//
    List mdrawerlistItem;

    public NavigationDrawerFragement() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(ReadFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
        navlist = getResources().getStringArray(R.array.navigationlistitem);

        mdrawerlist = new ArrayList();
        mdrawerlistItem = new ArrayList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        fetchedlist = (TextView) v.findViewById(R.id.fetchedlist);
        lview = (ListView) v.findViewById(R.id.navigationlistitem);

        mdrawerlist.add("Current Location");
        mdrawerlist.add("Get Places");
        mdrawerlist.add("Edit Location");
        mdrawerlist.add("Inbox");
        mdrawerlist.add("Sent Item");
        mdrawerlist.add("Draft Box");
        mdrawerlist.add("Settings");
        mdrawerlist.add("Notifications");
        mdrawerlist.add("News");
        mdrawerlist.add("SignOut");
        mdrawerlistItem.add(mdrawerlist);


      /*  NavigationDrawerAdapter nadapter = new NavigationDrawerAdapter(getActivity().getApplicationContext(), mdrawerlistItem);
        lview.setAdapter(nadapter);
*/


        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        return v;
    }


    public void SetUP(int fragementid, DrawerLayout drawerlayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragementid);
        mDrawerlayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerlayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;

                    SaveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");

                }


                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerOpened(drawerView);


                getActivity().invalidateOptionsMenu();


            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerlayout.openDrawer(containerView);
        }

        mDrawerlayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerlayout.setDrawerListener(mDrawerToggle);

    }

    public static void SaveToPreferences(Context context, String preferencename, String preferenceValue) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(preferencename, preferenceValue);
        editor.apply();
    }

    public static String ReadFromPreferences(Context context, String preferencename, String defaultValue) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        return sharedpreferences.getString(preferencename, defaultValue);
    }

    public void SetList(String cityname) {


        //  fetchedlist.setText(cityname);

    }

    public boolean isDrawerOpen() {
        return mDrawerlayout != null
                && mDrawerlayout.isDrawerOpen(containerView);
    }

    public void closeDrawer() {
        mDrawerlayout.isDrawerOpen(containerView);
    }


}