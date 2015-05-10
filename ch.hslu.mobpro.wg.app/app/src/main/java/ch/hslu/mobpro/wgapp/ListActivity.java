package ch.hslu.mobpro.wgapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ch.hslu.mobpro.wgapp.R;

public class ListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        new RetrieveFeedTask(this).execute();

        final ListView listView = (ListView) findViewById(R.id.lvListList);
        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisplayValue selectedItem = (DisplayValue) listView.getItemAtPosition(position);

                Intent intent = new Intent(context, ListDetailActivity.class);
                intent.putExtra("ListID", selectedItem.getId());
                intent.putExtra("ListName", selectedItem.getDisplayValue());
                startActivity(intent);
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {
        private JSONArray mJsonArray;
        private Exception exception;
        private Context mContext;

        public RetrieveFeedTask(Context context) {
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
                url = new URL("https://mobpro.dzim.ch/api/list");

                // Create an SSLContext that uses our TrustManager
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[]{
                        new X509TrustManager() {
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                            }

                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            }

                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[]{};
                            }
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
            DisplayValue[] items = new DisplayValue[mJsonArray.length()];

            int groupId = ((Activity) mContext).getIntent().getIntExtra("GroupId", 0);

            for (int idx = 0; idx < mJsonArray.length(); idx++) {
                JSONObject jsonObject = mJsonArray.getJSONObject(idx);
                int itemGroupId = jsonObject.getInt("GroupsGroupID");
                if (itemGroupId == groupId) {
                    DisplayValue item = new DisplayValue(jsonObject.getInt("ListID"), jsonObject.getString("ListName"));
                    items[idx] = item;
                }
            }
            ArrayAdapter<DisplayValue> codeLearnArrayAdapter = new ArrayAdapter<DisplayValue>(mContext, android.R.layout.simple_list_item_1, items);
            ListView codeLearnLessons = (ListView) findViewById(R.id.lvListList);
            codeLearnLessons.setAdapter(codeLearnArrayAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_newlist) {
            this.CreateList(this);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CreateList(final Context context) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_newlist, null);
        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText tbxName = (EditText) view.findViewById(R.id.tbxListName);
                int groupId = getIntent().getIntExtra("GroupId", 0);

                ListEntity list = new ListEntity(0, tbxName.getText().toString(), groupId);
                new PostListTask(context, list).execute();
            }
        });

        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing..
            }
        });

        dialogBuilder.show();
    }

    class PostListTask extends AsyncTask<String, Void, Void> {
        private String json;
        private Exception exception;
        private Context mContext;
        private ListEntity list;

        public PostListTask(Context context, ListEntity list) {
            mContext = context;
            this.list = list;
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
                url = new URL("https://mobpro.dzim.ch/api/list");

                // Create an SSLContext that uses our TrustManager
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[]{
                        new X509TrustManager() {
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                            }

                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            }

                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[]{};
                            }
                        }
                }, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

                //create the connection
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setHostnameVerifier(hostnameVerifier);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                //set the request method to POST
                connection.setRequestMethod("POST");

                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(new Gson().toJson(list));
                outputStream.flush();
                outputStream.close();


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

                json = response;

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
            new RetrieveFeedTask(this.mContext).execute();
        }
    }
}
