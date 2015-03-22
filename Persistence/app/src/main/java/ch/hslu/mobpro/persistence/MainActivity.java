package ch.hslu.mobpro.persistence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.prefs.Preferences;


public class MainActivity extends ActionBarActivity {

    private final String COUNTER_KEY = "Counter_Key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public void onResume(){
        super.onResume();


        UpdateResumeCounter();
        UpdateSettingString();


    }

    private void UpdateSettingString() {
        //final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String favoriteTea =  preferences.getString("teaPreferred", "Pfefferminztee");
        final boolean sweetened = preferences.getBoolean("teaWithSugar", true);
        TextView teaPreferences = (TextView)findViewById(R.id.txvSettings);
        if(sweetened){
            final String sweetener = preferences.getString("teaSweetener", "Zucker");
            teaPreferences.setText(String.format("Ich trinke am liebsten %s mit %s gesüsst.", favoriteTea, sweetener));
        }
        else{
            teaPreferences.setText(String.format("Ich trinke am liebsten %s.", favoriteTea));
        }
    }

    private void UpdateResumeCounter(){
        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        final int newResumeCount = preferences.getInt(COUNTER_KEY, 0) + 1;
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(COUNTER_KEY, newResumeCount);
        editor.apply();
        TextView resumeCounter = (TextView)findViewById(R.id.txtResumeCounter);
        String counterText = String.format("MainActivity.OnResume() wurde seit der letzten Installation der App %d mal aufgerufen.", newResumeCount);
        resumeCounter.setText(counterText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, TeaPreferenceActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void btnTeaDefaults_OnClick(View v){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("teaPreferred", "Yogi Tee");
        editor.putString("teaSweetener", "artificial");
        editor.putBoolean("teaWithSugar", true);
        editor.apply();
        UpdateSettingString();
    }

    public void btnEditSettings_OnClick(View v){
        Intent intent = new Intent(this, TeaPreferenceActivity.class);
        startActivity(intent);
    }

    public void txtSave_OnClick(View v){
        CheckBox cbxExternalStorage =  (CheckBox)findViewById(R.id.cbxExternalStorage);
        String path = "";
        if(cbxExternalStorage.isChecked()){
            path = this.getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        } else{
            path = this.getBaseContext().getFilesDir().getAbsolutePath();
        }
        File outFile = new File(path + "/myFile.txt");
        Writer writer = null;
        TextView txtFileSystem = (TextView)findViewById(R.id.txtFileSystem);
        String textToSave = txtFileSystem.getText().toString();
        try{
            writer = new BufferedWriter(new FileWriter(outFile));
            writer.write(textToSave);
            writer.close();
        } catch (final IOException ex){
            Log.e("HSLU-MobPro-Persistenz", "Houston we have a Porblem.");
        }
    }

    public void txtLoad_OnClick(View v){
        CheckBox cbxExternalStorage =  (CheckBox)findViewById(R.id.cbxExternalStorage);
        String path = "";
        if(cbxExternalStorage.isChecked()){
            path = this.getBaseContext().getExternalFilesDir(null).getAbsolutePath();
        } else{
            path = this.getBaseContext().getFilesDir().getAbsolutePath();
        }

        try {
            File infile = new File(path + "/myFile.txt");
            int length = (int) infile.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(infile);
            in.read(bytes);
            in.close();
            String contents = new String(bytes);
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Gespeicherte Nachricht");
            ad.setMessage(contents);
            ad.show();
        } catch (Exception ex) {
            Log.e("HSLU-MobPro-Persistenz", "Houston we have a Porblem.");
        }
    }

}
