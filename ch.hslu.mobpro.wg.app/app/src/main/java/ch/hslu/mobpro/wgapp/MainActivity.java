package ch.hslu.mobpro.wgapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import ch.hslu.mobpro.wgapp.LoginActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIsLoggedIn();
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume(){
        super.onResume();
        checkIsLoggedIn();
    }


    private void checkIsLoggedIn() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String acceessToken = prefs.getString("access_token", "");
        if(!acceessToken.equals("")) {
            new checkAccessToken(acceessToken, this).execute();
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }
    // Create an HostnameVerifier that hardwires the expected hostname.
    // Note that is different than the URL's hostname:
    // example.com versus example.org
    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private class checkAccessToken extends AsyncTask<Void, Void, Void> {

        private String mAccessToken;
        private int mResponseCode;
        private Context mContext;
        public checkAccessToken(String accessToken, Context context){
            this.mAccessToken = accessToken;
            this.mContext = context;
        }
        @Override
        protected Void doInBackground(Void ... theVoid){
            URL url = null;
            try {
                url = new URL("https://mobpro.dzim.ch/api/values/1");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setHostnameVerifier(hostnameVerifier);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + mAccessToken);
                mResponseCode = connection.getResponseCode();
                Log.i("Respone Code", String.format("%s", connection.getResponseCode()));
                Log.i("Respone Message", connection.getResponseMessage().toString());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if(mResponseCode != 200){
                Intent loginIntent = new Intent(mContext, LoginActivity.class);
                startActivity(loginIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void  onLoginBtn_Click(View view)
    {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

    }

    public void btnGroup_OnClick(View view)
    {
        Intent groupListIntent = new Intent(this, GroupListActivity.class);
        startActivity(groupListIntent);
    }
}
