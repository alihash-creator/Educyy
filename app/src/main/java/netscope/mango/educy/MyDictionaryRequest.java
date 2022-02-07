package netscope.mango.educy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sannan on 9/21/2018.
 */

public class MyDictionaryRequest extends AsyncTask<String,Integer,String> {

    final String app_id = "091b8935";
    final String app_key = "5decabd8eaef02bb10c7219c07481bb0";
    String myUrl;
    Context context;
    public View view;


    MyDictionaryRequest(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        myUrl = params[0];

        try {
            URL url = new URL(myUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String def;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray results = jsonObject.getJSONArray("results");

            JSONObject lEnteries = results.getJSONObject(0);
            JSONArray laArray = lEnteries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray entriesArray = entries.getJSONArray("entries");

            JSONObject senses = entriesArray.getJSONObject(0);
            JSONArray sensesArray = senses.getJSONArray("senses");

            JSONObject definations = sensesArray.getJSONObject(0);
            JSONArray definationsArray = definations.getJSONArray("definitions");

            def = definationsArray.getString(0);

            TextView t1 = (TextView)((Activity)context).findViewById(R.id.view);
            t1.setText(def);
            Toast.makeText(context, def, Toast.LENGTH_SHORT).show();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
