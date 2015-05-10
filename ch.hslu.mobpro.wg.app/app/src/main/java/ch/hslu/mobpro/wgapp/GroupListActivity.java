package ch.hslu.mobpro.wgapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class GroupListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        new RetrieveFeedTask(this).execute();

        final ListView codeLearnLessons = (ListView)findViewById(R.id.lvGroupList);
        final Activity currentActivity = this;

        codeLearnLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ToDo: Get GroupId by position

                DisplayValue selectedItem = (DisplayValue) codeLearnLessons.getItemAtPosition(position);

                Intent intent = new Intent(currentActivity, ListActivity.class);
                intent.putExtra("GroupId", selectedItem.getId());
                startActivity(intent);
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {
        private JSONArray mJsonArray;
        private Exception exception;
        private Context mContext;

        public RetrieveFeedTask(Context context){
            mContext = context;
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
        protected Void doInBackground(String... urls) {
            try {
                URL url = null;
                String response = null;
                String parameters = "";
                url = new URL("https://mobpro.dzim.ch/api/WGGroups");

                // Create an SSLContext that uses our TrustManager
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] {
                        new X509TrustManager() {
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                        }
                }, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

                //create the connection
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setHostnameVerifier(hostnameVerifier);
                connection.setDoOutput(false);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                //set the request method to GET
                connection.setRequestMethod("GET");

                Log.i("Respone Code", String.format("%s", connection.getResponseCode()));
                Log.i("Respone Message", connection.getResponseMessage().toString());

                String line = "";
                //create your inputsream
                InputStreamReader isr = new InputStreamReader(
                        connection.getInputStream());
                //read in the data from input stream, this can be done a variety of ways
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //get the string version of the response data
                response = sb.toString();

                //do what you want with the data now
                mJsonArray = new JSONArray(response);

                //always remember to close your input and output streams
                isr.close();
                reader.close();
            } catch (Exception e) {
                Log.e("HTTP GET:", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            DisplayValue[] displayValues = new DisplayValue[mJsonArray.length()];
            for(int idx = 0; idx < mJsonArray.length(); idx++)
            {
                JSONObject jsonObject =  mJsonArray.getJSONObject(idx);
                DisplayValue item = new DisplayValue(jsonObject.getInt("GroupID"), jsonObject.getString("GroupName"));
                displayValues[idx] = item;
            }
            ArrayAdapter<DisplayValue> codeLearnArrayAdapter = new ArrayAdapter<DisplayValue>(mContext, android.R.layout.simple_list_item_1, displayValues);
            ListView codeLearnLessons = (ListView)findViewById(R.id.lvGroupList);
            codeLearnLessons.setAdapter(codeLearnArrayAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_list, menu);
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
}
