package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.adapter.CityAdapter;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.beans.CityResult;
import teleportscreenlatest.mobimedia.com.wheatherapplicastion.parser.ParserCity;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private AutoCompleteTextView city;
    private String mcityname;
    Context activity;
    ArrayList<CityResult> cityResultList;
    CityAdapter adpt;
    String msendcityname;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupToolbar();
        SetUpUI();

        submit.setOnClickListener(this);

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                msendcityname = city.getText().toString().trim();
                new Thread(new Runnable() {
                    public void run() {
                        ParserCity parscity = new ParserCity();
                        parscity.setText(msendcityname);

                        try {
                            cityResultList = parscity.getCityList();

                            Log.i("cityActivity", "==" + cityResultList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void afterTextChanged(Editable s) {


                adpt = new CityAdapter(getApplicationContext(), cityResultList);
                city.setAdapter(adpt);
                adpt.notifyDataSetChanged();

            }
        });
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x = position;


            }
        });
    }


    private void SetUpUI() {
        submit = (Button) findViewById(R.id.submit);
        city = (AutoCompleteTextView) findViewById(R.id.edittextplace);
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
                mcityname = city.getText().toString();
                if ((mcityname.length() == 0) || (mcityname == null)) {

                    Toast.makeText(this, "Enter City Name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
                    intent_submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent_submit.putExtra("CitytoDetail", mcityname);
                    startActivity(intent_submit);
                }
                break;
            default:
                break;
        }
    }
}
