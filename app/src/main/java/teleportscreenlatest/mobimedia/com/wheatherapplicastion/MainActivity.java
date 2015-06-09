package teleportscreenlatest.mobimedia.com.wheatherapplicastion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private Button submit;
    private TextView txt_Title;
    private TextView txt_Next;
    private EditText city;
    private String mcityname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupToolbar();
        SetUpUI();
        Log.i("Mainactivity","check");

        submit.setOnClickListener(this);
    }


    private void SetUpUI() {
        submit = (Button) findViewById(R.id.submit);
        city = (EditText) findViewById(R.id.edittextplace);
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
                Intent intent_submit = new Intent(MainActivity.this, DetailActivty.class);
                intent_submit.putExtra("CitytoDetail", mcityname);
                startActivity(intent_submit);

                break;
            default:
                break;
        }
    }
}
