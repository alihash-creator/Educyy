package netscope.mango.educy;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Anonymous on 10/8/2018.
 */

public class Student3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student3, container, false);


        WebView wview = (WebView) view.findViewById(R.id.webv);
        wview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        wview.getSettings().setJavaScriptEnabled(true);
        wview.loadUrl("https://www.slideshare.net/");
        return view;
    }
}
