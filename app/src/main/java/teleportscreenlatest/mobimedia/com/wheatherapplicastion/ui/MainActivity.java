package teleportscreenlatest.mobimedia.com.wheatherapplicastion.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import teleportscreenlatest.mobimedia.com.wheatherapplicastion.R;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private AutoCompleteTextView city;
    private String mcityname;
    Context activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupToolbar();
        SetUpUI();

        submit.setOnClickListener(this);


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

                    Toast.makeText(this,"Enter City Name",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
                    intent_submit.putExtra("CitytoDetail", mcityname);
                    startActivity(intent_submit);
                }
                break;
            default:
                break;
        }
    }
}
