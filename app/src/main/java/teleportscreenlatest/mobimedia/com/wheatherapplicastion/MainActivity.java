package teleportscreenlatest.mobimedia.com.wheatherapplicastion;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private EditText medittextplace;
    private String mTextplace = "";
    private String mcityname;
    LatLng latitude;
    LatLng longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupToolbar();
        SetUpUI();

        submit.setOnClickListener(this);
        mcityname = medittextplace.getText().toString().trim();

        SendCityNameByGeocode(mcityname);


    }

    private void SendCityNameByGeocode(String mcityname) {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

    }

    private void SetUpUI() {
        submit = (Button) findViewById(R.id.submit);
        medittextplace = (EditText) findViewById(R.id.edittextplace);


    }

    private void SetupToolbar() {
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_Title.setText("Weather Detail");
        txt_Next = (TextView) findViewById(R.id.txt_Next);
        txt_Next.setVisibility(View.INVISIBLE);
        mtoolbar = (Toolbar) findViewById(R.id.customActionbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
/*
                Validation mvalidation = new Validation();
                 mTextplace = mvalidation.CityNameValidation(medittextplace.getText().toString().trim());

                if (mTextplace.length() > 0) {

                    Intent intent_home = new Intent(MainActivity.this, DetailActivty.class);
                    startActivity(intent_home);
                } else {
                    Toast.makeText(MainActivity.this, "" + mTextplace, Toast.LENGTH_SHORT).show();

                }
*/
                Intent intent_home = new Intent(MainActivity.this, DetailActivty.class);
                startActivity(intent_home);

                break;
            default:
                break;
        }
    }
}
