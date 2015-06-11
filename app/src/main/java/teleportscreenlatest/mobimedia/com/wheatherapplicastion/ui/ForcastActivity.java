package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.fragments.DayFragement;


public class ForcastActivity extends Activity implements View.OnClickListener {
    private TextView mcityField;
    private String mcity = "";
    private Context mContext = this;
    private TextView mdetailsField;
    private TextView mcurrentTemperatureField;
    private TextView mupdatedField;
    private TextView mweatherIcon;
    private Handler mhandler;
    private TextView mtxt_Title;
    private ImageView mback_navigation;
    private TextView mtxt_Next;
    private ImageView mcurrentweather;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

        SetUpUI();



        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();

            }
        });


        getFragmentManager().beginTransaction().add(R.id.framelayout, new DayFragement()).commit();


    }


    private void refreshContent() {

        mSwipeRefreshLayout.setRefreshing(false);
    }


    private void SetUpUI() {

        mtxt_Title = (TextView) findViewById(R.id.txt_Title);
        mtxt_Title.setText("Forcast Infomartion");
        mtxt_Next = (TextView) findViewById(R.id.txt_Next);
        mtxt_Next.setVisibility(View.INVISIBLE);
        mcityField = (TextView) findViewById(R.id.city_field);
        mupdatedField = (TextView) findViewById(R.id.updated_field);
        mdetailsField = (TextView) findViewById(R.id.details_field);
        mcurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        mweatherIcon = (TextView) findViewById(R.id.weather_icon);
        mback_navigation = (ImageView) findViewById(R.id.back_navigation);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mback_navigation.setBackground(upArrow);
        mback_navigation.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_navigation:
                Intent intent_back = new Intent(ForcastActivity.this, DetailActivty.class);
                startActivity(intent_back);
                break;

            default:
                break;
        }
    }
}
