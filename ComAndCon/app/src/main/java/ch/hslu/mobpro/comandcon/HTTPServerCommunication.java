package ch.hslu.mobpro.comandcon;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class HTTPServerCommunication extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpserver_communication);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_httpserver_communication, menu);
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

    public void btnSend_OnClick(View view){
        AsyncTask<Void, Void, Void> asyncTask = new TheTask().execute();
    }

    public void setResultMessage(String statusMessage, String resultMessage){
        TextView lblServerRespone = (TextView) findViewById(R.id.lblServerResponse);
        lblServerRespone.setText(statusMessage);

        TextView lblServerResponseActual = (TextView) findViewById(R.id.lblServerResponseActual);
        lblServerResponseActual.setText(resultMessage);
    }

    class TheTask extends AsyncTask<Void, Void, Void> {
        String returnMessage = "N/A";
        String serverResponse = "N/A";
        @Override
        protected Void doInBackground(Void... params) {
            try {
                EditText tbxUrl = (EditText) findViewById(R.id.tbxURL);
                EditText tbxData = (EditText) findViewById(R.id.tbxData);

                String url = tbxUrl.getText().toString();
                String parameter = tbxData.getText().toString();
                HttpGet httpGet = new HttpGet(url+"?"+parameter);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                returnMessage = statusLine.toString();
                serverResponse = response.toString();

            } catch (IOException ex) {
                Log.e("HSLU -  Communication & Concurrency", ex.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            setResultMessage(returnMessage, serverResponse);
        }
    }
}
