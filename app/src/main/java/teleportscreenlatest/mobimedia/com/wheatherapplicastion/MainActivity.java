package teleportscreenlatest.mobimedia.com.wheatherapplicastion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {
    Toolbar mtoolbar;
    Button submit;
    TextView txt_Title;
    TextView txt_Next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupToolbar();
        SetUpUI();


    }

    private void SetUpUI() {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

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
                Intent intent_home = new Intent(MainActivity.this, DetailActivty.class);
                startActivity(intent_home);

                break;
            default:
                break;
        }
    }
}
