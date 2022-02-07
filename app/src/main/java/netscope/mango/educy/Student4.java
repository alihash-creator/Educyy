package netscope.mango.educy;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by Anonymous on 11/28/2018.
 */

public class Student4 extends Fragment {
    String url;
    Button getresponse;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student4, container, false);
        url = dictionaryEntries();
        getresponse=(Button)view.findViewById(R.id.button);
        webView=(WebView)view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://translate.google.com/");


        getresponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDictionaryRequest myDictionaryRequest = new MyDictionaryRequest(getActivity());
                myDictionaryRequest.execute(url);
            }
        });
        return view;
    }


    private String dictionaryEntries() {
        final String language = "en";
        final String word = "Ace";
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }
}

