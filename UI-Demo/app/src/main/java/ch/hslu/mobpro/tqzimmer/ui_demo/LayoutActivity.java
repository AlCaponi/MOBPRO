package ch.hslu.mobpro.tqzimmer.ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class LayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int radioButtonId = getIntent().getIntExtra("CheckedRadioButton", 0);
        if (radioButtonId == R.id.rbnRelativeLayout)
        {
            setContentView(R.layout.layoutdemo_relativelayout);
        }
        else
        {
            setContentView(R.layout.layoutdemo_linearlayout);
        }
    }
}
