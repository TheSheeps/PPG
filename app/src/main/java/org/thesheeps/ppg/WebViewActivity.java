package org.thesheeps.ppg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WebViewActivity extends Activity {

    String length;
    String secret;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle bundle = getIntent().getExtras();
        length = bundle.getString("LENGTH");
        secret = bundle.getString("SECRET");

        String line;
        String html = null;
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = getAssets().open("www/index.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
            br.close();

            html = sb.toString();
        } catch (IOException e) {
            Log.e("PPG_WebView", "Can't access file: ", e);
        }

        if (html != null) {
            html = html.replace("$LENGTH$", length);
            html = html.replace("$SECRET$", secret);
        }

        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("file:///android_asset/www/index.html", html, "text/html",
                "UTF-8", null);
    }
}