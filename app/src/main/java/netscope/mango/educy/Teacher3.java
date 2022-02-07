package netscope.mango.educy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Anonymous on 10/8/2018.
 */

public class Teacher3 extends Fragment {
    public ProgressDialog pDialog;
    Button button;
    TextView qt,Auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher3, container, false);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");
            }
        });
       button=view.findViewById(R.id.next);
       qt=view.findViewById(R.id.qt);
      Auth=view.findViewById(R.id.Authr);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSON().execute("https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");
                    }
                });
            }
        });
        return view;
    }

  public   class ReadJSON extends AsyncTask<String, Integer, String> {
      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          // Showing progress dialog
          pDialog = new ProgressDialog(getActivity());
          pDialog.setMessage("Please wait...");
          pDialog.setCancelable(false);
          pDialog.show();

      }

      @Override
      protected String doInBackground(String... params) {
          return readURL(params[0]);
      }

      @Override
      protected void onPostExecute(String content) {
          if (pDialog.isShowing())
              pDialog.dismiss();
          try {
              JSONObject jsonObject = new JSONObject(content);
              String Quotes = jsonObject.getString("quoteText");
              String Authr = jsonObject.getString("quoteAuthor");
              qt.setText(Quotes);
              Auth.setText(Authr);


          } catch (JSONException e) {
              e.printStackTrace();
          }
      }

  }

      private static String readURL(String theUrl) {
          StringBuilder content = new StringBuilder();
          try {
              URL url = new URL(theUrl);
              URLConnection urlConnection = url.openConnection();
              BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
              String line;
              while ((line = bufferedReader.readLine()) != null) {
                  content.append(line + "\n");
              }
              bufferedReader.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
          return content.toString();
      }


}